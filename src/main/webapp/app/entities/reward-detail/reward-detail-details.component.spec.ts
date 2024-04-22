/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RewardDetailDetails from './reward-detail-details.vue';
import RewardDetailService from './reward-detail.service';
import AlertService from '@/shared/alert/alert.service';

type RewardDetailDetailsComponentType = InstanceType<typeof RewardDetailDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rewardDetailSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('RewardDetail Management Detail Component', () => {
    let rewardDetailServiceStub: SinonStubbedInstance<RewardDetailService>;
    let mountOptions: MountingOptions<RewardDetailDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      rewardDetailServiceStub = sinon.createStubInstance<RewardDetailService>(RewardDetailService);

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
          rewardDetailService: () => rewardDetailServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        rewardDetailServiceStub.find.resolves(rewardDetailSample);
        route = {
          params: {
            rewardDetailId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(RewardDetailDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.rewardDetail).toMatchObject(rewardDetailSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rewardDetailServiceStub.find.resolves(rewardDetailSample);
        const wrapper = shallowMount(RewardDetailDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
