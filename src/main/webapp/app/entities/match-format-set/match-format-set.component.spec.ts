/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import MatchFormatSet from './match-format-set.vue';
import MatchFormatSetService from './match-format-set.service';
import AlertService from '@/shared/alert/alert.service';

type MatchFormatSetComponentType = InstanceType<typeof MatchFormatSet>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('MatchFormatSet Management Component', () => {
    let matchFormatSetServiceStub: SinonStubbedInstance<MatchFormatSetService>;
    let mountOptions: MountingOptions<MatchFormatSetComponentType>['global'];

    beforeEach(() => {
      matchFormatSetServiceStub = sinon.createStubInstance<MatchFormatSetService>(MatchFormatSetService);
      matchFormatSetServiceStub.retrieve.resolves({ headers: {} });

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
          matchFormatSetService: () => matchFormatSetServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        matchFormatSetServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(MatchFormatSet, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(matchFormatSetServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.matchFormatSets[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: MatchFormatSetComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(MatchFormatSet, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        matchFormatSetServiceStub.retrieve.reset();
        matchFormatSetServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        matchFormatSetServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeMatchFormatSet();
        await comp.$nextTick(); // clear components

        // THEN
        expect(matchFormatSetServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(matchFormatSetServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
