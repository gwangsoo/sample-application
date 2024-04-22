/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MachineDetails from './machine-details.vue';
import MachineService from './machine.service';
import AlertService from '@/shared/alert/alert.service';

type MachineDetailsComponentType = InstanceType<typeof MachineDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const machineSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Machine Management Detail Component', () => {
    let machineServiceStub: SinonStubbedInstance<MachineService>;
    let mountOptions: MountingOptions<MachineDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      machineServiceStub = sinon.createStubInstance<MachineService>(MachineService);

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
          machineService: () => machineServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        machineServiceStub.find.resolves(machineSample);
        route = {
          params: {
            machineId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(MachineDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.machine).toMatchObject(machineSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        machineServiceStub.find.resolves(machineSample);
        const wrapper = shallowMount(MachineDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
