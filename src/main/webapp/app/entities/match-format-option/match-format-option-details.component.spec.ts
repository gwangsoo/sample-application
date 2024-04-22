/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MatchFormatOptionDetails from './match-format-option-details.vue';
import MatchFormatOptionService from './match-format-option.service';
import AlertService from '@/shared/alert/alert.service';

type MatchFormatOptionDetailsComponentType = InstanceType<typeof MatchFormatOptionDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const matchFormatOptionSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('MatchFormatOption Management Detail Component', () => {
    let matchFormatOptionServiceStub: SinonStubbedInstance<MatchFormatOptionService>;
    let mountOptions: MountingOptions<MatchFormatOptionDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      matchFormatOptionServiceStub = sinon.createStubInstance<MatchFormatOptionService>(MatchFormatOptionService);

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
          matchFormatOptionService: () => matchFormatOptionServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        matchFormatOptionServiceStub.find.resolves(matchFormatOptionSample);
        route = {
          params: {
            matchFormatOptionId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(MatchFormatOptionDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.matchFormatOption).toMatchObject(matchFormatOptionSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        matchFormatOptionServiceStub.find.resolves(matchFormatOptionSample);
        const wrapper = shallowMount(MatchFormatOptionDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
