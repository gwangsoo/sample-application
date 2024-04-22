/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MatchUpdate from './match-update.vue';
import MatchService from './match.service';
import AlertService from '@/shared/alert/alert.service';

import MatchTeamService from '@/entities/match-team/match-team.service';
import MachineService from '@/entities/machine/machine.service';
import DivisionService from '@/entities/division/division.service';

type MatchUpdateComponentType = InstanceType<typeof MatchUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const matchSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<MatchUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Match Management Update Component', () => {
    let comp: MatchUpdateComponentType;
    let matchServiceStub: SinonStubbedInstance<MatchService>;

    beforeEach(() => {
      route = {};
      matchServiceStub = sinon.createStubInstance<MatchService>(MatchService);
      matchServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          matchService: () => matchServiceStub,
          matchTeamService: () =>
            sinon.createStubInstance<MatchTeamService>(MatchTeamService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          machineService: () =>
            sinon.createStubInstance<MachineService>(MachineService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          divisionService: () =>
            sinon.createStubInstance<DivisionService>(DivisionService, {
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
        const wrapper = shallowMount(MatchUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.match = matchSample;
        matchServiceStub.update.resolves(matchSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchServiceStub.update.calledWith(matchSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        matchServiceStub.create.resolves(entity);
        const wrapper = shallowMount(MatchUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.match = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        matchServiceStub.find.resolves(matchSample);
        matchServiceStub.retrieve.resolves([matchSample]);

        // WHEN
        route = {
          params: {
            matchId: '' + matchSample.id,
          },
        };
        const wrapper = shallowMount(MatchUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.match).toMatchObject(matchSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        matchServiceStub.find.resolves(matchSample);
        const wrapper = shallowMount(MatchUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
