/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MatchScoreUpdate from './match-score-update.vue';
import MatchScoreService from './match-score.service';
import AlertService from '@/shared/alert/alert.service';

import MatchService from '@/entities/match/match.service';

type MatchScoreUpdateComponentType = InstanceType<typeof MatchScoreUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const matchScoreSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<MatchScoreUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('MatchScore Management Update Component', () => {
    let comp: MatchScoreUpdateComponentType;
    let matchScoreServiceStub: SinonStubbedInstance<MatchScoreService>;

    beforeEach(() => {
      route = {};
      matchScoreServiceStub = sinon.createStubInstance<MatchScoreService>(MatchScoreService);
      matchScoreServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          matchScoreService: () => matchScoreServiceStub,
          matchService: () =>
            sinon.createStubInstance<MatchService>(MatchService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(MatchScoreUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.matchScore = matchScoreSample;
        matchScoreServiceStub.update.resolves(matchScoreSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchScoreServiceStub.update.calledWith(matchScoreSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        matchScoreServiceStub.create.resolves(entity);
        const wrapper = shallowMount(MatchScoreUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.matchScore = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchScoreServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        matchScoreServiceStub.find.resolves(matchScoreSample);
        matchScoreServiceStub.retrieve.resolves([matchScoreSample]);

        // WHEN
        route = {
          params: {
            matchScoreId: '' + matchScoreSample.id,
          },
        };
        const wrapper = shallowMount(MatchScoreUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.matchScore).toMatchObject(matchScoreSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        matchScoreServiceStub.find.resolves(matchScoreSample);
        const wrapper = shallowMount(MatchScoreUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
