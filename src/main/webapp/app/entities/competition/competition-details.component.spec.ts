/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CompetitionDetails from './competition-details.vue';
import CompetitionService from './competition.service';
import AlertService from '@/shared/alert/alert.service';

type CompetitionDetailsComponentType = InstanceType<typeof CompetitionDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const competitionSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Competition Management Detail Component', () => {
    let competitionServiceStub: SinonStubbedInstance<CompetitionService>;
    let mountOptions: MountingOptions<CompetitionDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      competitionServiceStub = sinon.createStubInstance<CompetitionService>(CompetitionService);

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
          competitionService: () => competitionServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        competitionServiceStub.find.resolves(competitionSample);
        route = {
          params: {
            competitionId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(CompetitionDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.competition).toMatchObject(competitionSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        competitionServiceStub.find.resolves(competitionSample);
        const wrapper = shallowMount(CompetitionDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
