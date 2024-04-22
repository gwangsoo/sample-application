/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RewardDetails from './reward-details.vue';
import RewardService from './reward.service';
import AlertService from '@/shared/alert/alert.service';

type RewardDetailsComponentType = InstanceType<typeof RewardDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rewardSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Reward Management Detail Component', () => {
    let rewardServiceStub: SinonStubbedInstance<RewardService>;
    let mountOptions: MountingOptions<RewardDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      rewardServiceStub = sinon.createStubInstance<RewardService>(RewardService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          rewardService: () => rewardServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        rewardServiceStub.find.resolves(rewardSample);
        route = {
          params: {
            rewardId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(RewardDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.reward).toMatchObject(rewardSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rewardServiceStub.find.resolves(rewardSample);
        const wrapper = shallowMount(RewardDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
