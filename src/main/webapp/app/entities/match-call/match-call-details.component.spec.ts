/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MatchCallDetails from './match-call-details.vue';
import MatchCallService from './match-call.service';
import AlertService from '@/shared/alert/alert.service';

type MatchCallDetailsComponentType = InstanceType<typeof MatchCallDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const matchCallSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('MatchCall Management Detail Component', () => {
    let matchCallServiceStub: SinonStubbedInstance<MatchCallService>;
    let mountOptions: MountingOptions<MatchCallDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      matchCallServiceStub = sinon.createStubInstance<MatchCallService>(MatchCallService);

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
          matchCallService: () => matchCallServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        matchCallServiceStub.find.resolves(matchCallSample);
        route = {
          params: {
            matchCallId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(MatchCallDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.matchCall).toMatchObject(matchCallSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        matchCallServiceStub.find.resolves(matchCallSample);
        const wrapper = shallowMount(MatchCallDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
