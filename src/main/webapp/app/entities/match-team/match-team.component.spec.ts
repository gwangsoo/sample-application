/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import MatchTeam from './match-team.vue';
import MatchTeamService from './match-team.service';
import AlertService from '@/shared/alert/alert.service';

type MatchTeamComponentType = InstanceType<typeof MatchTeam>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('MatchTeam Management Component', () => {
    let matchTeamServiceStub: SinonStubbedInstance<MatchTeamService>;
    let mountOptions: MountingOptions<MatchTeamComponentType>['global'];

    beforeEach(() => {
      matchTeamServiceStub = sinon.createStubInstance<MatchTeamService>(MatchTeamService);
      matchTeamServiceStub.retrieve.resolves({ headers: {} });

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
          matchTeamService: () => matchTeamServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        matchTeamServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(MatchTeam, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(matchTeamServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.matchTeams[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: MatchTeamComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(MatchTeam, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        matchTeamServiceStub.retrieve.reset();
        matchTeamServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        matchTeamServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeMatchTeam();
        await comp.$nextTick(); // clear components

        // THEN
        expect(matchTeamServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(matchTeamServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
