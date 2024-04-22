/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MatchFormatDetails from './match-format-details.vue';
import MatchFormatService from './match-format.service';
import AlertService from '@/shared/alert/alert.service';

type MatchFormatDetailsComponentType = InstanceType<typeof MatchFormatDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const matchFormatSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('MatchFormat Management Detail Component', () => {
    let matchFormatServiceStub: SinonStubbedInstance<MatchFormatService>;
    let mountOptions: MountingOptions<MatchFormatDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      matchFormatServiceStub = sinon.createStubInstance<MatchFormatService>(MatchFormatService);

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
          matchFormatService: () => matchFormatServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        matchFormatServiceStub.find.resolves(matchFormatSample);
        route = {
          params: {
            matchFormatId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(MatchFormatDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.matchFormat).toMatchObject(matchFormatSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        matchFormatServiceStub.find.resolves(matchFormatSample);
        const wrapper = shallowMount(MatchFormatDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
