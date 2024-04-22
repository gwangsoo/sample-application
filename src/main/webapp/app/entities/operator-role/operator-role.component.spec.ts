/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import OperatorRole from './operator-role.vue';
import OperatorRoleService from './operator-role.service';
import AlertService from '@/shared/alert/alert.service';

type OperatorRoleComponentType = InstanceType<typeof OperatorRole>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('OperatorRole Management Component', () => {
    let operatorRoleServiceStub: SinonStubbedInstance<OperatorRoleService>;
    let mountOptions: MountingOptions<OperatorRoleComponentType>['global'];

    beforeEach(() => {
      operatorRoleServiceStub = sinon.createStubInstance<OperatorRoleService>(OperatorRoleService);
      operatorRoleServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          operatorRoleService: () => operatorRoleServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        operatorRoleServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(OperatorRole, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(operatorRoleServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.operatorRoles[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: OperatorRoleComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(OperatorRole, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        operatorRoleServiceStub.retrieve.reset();
        operatorRoleServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        operatorRoleServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeOperatorRole();
        await comp.$nextTick(); // clear components

        // THEN
        expect(operatorRoleServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(operatorRoleServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
