/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import EventPoint from './event-point.vue';
import EventPointService from './event-point.service';
import AlertService from '@/shared/alert/alert.service';

type EventPointComponentType = InstanceType<typeof EventPoint>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('EventPoint Management Component', () => {
    let eventPointServiceStub: SinonStubbedInstance<EventPointService>;
    let mountOptions: MountingOptions<EventPointComponentType>['global'];

    beforeEach(() => {
      eventPointServiceStub = sinon.createStubInstance<EventPointService>(EventPointService);
      eventPointServiceStub.retrieve.resolves({ headers: {} });

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
          eventPointService: () => eventPointServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        eventPointServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(EventPoint, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(eventPointServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.eventPoints[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: EventPointComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(EventPoint, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        eventPointServiceStub.retrieve.reset();
        eventPointServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        eventPointServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeEventPoint();
        await comp.$nextTick(); // clear components

        // THEN
        expect(eventPointServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(eventPointServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
