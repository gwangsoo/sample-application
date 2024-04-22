/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import MatchAttendance from './match-attendance.vue';
import MatchAttendanceService from './match-attendance.service';
import AlertService from '@/shared/alert/alert.service';

type MatchAttendanceComponentType = InstanceType<typeof MatchAttendance>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('MatchAttendance Management Component', () => {
    let matchAttendanceServiceStub: SinonStubbedInstance<MatchAttendanceService>;
    let mountOptions: MountingOptions<MatchAttendanceComponentType>['global'];

    beforeEach(() => {
      matchAttendanceServiceStub = sinon.createStubInstance<MatchAttendanceService>(MatchAttendanceService);
      matchAttendanceServiceStub.retrieve.resolves({ headers: {} });

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
          matchAttendanceService: () => matchAttendanceServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        matchAttendanceServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(MatchAttendance, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(matchAttendanceServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.matchAttendances[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: MatchAttendanceComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(MatchAttendance, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        matchAttendanceServiceStub.retrieve.reset();
        matchAttendanceServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        matchAttendanceServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeMatchAttendance();
        await comp.$nextTick(); // clear components

        // THEN
        expect(matchAttendanceServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(matchAttendanceServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
