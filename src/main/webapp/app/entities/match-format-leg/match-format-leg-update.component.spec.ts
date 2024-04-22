/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MatchFormatLegUpdate from './match-format-leg-update.vue';
import MatchFormatLegService from './match-format-leg.service';
import AlertService from '@/shared/alert/alert.service';

import MatchFormatOptionService from '@/entities/match-format-option/match-format-option.service';
import MatchFormatSetService from '@/entities/match-format-set/match-format-set.service';

type MatchFormatLegUpdateComponentType = InstanceType<typeof MatchFormatLegUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const matchFormatLegSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<MatchFormatLegUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('MatchFormatLeg Management Update Component', () => {
    let comp: MatchFormatLegUpdateComponentType;
    let matchFormatLegServiceStub: SinonStubbedInstance<MatchFormatLegService>;

    beforeEach(() => {
      route = {};
      matchFormatLegServiceStub = sinon.createStubInstance<MatchFormatLegService>(MatchFormatLegService);
      matchFormatLegServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          matchFormatLegService: () => matchFormatLegServiceStub,
          matchFormatOptionService: () =>
            sinon.createStubInstance<MatchFormatOptionService>(MatchFormatOptionService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          matchFormatSetService: () =>
            sinon.createStubInstance<MatchFormatSetService>(MatchFormatSetService, {
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
        const wrapper = shallowMount(MatchFormatLegUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.matchFormatLeg = matchFormatLegSample;
        matchFormatLegServiceStub.update.resolves(matchFormatLegSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchFormatLegServiceStub.update.calledWith(matchFormatLegSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        matchFormatLegServiceStub.create.resolves(entity);
        const wrapper = shallowMount(MatchFormatLegUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.matchFormatLeg = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchFormatLegServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        matchFormatLegServiceStub.find.resolves(matchFormatLegSample);
        matchFormatLegServiceStub.retrieve.resolves([matchFormatLegSample]);

        // WHEN
        route = {
          params: {
            matchFormatLegId: '' + matchFormatLegSample.id,
          },
        };
        const wrapper = shallowMount(MatchFormatLegUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.matchFormatLeg).toMatchObject(matchFormatLegSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        matchFormatLegServiceStub.find.resolves(matchFormatLegSample);
        const wrapper = shallowMount(MatchFormatLegUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
