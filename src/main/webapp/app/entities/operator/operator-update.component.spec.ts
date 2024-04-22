/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import OperatorUpdate from './operator-update.vue';
import OperatorService from './operator.service';
import AlertService from '@/shared/alert/alert.service';

import OperatorRoleService from '@/entities/operator-role/operator-role.service';
import RegionService from '@/entities/region/region.service';

type OperatorUpdateComponentType = InstanceType<typeof OperatorUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const operatorSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<OperatorUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Operator Management Update Component', () => {
    let comp: OperatorUpdateComponentType;
    let operatorServiceStub: SinonStubbedInstance<OperatorService>;

    beforeEach(() => {
      route = {};
      operatorServiceStub = sinon.createStubInstance<OperatorService>(OperatorService);
      operatorServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          operatorService: () => operatorServiceStub,
          operatorRoleService: () =>
            sinon.createStubInstance<OperatorRoleService>(OperatorRoleService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          regionService: () =>
            sinon.createStubInstance<RegionService>(RegionService, {
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
        const wrapper = shallowMount(OperatorUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.operator = operatorSample;
        operatorServiceStub.update.resolves(operatorSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(operatorServiceStub.update.calledWith(operatorSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        operatorServiceStub.create.resolves(entity);
        const wrapper = shallowMount(OperatorUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.operator = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(operatorServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        operatorServiceStub.find.resolves(operatorSample);
        operatorServiceStub.retrieve.resolves([operatorSample]);

        // WHEN
        route = {
          params: {
            operatorId: '' + operatorSample.id,
          },
        };
        const wrapper = shallowMount(OperatorUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.operator).toMatchObject(operatorSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        operatorServiceStub.find.resolves(operatorSample);
        const wrapper = shallowMount(OperatorUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
