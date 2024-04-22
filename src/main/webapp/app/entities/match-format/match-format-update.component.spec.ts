/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MatchFormatUpdate from './match-format-update.vue';
import MatchFormatService from './match-format.service';
import AlertService from '@/shared/alert/alert.service';

import DivisionService from '@/entities/division/division.service';

type MatchFormatUpdateComponentType = InstanceType<typeof MatchFormatUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const matchFormatSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<MatchFormatUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('MatchFormat Management Update Component', () => {
    let comp: MatchFormatUpdateComponentType;
    let matchFormatServiceStub: SinonStubbedInstance<MatchFormatService>;

    beforeEach(() => {
      route = {};
      matchFormatServiceStub = sinon.createStubInstance<MatchFormatService>(MatchFormatService);
      matchFormatServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          matchFormatService: () => matchFormatServiceStub,
          divisionService: () =>
            sinon.createStubInstance<DivisionService>(DivisionService, {
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
        const wrapper = shallowMount(MatchFormatUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.matchFormat = matchFormatSample;
        matchFormatServiceStub.update.resolves(matchFormatSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchFormatServiceStub.update.calledWith(matchFormatSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        matchFormatServiceStub.create.resolves(entity);
        const wrapper = shallowMount(MatchFormatUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.matchFormat = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchFormatServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        matchFormatServiceStub.find.resolves(matchFormatSample);
        matchFormatServiceStub.retrieve.resolves([matchFormatSample]);

        // WHEN
        route = {
          params: {
            matchFormatId: '' + matchFormatSample.id,
          },
        };
        const wrapper = shallowMount(MatchFormatUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.matchFormat).toMatchObject(matchFormatSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        matchFormatServiceStub.find.resolves(matchFormatSample);
        const wrapper = shallowMount(MatchFormatUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
