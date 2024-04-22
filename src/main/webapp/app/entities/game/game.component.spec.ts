/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Game from './game.vue';
import GameService from './game.service';
import AlertService from '@/shared/alert/alert.service';

type GameComponentType = InstanceType<typeof Game>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Game Management Component', () => {
    let gameServiceStub: SinonStubbedInstance<GameService>;
    let mountOptions: MountingOptions<GameComponentType>['global'];

    beforeEach(() => {
      gameServiceStub = sinon.createStubInstance<GameService>(GameService);
      gameServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          gameService: () => gameServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        gameServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(Game, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(gameServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.games[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: GameComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Game, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        gameServiceStub.retrieve.reset();
        gameServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        gameServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeGame();
        await comp.$nextTick(); // clear components

        // THEN
        expect(gameServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(gameServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
