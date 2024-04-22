/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MatchFormatSetUpdate from './match-format-set-update.vue';
import MatchFormatSetService from './match-format-set.service';
import AlertService from '@/shared/alert/alert.service';

import MatchFormatService from '@/entities/match-format/match-format.service';

type MatchFormatSetUpdateComponentType = InstanceType<typeof MatchFormatSetUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const matchFormatSetSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<MatchFormatSetUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('MatchFormatSet Management Update Component', () => {
    let comp: MatchFormatSetUpdateComponentType;
    let matchFormatSetServiceStub: SinonStubbedInstance<MatchFormatSetService>;

    beforeEach(() => {
      route = {};
      matchFormatSetServiceStub = sinon.createStubInstance<MatchFormatSetService>(MatchFormatSetService);
      matchFormatSetServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          matchFormatSetService: () => matchFormatSetServiceStub,
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
        const wrapper = shallowMount(MatchFormatSetUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.matchFormatSet = matchFormatSetSample;
        matchFormatSetServiceStub.update.resolves(matchFormatSetSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchFormatSetServiceStub.update.calledWith(matchFormatSetSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        matchFormatSetServiceStub.create.resolves(entity);
        const wrapper = shallowMount(MatchFormatSetUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.matchFormatSet = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchFormatSetServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        matchFormatSetServiceStub.find.resolves(matchFormatSetSample);
        matchFormatSetServiceStub.retrieve.resolves([matchFormatSetSample]);

        // WHEN
        route = {
          params: {
            matchFormatSetId: '' + matchFormatSetSample.id,
          },
        };
        const wrapper = shallowMount(MatchFormatSetUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.matchFormatSet).toMatchObject(matchFormatSetSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        matchFormatSetServiceStub.find.resolves(matchFormatSetSample);
        const wrapper = shallowMount(MatchFormatSetUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
