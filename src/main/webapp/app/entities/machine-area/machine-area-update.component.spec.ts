/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MachineAreaUpdate from './machine-area-update.vue';
import MachineAreaService from './machine-area.service';
import AlertService from '@/shared/alert/alert.service';

import CompetitionService from '@/entities/competition/competition.service';

type MachineAreaUpdateComponentType = InstanceType<typeof MachineAreaUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const machineAreaSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<MachineAreaUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('MachineArea Management Update Component', () => {
    let comp: MachineAreaUpdateComponentType;
    let machineAreaServiceStub: SinonStubbedInstance<MachineAreaService>;

    beforeEach(() => {
      route = {};
      machineAreaServiceStub = sinon.createStubInstance<MachineAreaService>(MachineAreaService);
      machineAreaServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          machineAreaService: () => machineAreaServiceStub,
          competitionService: () =>
            sinon.createStubInstance<CompetitionService>(CompetitionService, {
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
        const wrapper = shallowMount(MachineAreaUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.machineArea = machineAreaSample;
        machineAreaServiceStub.update.resolves(machineAreaSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(machineAreaServiceStub.update.calledWith(machineAreaSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        machineAreaServiceStub.create.resolves(entity);
        const wrapper = shallowMount(MachineAreaUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.machineArea = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(machineAreaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        machineAreaServiceStub.find.resolves(machineAreaSample);
        machineAreaServiceStub.retrieve.resolves([machineAreaSample]);

        // WHEN
        route = {
          params: {
            machineAreaId: '' + machineAreaSample.id,
          },
        };
        const wrapper = shallowMount(MachineAreaUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.machineArea).toMatchObject(machineAreaSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        machineAreaServiceStub.find.resolves(machineAreaSample);
        const wrapper = shallowMount(MachineAreaUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
