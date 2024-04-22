/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import MatchFormatLeg from './match-format-leg.vue';
import MatchFormatLegService from './match-format-leg.service';
import AlertService from '@/shared/alert/alert.service';

type MatchFormatLegComponentType = InstanceType<typeof MatchFormatLeg>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('MatchFormatLeg Management Component', () => {
    let matchFormatLegServiceStub: SinonStubbedInstance<MatchFormatLegService>;
    let mountOptions: MountingOptions<MatchFormatLegComponentType>['global'];

    beforeEach(() => {
      matchFormatLegServiceStub = sinon.createStubInstance<MatchFormatLegService>(MatchFormatLegService);
      matchFormatLegServiceStub.retrieve.resolves({ headers: {} });

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
          matchFormatLegService: () => matchFormatLegServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        matchFormatLegServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(MatchFormatLeg, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(matchFormatLegServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.matchFormatLegs[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: MatchFormatLegComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(MatchFormatLeg, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        matchFormatLegServiceStub.retrieve.reset();
        matchFormatLegServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        matchFormatLegServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeMatchFormatLeg();
        await comp.$nextTick(); // clear components

        // THEN
        expect(matchFormatLegServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(matchFormatLegServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
