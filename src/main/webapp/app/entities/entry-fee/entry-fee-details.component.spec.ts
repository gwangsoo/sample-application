/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import EntryFeeDetails from './entry-fee-details.vue';
import EntryFeeService from './entry-fee.service';
import AlertService from '@/shared/alert/alert.service';

type EntryFeeDetailsComponentType = InstanceType<typeof EntryFeeDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const entryFeeSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('EntryFee Management Detail Component', () => {
    let entryFeeServiceStub: SinonStubbedInstance<EntryFeeService>;
    let mountOptions: MountingOptions<EntryFeeDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      entryFeeServiceStub = sinon.createStubInstance<EntryFeeService>(EntryFeeService);

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
          entryFeeService: () => entryFeeServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        entryFeeServiceStub.find.resolves(entryFeeSample);
        route = {
          params: {
            entryFeeId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(EntryFeeDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.entryFee).toMatchObject(entryFeeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        entryFeeServiceStub.find.resolves(entryFeeSample);
        const wrapper = shallowMount(EntryFeeDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
