/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Match from './match.vue';
import MatchService from './match.service';
import AlertService from '@/shared/alert/alert.service';

type MatchComponentType = InstanceType<typeof Match>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Match Management Component', () => {
    let matchServiceStub: SinonStubbedInstance<MatchService>;
    let mountOptions: MountingOptions<MatchComponentType>['global'];

    beforeEach(() => {
      matchServiceStub = sinon.createStubInstance<MatchService>(MatchService);
      matchServiceStub.retrieve.resolves({ headers: {} });

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
          matchService: () => matchServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        matchServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(Match, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(matchServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.matches[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: MatchComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Match, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        matchServiceStub.retrieve.reset();
        matchServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        matchServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeMatch();
        await comp.$nextTick(); // clear components

        // THEN
        expect(matchServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(matchServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
