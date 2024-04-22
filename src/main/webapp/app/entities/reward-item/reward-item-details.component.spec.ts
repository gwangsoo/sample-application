/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RewardItemDetails from './reward-item-details.vue';
import RewardItemService from './reward-item.service';
import AlertService from '@/shared/alert/alert.service';

type RewardItemDetailsComponentType = InstanceType<typeof RewardItemDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rewardItemSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('RewardItem Management Detail Component', () => {
    let rewardItemServiceStub: SinonStubbedInstance<RewardItemService>;
    let mountOptions: MountingOptions<RewardItemDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      rewardItemServiceStub = sinon.createStubInstance<RewardItemService>(RewardItemService);

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
          rewardItemService: () => rewardItemServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        rewardItemServiceStub.find.resolves(rewardItemSample);
        route = {
          params: {
            rewardItemId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(RewardItemDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.rewardItem).toMatchObject(rewardItemSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rewardItemServiceStub.find.resolves(rewardItemSample);
        const wrapper = shallowMount(RewardItemDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
