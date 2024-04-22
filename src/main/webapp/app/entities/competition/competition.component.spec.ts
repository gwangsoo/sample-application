/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Competition from './competition.vue';
import CompetitionService from './competition.service';
import AlertService from '@/shared/alert/alert.service';

type CompetitionComponentType = InstanceType<typeof Competition>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Competition Management Component', () => {
    let competitionServiceStub: SinonStubbedInstance<CompetitionService>;
    let mountOptions: MountingOptions<CompetitionComponentType>['global'];

    beforeEach(() => {
      competitionServiceStub = sinon.createStubInstance<CompetitionService>(CompetitionService);
      competitionServiceStub.retrieve.resolves({ headers: {} });

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
          competitionService: () => competitionServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        competitionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(Competition, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(competitionServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.competitions[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: CompetitionComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Competition, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        competitionServiceStub.retrieve.reset();
        competitionServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        competitionServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeCompetition();
        await comp.$nextTick(); // clear components

        // THEN
        expect(competitionServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(competitionServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
