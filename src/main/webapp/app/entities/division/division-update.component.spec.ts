/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import DivisionUpdate from './division-update.vue';
import DivisionService from './division.service';
import AlertService from '@/shared/alert/alert.service';

import TournamentService from '@/entities/tournament/tournament.service';

type DivisionUpdateComponentType = InstanceType<typeof DivisionUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const divisionSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<DivisionUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Division Management Update Component', () => {
    let comp: DivisionUpdateComponentType;
    let divisionServiceStub: SinonStubbedInstance<DivisionService>;

    beforeEach(() => {
      route = {};
      divisionServiceStub = sinon.createStubInstance<DivisionService>(DivisionService);
      divisionServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          divisionService: () => divisionServiceStub,
          tournamentService: () =>
            sinon.createStubInstance<TournamentService>(TournamentService, {
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
        const wrapper = shallowMount(DivisionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.division = divisionSample;
        divisionServiceStub.update.resolves(divisionSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(divisionServiceStub.update.calledWith(divisionSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        divisionServiceStub.create.resolves(entity);
        const wrapper = shallowMount(DivisionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.division = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(divisionServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        divisionServiceStub.find.resolves(divisionSample);
        divisionServiceStub.retrieve.resolves([divisionSample]);

        // WHEN
        route = {
          params: {
            divisionId: '' + divisionSample.id,
          },
        };
        const wrapper = shallowMount(DivisionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.division).toMatchObject(divisionSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        divisionServiceStub.find.resolves(divisionSample);
        const wrapper = shallowMount(DivisionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
