/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MatchTeamDetails from './match-team-details.vue';
import MatchTeamService from './match-team.service';
import AlertService from '@/shared/alert/alert.service';

type MatchTeamDetailsComponentType = InstanceType<typeof MatchTeamDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const matchTeamSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('MatchTeam Management Detail Component', () => {
    let matchTeamServiceStub: SinonStubbedInstance<MatchTeamService>;
    let mountOptions: MountingOptions<MatchTeamDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      matchTeamServiceStub = sinon.createStubInstance<MatchTeamService>(MatchTeamService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          matchTeamService: () => matchTeamServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        matchTeamServiceStub.find.resolves(matchTeamSample);
        route = {
          params: {
            matchTeamId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(MatchTeamDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.matchTeam).toMatchObject(matchTeamSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        matchTeamServiceStub.find.resolves(matchTeamSample);
        const wrapper = shallowMount(MatchTeamDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
