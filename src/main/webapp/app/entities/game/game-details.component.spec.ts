/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import GameDetails from './game-details.vue';
import GameService from './game.service';
import AlertService from '@/shared/alert/alert.service';

type GameDetailsComponentType = InstanceType<typeof GameDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const gameSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Game Management Detail Component', () => {
    let gameServiceStub: SinonStubbedInstance<GameService>;
    let mountOptions: MountingOptions<GameDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      gameServiceStub = sinon.createStubInstance<GameService>(GameService);

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
          gameService: () => gameServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        gameServiceStub.find.resolves(gameSample);
        route = {
          params: {
            gameId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(GameDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.game).toMatchObject(gameSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        gameServiceStub.find.resolves(gameSample);
        const wrapper = shallowMount(GameDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
