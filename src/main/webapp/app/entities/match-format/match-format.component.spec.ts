/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import MatchFormat from './match-format.vue';
import MatchFormatService from './match-format.service';
import AlertService from '@/shared/alert/alert.service';

type MatchFormatComponentType = InstanceType<typeof MatchFormat>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('MatchFormat Management Component', () => {
    let matchFormatServiceStub: SinonStubbedInstance<MatchFormatService>;
    let mountOptions: MountingOptions<MatchFormatComponentType>['global'];

    beforeEach(() => {
      matchFormatServiceStub = sinon.createStubInstance<MatchFormatService>(MatchFormatService);
      matchFormatServiceStub.retrieve.resolves({ headers: {} });

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
          matchFormatService: () => matchFormatServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        matchFormatServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(MatchFormat, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(matchFormatServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.matchFormats[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: MatchFormatComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(MatchFormat, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        matchFormatServiceStub.retrieve.reset();
        matchFormatServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        matchFormatServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeMatchFormat();
        await comp.$nextTick(); // clear components

        // THEN
        expect(matchFormatServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(matchFormatServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
