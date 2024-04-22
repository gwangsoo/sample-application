/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import GameUpdate from './game-update.vue';
import GameService from './game.service';
import AlertService from '@/shared/alert/alert.service';

type GameUpdateComponentType = InstanceType<typeof GameUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const gameSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<GameUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Game Management Update Component', () => {
    let comp: GameUpdateComponentType;
    let gameServiceStub: SinonStubbedInstance<GameService>;

    beforeEach(() => {
      route = {};
      gameServiceStub = sinon.createStubInstance<GameService>(GameService);
      gameServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          gameService: () => gameServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(GameUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.game = gameSample;
        gameServiceStub.update.resolves(gameSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(gameServiceStub.update.calledWith(gameSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        gameServiceStub.create.resolves(entity);
        const wrapper = shallowMount(GameUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.game = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(gameServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        gameServiceStub.find.resolves(gameSample);
        gameServiceStub.retrieve.resolves([gameSample]);

        // WHEN
        route = {
          params: {
            gameId: '' + gameSample.id,
          },
        };
        const wrapper = shallowMount(GameUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.game).toMatchObject(gameSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        gameServiceStub.find.resolves(gameSample);
        const wrapper = shallowMount(GameUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
