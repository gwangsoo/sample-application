/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Machine from './machine.vue';
import MachineService from './machine.service';
import AlertService from '@/shared/alert/alert.service';

type MachineComponentType = InstanceType<typeof Machine>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Machine Management Component', () => {
    let machineServiceStub: SinonStubbedInstance<MachineService>;
    let mountOptions: MountingOptions<MachineComponentType>['global'];

    beforeEach(() => {
      machineServiceStub = sinon.createStubInstance<MachineService>(MachineService);
      machineServiceStub.retrieve.resolves({ headers: {} });

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
          machineService: () => machineServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        machineServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(Machine, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(machineServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.machines[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: MachineComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Machine, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        machineServiceStub.retrieve.reset();
        machineServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        machineServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeMachine();
        await comp.$nextTick(); // clear components

        // THEN
        expect(machineServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(machineServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
