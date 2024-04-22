/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MatchFormatOptionUpdate from './match-format-option-update.vue';
import MatchFormatOptionService from './match-format-option.service';
import AlertService from '@/shared/alert/alert.service';

import MatchFormatService from '@/entities/match-format/match-format.service';

type MatchFormatOptionUpdateComponentType = InstanceType<typeof MatchFormatOptionUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const matchFormatOptionSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<MatchFormatOptionUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('MatchFormatOption Management Update Component', () => {
    let comp: MatchFormatOptionUpdateComponentType;
    let matchFormatOptionServiceStub: SinonStubbedInstance<MatchFormatOptionService>;

    beforeEach(() => {
      route = {};
      matchFormatOptionServiceStub = sinon.createStubInstance<MatchFormatOptionService>(MatchFormatOptionService);
      matchFormatOptionServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          matchFormatOptionService: () => matchFormatOptionServiceStub,
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
        const wrapper = shallowMount(MatchFormatOptionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.matchFormatOption = matchFormatOptionSample;
        matchFormatOptionServiceStub.update.resolves(matchFormatOptionSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchFormatOptionServiceStub.update.calledWith(matchFormatOptionSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        matchFormatOptionServiceStub.create.resolves(entity);
        const wrapper = shallowMount(MatchFormatOptionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.matchFormatOption = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchFormatOptionServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        matchFormatOptionServiceStub.find.resolves(matchFormatOptionSample);
        matchFormatOptionServiceStub.retrieve.resolves([matchFormatOptionSample]);

        // WHEN
        route = {
          params: {
            matchFormatOptionId: '' + matchFormatOptionSample.id,
          },
        };
        const wrapper = shallowMount(MatchFormatOptionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.matchFormatOption).toMatchObject(matchFormatOptionSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        matchFormatOptionServiceStub.find.resolves(matchFormatOptionSample);
        const wrapper = shallowMount(MatchFormatOptionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
