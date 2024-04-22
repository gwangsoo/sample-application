/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import OperatorRoleUpdate from './operator-role-update.vue';
import OperatorRoleService from './operator-role.service';
import AlertService from '@/shared/alert/alert.service';

type OperatorRoleUpdateComponentType = InstanceType<typeof OperatorRoleUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const operatorRoleSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<OperatorRoleUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('OperatorRole Management Update Component', () => {
    let comp: OperatorRoleUpdateComponentType;
    let operatorRoleServiceStub: SinonStubbedInstance<OperatorRoleService>;

    beforeEach(() => {
      route = {};
      operatorRoleServiceStub = sinon.createStubInstance<OperatorRoleService>(OperatorRoleService);
      operatorRoleServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          operatorRoleService: () => operatorRoleServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(OperatorRoleUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.operatorRole = operatorRoleSample;
        operatorRoleServiceStub.update.resolves(operatorRoleSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(operatorRoleServiceStub.update.calledWith(operatorRoleSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        operatorRoleServiceStub.create.resolves(entity);
        const wrapper = shallowMount(OperatorRoleUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.operatorRole = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(operatorRoleServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        operatorRoleServiceStub.find.resolves(operatorRoleSample);
        operatorRoleServiceStub.retrieve.resolves([operatorRoleSample]);

        // WHEN
        route = {
          params: {
            operatorRoleId: '' + operatorRoleSample.id,
          },
        };
        const wrapper = shallowMount(OperatorRoleUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.operatorRole).toMatchObject(operatorRoleSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        operatorRoleServiceStub.find.resolves(operatorRoleSample);
        const wrapper = shallowMount(OperatorRoleUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
