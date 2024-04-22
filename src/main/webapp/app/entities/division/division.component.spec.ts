/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Division from './division.vue';
import DivisionService from './division.service';
import AlertService from '@/shared/alert/alert.service';

type DivisionComponentType = InstanceType<typeof Division>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Division Management Component', () => {
    let divisionServiceStub: SinonStubbedInstance<DivisionService>;
    let mountOptions: MountingOptions<DivisionComponentType>['global'];

    beforeEach(() => {
      divisionServiceStub = sinon.createStubInstance<DivisionService>(DivisionService);
      divisionServiceStub.retrieve.resolves({ headers: {} });

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
          divisionService: () => divisionServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        divisionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(Division, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(divisionServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.divisions[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: DivisionComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Division, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        divisionServiceStub.retrieve.reset();
        divisionServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        divisionServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeDivision();
        await comp.$nextTick(); // clear components

        // THEN
        expect(divisionServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(divisionServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
