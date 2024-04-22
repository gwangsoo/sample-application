/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MatchFormatLegDetails from './match-format-leg-details.vue';
import MatchFormatLegService from './match-format-leg.service';
import AlertService from '@/shared/alert/alert.service';

type MatchFormatLegDetailsComponentType = InstanceType<typeof MatchFormatLegDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const matchFormatLegSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('MatchFormatLeg Management Detail Component', () => {
    let matchFormatLegServiceStub: SinonStubbedInstance<MatchFormatLegService>;
    let mountOptions: MountingOptions<MatchFormatLegDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      matchFormatLegServiceStub = sinon.createStubInstance<MatchFormatLegService>(MatchFormatLegService);

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
          matchFormatLegService: () => matchFormatLegServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        matchFormatLegServiceStub.find.resolves(matchFormatLegSample);
        route = {
          params: {
            matchFormatLegId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(MatchFormatLegDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.matchFormatLeg).toMatchObject(matchFormatLegSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        matchFormatLegServiceStub.find.resolves(matchFormatLegSample);
        const wrapper = shallowMount(MatchFormatLegDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
