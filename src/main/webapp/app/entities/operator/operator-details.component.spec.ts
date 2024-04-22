/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import OperatorDetails from './operator-details.vue';
import OperatorService from './operator.service';
import AlertService from '@/shared/alert/alert.service';

type OperatorDetailsComponentType = InstanceType<typeof OperatorDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const operatorSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Operator Management Detail Component', () => {
    let operatorServiceStub: SinonStubbedInstance<OperatorService>;
    let mountOptions: MountingOptions<OperatorDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      operatorServiceStub = sinon.createStubInstance<OperatorService>(OperatorService);

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
          operatorService: () => operatorServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        operatorServiceStub.find.resolves(operatorSample);
        route = {
          params: {
            operatorId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(OperatorDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.operator).toMatchObject(operatorSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        operatorServiceStub.find.resolves(operatorSample);
        const wrapper = shallowMount(OperatorDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
