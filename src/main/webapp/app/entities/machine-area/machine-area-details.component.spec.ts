/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MachineAreaDetails from './machine-area-details.vue';
import MachineAreaService from './machine-area.service';
import AlertService from '@/shared/alert/alert.service';

type MachineAreaDetailsComponentType = InstanceType<typeof MachineAreaDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const machineAreaSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('MachineArea Management Detail Component', () => {
    let machineAreaServiceStub: SinonStubbedInstance<MachineAreaService>;
    let mountOptions: MountingOptions<MachineAreaDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      machineAreaServiceStub = sinon.createStubInstance<MachineAreaService>(MachineAreaService);

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
          machineAreaService: () => machineAreaServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        machineAreaServiceStub.find.resolves(machineAreaSample);
        route = {
          params: {
            machineAreaId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(MachineAreaDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.machineArea).toMatchObject(machineAreaSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        machineAreaServiceStub.find.resolves(machineAreaSample);
        const wrapper = shallowMount(MachineAreaDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
