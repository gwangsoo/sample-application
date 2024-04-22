/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import MatchFormatGame from './match-format-game.vue';
import MatchFormatGameService from './match-format-game.service';
import AlertService from '@/shared/alert/alert.service';

type MatchFormatGameComponentType = InstanceType<typeof MatchFormatGame>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('MatchFormatGame Management Component', () => {
    let matchFormatGameServiceStub: SinonStubbedInstance<MatchFormatGameService>;
    let mountOptions: MountingOptions<MatchFormatGameComponentType>['global'];

    beforeEach(() => {
      matchFormatGameServiceStub = sinon.createStubInstance<MatchFormatGameService>(MatchFormatGameService);
      matchFormatGameServiceStub.retrieve.resolves({ headers: {} });

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
          matchFormatGameService: () => matchFormatGameServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        matchFormatGameServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(MatchFormatGame, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(matchFormatGameServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.matchFormatGames[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: MatchFormatGameComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(MatchFormatGame, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        matchFormatGameServiceStub.retrieve.reset();
        matchFormatGameServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        matchFormatGameServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeMatchFormatGame();
        await comp.$nextTick(); // clear components

        // THEN
        expect(matchFormatGameServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(matchFormatGameServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
