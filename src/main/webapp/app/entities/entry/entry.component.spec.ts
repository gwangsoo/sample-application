/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Entry from './entry.vue';
import EntryService from './entry.service';
import AlertService from '@/shared/alert/alert.service';

type EntryComponentType = InstanceType<typeof Entry>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Entry Management Component', () => {
    let entryServiceStub: SinonStubbedInstance<EntryService>;
    let mountOptions: MountingOptions<EntryComponentType>['global'];

    beforeEach(() => {
      entryServiceStub = sinon.createStubInstance<EntryService>(EntryService);
      entryServiceStub.retrieve.resolves({ headers: {} });

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
          entryService: () => entryServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        entryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(Entry, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(entryServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.entries[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: EntryComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Entry, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        entryServiceStub.retrieve.reset();
        entryServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        entryServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeEntry();
        await comp.$nextTick(); // clear components

        // THEN
        expect(entryServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(entryServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
