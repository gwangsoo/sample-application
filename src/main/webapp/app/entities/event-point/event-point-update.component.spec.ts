/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import EventPointUpdate from './event-point-update.vue';
import EventPointService from './event-point.service';
import AlertService from '@/shared/alert/alert.service';

import DivisionService from '@/entities/division/division.service';

type EventPointUpdateComponentType = InstanceType<typeof EventPointUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const eventPointSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<EventPointUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('EventPoint Management Update Component', () => {
    let comp: EventPointUpdateComponentType;
    let eventPointServiceStub: SinonStubbedInstance<EventPointService>;

    beforeEach(() => {
      route = {};
      eventPointServiceStub = sinon.createStubInstance<EventPointService>(EventPointService);
      eventPointServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          eventPointService: () => eventPointServiceStub,
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
        const wrapper = shallowMount(EventPointUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.eventPoint = eventPointSample;
        eventPointServiceStub.update.resolves(eventPointSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(eventPointServiceStub.update.calledWith(eventPointSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        eventPointServiceStub.create.resolves(entity);
        const wrapper = shallowMount(EventPointUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.eventPoint = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(eventPointServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        eventPointServiceStub.find.resolves(eventPointSample);
        eventPointServiceStub.retrieve.resolves([eventPointSample]);

        // WHEN
        route = {
          params: {
            eventPointId: '' + eventPointSample.id,
          },
        };
        const wrapper = shallowMount(EventPointUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.eventPoint).toMatchObject(eventPointSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        eventPointServiceStub.find.resolves(eventPointSample);
        const wrapper = shallowMount(EventPointUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
