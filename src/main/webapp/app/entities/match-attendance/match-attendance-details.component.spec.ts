/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MatchAttendanceDetails from './match-attendance-details.vue';
import MatchAttendanceService from './match-attendance.service';
import AlertService from '@/shared/alert/alert.service';

type MatchAttendanceDetailsComponentType = InstanceType<typeof MatchAttendanceDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const matchAttendanceSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('MatchAttendance Management Detail Component', () => {
    let matchAttendanceServiceStub: SinonStubbedInstance<MatchAttendanceService>;
    let mountOptions: MountingOptions<MatchAttendanceDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      matchAttendanceServiceStub = sinon.createStubInstance<MatchAttendanceService>(MatchAttendanceService);

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
          matchAttendanceService: () => matchAttendanceServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        matchAttendanceServiceStub.find.resolves(matchAttendanceSample);
        route = {
          params: {
            matchAttendanceId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(MatchAttendanceDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.matchAttendance).toMatchObject(matchAttendanceSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        matchAttendanceServiceStub.find.resolves(matchAttendanceSample);
        const wrapper = shallowMount(MatchAttendanceDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
