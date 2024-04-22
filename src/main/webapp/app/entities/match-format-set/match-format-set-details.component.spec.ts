/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MatchFormatSetDetails from './match-format-set-details.vue';
import MatchFormatSetService from './match-format-set.service';
import AlertService from '@/shared/alert/alert.service';

type MatchFormatSetDetailsComponentType = InstanceType<typeof MatchFormatSetDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const matchFormatSetSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('MatchFormatSet Management Detail Component', () => {
    let matchFormatSetServiceStub: SinonStubbedInstance<MatchFormatSetService>;
    let mountOptions: MountingOptions<MatchFormatSetDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      matchFormatSetServiceStub = sinon.createStubInstance<MatchFormatSetService>(MatchFormatSetService);

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
          matchFormatSetService: () => matchFormatSetServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        matchFormatSetServiceStub.find.resolves(matchFormatSetSample);
        route = {
          params: {
            matchFormatSetId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(MatchFormatSetDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.matchFormatSet).toMatchObject(matchFormatSetSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        matchFormatSetServiceStub.find.resolves(matchFormatSetSample);
        const wrapper = shallowMount(MatchFormatSetDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
