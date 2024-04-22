/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TournamentUpdate from './tournament-update.vue';
import TournamentService from './tournament.service';
import AlertService from '@/shared/alert/alert.service';

import EntryFeeService from '@/entities/entry-fee/entry-fee.service';
import CompetitionService from '@/entities/competition/competition.service';

type TournamentUpdateComponentType = InstanceType<typeof TournamentUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const tournamentSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<TournamentUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Tournament Management Update Component', () => {
    let comp: TournamentUpdateComponentType;
    let tournamentServiceStub: SinonStubbedInstance<TournamentService>;

    beforeEach(() => {
      route = {};
      tournamentServiceStub = sinon.createStubInstance<TournamentService>(TournamentService);
      tournamentServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          tournamentService: () => tournamentServiceStub,
          entryFeeService: () =>
            sinon.createStubInstance<EntryFeeService>(EntryFeeService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          competitionService: () =>
            sinon.createStubInstance<CompetitionService>(CompetitionService, {
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
        const wrapper = shallowMount(TournamentUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.tournament = tournamentSample;
        tournamentServiceStub.update.resolves(tournamentSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tournamentServiceStub.update.calledWith(tournamentSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        tournamentServiceStub.create.resolves(entity);
        const wrapper = shallowMount(TournamentUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.tournament = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tournamentServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        tournamentServiceStub.find.resolves(tournamentSample);
        tournamentServiceStub.retrieve.resolves([tournamentSample]);

        // WHEN
        route = {
          params: {
            tournamentId: '' + tournamentSample.id,
          },
        };
        const wrapper = shallowMount(TournamentUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.tournament).toMatchObject(tournamentSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        tournamentServiceStub.find.resolves(tournamentSample);
        const wrapper = shallowMount(TournamentUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
