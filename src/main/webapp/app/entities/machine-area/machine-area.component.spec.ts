/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import MachineArea from './machine-area.vue';
import MachineAreaService from './machine-area.service';
import AlertService from '@/shared/alert/alert.service';

type MachineAreaComponentType = InstanceType<typeof MachineArea>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('MachineArea Management Component', () => {
    let machineAreaServiceStub: SinonStubbedInstance<MachineAreaService>;
    let mountOptions: MountingOptions<MachineAreaComponentType>['global'];

    beforeEach(() => {
      machineAreaServiceStub = sinon.createStubInstance<MachineAreaService>(MachineAreaService);
      machineAreaServiceStub.retrieve.resolves({ headers: {} });

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
          machineAreaService: () => machineAreaServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        machineAreaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(MachineArea, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(machineAreaServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.machineAreas[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: MachineAreaComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(MachineArea, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        machineAreaServiceStub.retrieve.reset();
        machineAreaServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        machineAreaServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeMachineArea();
        await comp.$nextTick(); // clear components

        // THEN
        expect(machineAreaServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(machineAreaServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
