/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MatchFormatGameDetails from './match-format-game-details.vue';
import MatchFormatGameService from './match-format-game.service';
import AlertService from '@/shared/alert/alert.service';

type MatchFormatGameDetailsComponentType = InstanceType<typeof MatchFormatGameDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const matchFormatGameSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('MatchFormatGame Management Detail Component', () => {
    let matchFormatGameServiceStub: SinonStubbedInstance<MatchFormatGameService>;
    let mountOptions: MountingOptions<MatchFormatGameDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      matchFormatGameServiceStub = sinon.createStubInstance<MatchFormatGameService>(MatchFormatGameService);

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
          matchFormatGameService: () => matchFormatGameServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        matchFormatGameServiceStub.find.resolves(matchFormatGameSample);
        route = {
          params: {
            matchFormatGameId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(MatchFormatGameDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.matchFormatGame).toMatchObject(matchFormatGameSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        matchFormatGameServiceStub.find.resolves(matchFormatGameSample);
        const wrapper = shallowMount(MatchFormatGameDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
