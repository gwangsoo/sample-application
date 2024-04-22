/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MatchTeamUpdate from './match-team-update.vue';
import MatchTeamService from './match-team.service';
import AlertService from '@/shared/alert/alert.service';

import TeamService from '@/entities/team/team.service';

type MatchTeamUpdateComponentType = InstanceType<typeof MatchTeamUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const matchTeamSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<MatchTeamUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('MatchTeam Management Update Component', () => {
    let comp: MatchTeamUpdateComponentType;
    let matchTeamServiceStub: SinonStubbedInstance<MatchTeamService>;

    beforeEach(() => {
      route = {};
      matchTeamServiceStub = sinon.createStubInstance<MatchTeamService>(MatchTeamService);
      matchTeamServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          matchTeamService: () => matchTeamServiceStub,
          teamService: () =>
            sinon.createStubInstance<TeamService>(TeamService, {
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
        const wrapper = shallowMount(MatchTeamUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.matchTeam = matchTeamSample;
        matchTeamServiceStub.update.resolves(matchTeamSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchTeamServiceStub.update.calledWith(matchTeamSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        matchTeamServiceStub.create.resolves(entity);
        const wrapper = shallowMount(MatchTeamUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.matchTeam = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchTeamServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        matchTeamServiceStub.find.resolves(matchTeamSample);
        matchTeamServiceStub.retrieve.resolves([matchTeamSample]);

        // WHEN
        route = {
          params: {
            matchTeamId: '' + matchTeamSample.id,
          },
        };
        const wrapper = shallowMount(MatchTeamUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.matchTeam).toMatchObject(matchTeamSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        matchTeamServiceStub.find.resolves(matchTeamSample);
        const wrapper = shallowMount(MatchTeamUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
