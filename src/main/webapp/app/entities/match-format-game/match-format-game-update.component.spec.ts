/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MatchFormatGameUpdate from './match-format-game-update.vue';
import MatchFormatGameService from './match-format-game.service';
import AlertService from '@/shared/alert/alert.service';

import GameService from '@/entities/game/game.service';
import MatchFormatService from '@/entities/match-format/match-format.service';

type MatchFormatGameUpdateComponentType = InstanceType<typeof MatchFormatGameUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const matchFormatGameSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<MatchFormatGameUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('MatchFormatGame Management Update Component', () => {
    let comp: MatchFormatGameUpdateComponentType;
    let matchFormatGameServiceStub: SinonStubbedInstance<MatchFormatGameService>;

    beforeEach(() => {
      route = {};
      matchFormatGameServiceStub = sinon.createStubInstance<MatchFormatGameService>(MatchFormatGameService);
      matchFormatGameServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          matchFormatGameService: () => matchFormatGameServiceStub,
          gameService: () =>
            sinon.createStubInstance<GameService>(GameService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          matchFormatService: () =>
            sinon.createStubInstance<MatchFormatService>(MatchFormatService, {
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
        const wrapper = shallowMount(MatchFormatGameUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.matchFormatGame = matchFormatGameSample;
        matchFormatGameServiceStub.update.resolves(matchFormatGameSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchFormatGameServiceStub.update.calledWith(matchFormatGameSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        matchFormatGameServiceStub.create.resolves(entity);
        const wrapper = shallowMount(MatchFormatGameUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.matchFormatGame = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchFormatGameServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        matchFormatGameServiceStub.find.resolves(matchFormatGameSample);
        matchFormatGameServiceStub.retrieve.resolves([matchFormatGameSample]);

        // WHEN
        route = {
          params: {
            matchFormatGameId: '' + matchFormatGameSample.id,
          },
        };
        const wrapper = shallowMount(MatchFormatGameUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.matchFormatGame).toMatchObject(matchFormatGameSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        matchFormatGameServiceStub.find.resolves(matchFormatGameSample);
        const wrapper = shallowMount(MatchFormatGameUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
