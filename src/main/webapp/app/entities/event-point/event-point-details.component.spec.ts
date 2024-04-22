/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import EventPointDetails from './event-point-details.vue';
import EventPointService from './event-point.service';
import AlertService from '@/shared/alert/alert.service';

type EventPointDetailsComponentType = InstanceType<typeof EventPointDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const eventPointSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('EventPoint Management Detail Component', () => {
    let eventPointServiceStub: SinonStubbedInstance<EventPointService>;
    let mountOptions: MountingOptions<EventPointDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      eventPointServiceStub = sinon.createStubInstance<EventPointService>(EventPointService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          eventPointService: () => eventPointServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        eventPointServiceStub.find.resolves(eventPointSample);
        route = {
          params: {
            eventPointId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(EventPointDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.eventPoint).toMatchObject(eventPointSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        eventPointServiceStub.find.resolves(eventPointSample);
        const wrapper = shallowMount(EventPointDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
