/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MatchScoreDetails from './match-score-details.vue';
import MatchScoreService from './match-score.service';
import AlertService from '@/shared/alert/alert.service';

type MatchScoreDetailsComponentType = InstanceType<typeof MatchScoreDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const matchScoreSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('MatchScore Management Detail Component', () => {
    let matchScoreServiceStub: SinonStubbedInstance<MatchScoreService>;
    let mountOptions: MountingOptions<MatchScoreDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      matchScoreServiceStub = sinon.createStubInstance<MatchScoreService>(MatchScoreService);

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
          matchScoreService: () => matchScoreServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        matchScoreServiceStub.find.resolves(matchScoreSample);
        route = {
          params: {
            matchScoreId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(MatchScoreDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.matchScore).toMatchObject(matchScoreSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        matchScoreServiceStub.find.resolves(matchScoreSample);
        const wrapper = shallowMount(MatchScoreDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
