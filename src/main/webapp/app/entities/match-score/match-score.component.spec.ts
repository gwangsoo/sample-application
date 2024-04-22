/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import MatchScore from './match-score.vue';
import MatchScoreService from './match-score.service';
import AlertService from '@/shared/alert/alert.service';

type MatchScoreComponentType = InstanceType<typeof MatchScore>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('MatchScore Management Component', () => {
    let matchScoreServiceStub: SinonStubbedInstance<MatchScoreService>;
    let mountOptions: MountingOptions<MatchScoreComponentType>['global'];

    beforeEach(() => {
      matchScoreServiceStub = sinon.createStubInstance<MatchScoreService>(MatchScoreService);
      matchScoreServiceStub.retrieve.resolves({ headers: {} });

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
          matchScoreService: () => matchScoreServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        matchScoreServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(MatchScore, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(matchScoreServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.matchScores[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: MatchScoreComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(MatchScore, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        matchScoreServiceStub.retrieve.reset();
        matchScoreServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        matchScoreServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeMatchScore();
        await comp.$nextTick(); // clear components

        // THEN
        expect(matchScoreServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(matchScoreServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
