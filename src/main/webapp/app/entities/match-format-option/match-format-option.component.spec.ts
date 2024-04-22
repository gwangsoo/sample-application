/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import MatchFormatOption from './match-format-option.vue';
import MatchFormatOptionService from './match-format-option.service';
import AlertService from '@/shared/alert/alert.service';

type MatchFormatOptionComponentType = InstanceType<typeof MatchFormatOption>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('MatchFormatOption Management Component', () => {
    let matchFormatOptionServiceStub: SinonStubbedInstance<MatchFormatOptionService>;
    let mountOptions: MountingOptions<MatchFormatOptionComponentType>['global'];

    beforeEach(() => {
      matchFormatOptionServiceStub = sinon.createStubInstance<MatchFormatOptionService>(MatchFormatOptionService);
      matchFormatOptionServiceStub.retrieve.resolves({ headers: {} });

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
          matchFormatOptionService: () => matchFormatOptionServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        matchFormatOptionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(MatchFormatOption, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(matchFormatOptionServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.matchFormatOptions[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: MatchFormatOptionComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(MatchFormatOption, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        matchFormatOptionServiceStub.retrieve.reset();
        matchFormatOptionServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        matchFormatOptionServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeMatchFormatOption();
        await comp.$nextTick(); // clear components

        // THEN
        expect(matchFormatOptionServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(matchFormatOptionServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
