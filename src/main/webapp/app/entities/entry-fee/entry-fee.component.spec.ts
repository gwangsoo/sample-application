/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import EntryFee from './entry-fee.vue';
import EntryFeeService from './entry-fee.service';
import AlertService from '@/shared/alert/alert.service';

type EntryFeeComponentType = InstanceType<typeof EntryFee>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('EntryFee Management Component', () => {
    let entryFeeServiceStub: SinonStubbedInstance<EntryFeeService>;
    let mountOptions: MountingOptions<EntryFeeComponentType>['global'];

    beforeEach(() => {
      entryFeeServiceStub = sinon.createStubInstance<EntryFeeService>(EntryFeeService);
      entryFeeServiceStub.retrieve.resolves({ headers: {} });

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
          entryFeeService: () => entryFeeServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        entryFeeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(EntryFee, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(entryFeeServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.entryFees[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: EntryFeeComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(EntryFee, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        entryFeeServiceStub.retrieve.reset();
        entryFeeServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        entryFeeServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeEntryFee();
        await comp.$nextTick(); // clear components

        // THEN
        expect(entryFeeServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(entryFeeServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
