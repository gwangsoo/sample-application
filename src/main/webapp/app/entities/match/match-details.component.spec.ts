/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MatchDetails from './match-details.vue';
import MatchService from './match.service';
import AlertService from '@/shared/alert/alert.service';

type MatchDetailsComponentType = InstanceType<typeof MatchDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const matchSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Match Management Detail Component', () => {
    let matchServiceStub: SinonStubbedInstance<MatchService>;
    let mountOptions: MountingOptions<MatchDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      matchServiceStub = sinon.createStubInstance<MatchService>(MatchService);

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
          matchService: () => matchServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        matchServiceStub.find.resolves(matchSample);
        route = {
          params: {
            matchId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(MatchDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.match).toMatchObject(matchSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        matchServiceStub.find.resolves(matchSample);
        const wrapper = shallowMount(MatchDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
