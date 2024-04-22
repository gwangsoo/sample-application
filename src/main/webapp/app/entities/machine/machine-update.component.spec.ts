/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MachineUpdate from './machine-update.vue';
import MachineService from './machine.service';
import AlertService from '@/shared/alert/alert.service';

import MatchService from '@/entities/match/match.service';
import MachineAreaService from '@/entities/machine-area/machine-area.service';

type MachineUpdateComponentType = InstanceType<typeof MachineUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const machineSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<MachineUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Machine Management Update Component', () => {
    let comp: MachineUpdateComponentType;
    let machineServiceStub: SinonStubbedInstance<MachineService>;

    beforeEach(() => {
      route = {};
      machineServiceStub = sinon.createStubInstance<MachineService>(MachineService);
      machineServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          machineService: () => machineServiceStub,
          matchService: () =>
            sinon.createStubInstance<MatchService>(MatchService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          machineAreaService: () =>
            sinon.createStubInstance<MachineAreaService>(MachineAreaService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(MachineUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.machine = machineSample;
        machineServiceStub.update.resolves(machineSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(machineServiceStub.update.calledWith(machineSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        machineServiceStub.create.resolves(entity);
        const wrapper = shallowMount(MachineUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.machine = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(machineServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        machineServiceStub.find.resolves(machineSample);
        machineServiceStub.retrieve.resolves([machineSample]);

        // WHEN
        route = {
          params: {
            machineId: '' + machineSample.id,
          },
        };
        const wrapper = shallowMount(MachineUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.machine).toMatchObject(machineSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        machineServiceStub.find.resolves(machineSample);
        const wrapper = shallowMount(MachineUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
