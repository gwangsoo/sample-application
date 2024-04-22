/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import AffiliatedInfoDetails from './affiliated-info-details.vue';
import AffiliatedInfoService from './affiliated-info.service';
import AlertService from '@/shared/alert/alert.service';

type AffiliatedInfoDetailsComponentType = InstanceType<typeof AffiliatedInfoDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const affiliatedInfoSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('AffiliatedInfo Management Detail Component', () => {
    let affiliatedInfoServiceStub: SinonStubbedInstance<AffiliatedInfoService>;
    let mountOptions: MountingOptions<AffiliatedInfoDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      affiliatedInfoServiceStub = sinon.createStubInstance<AffiliatedInfoService>(AffiliatedInfoService);

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
          affiliatedInfoService: () => affiliatedInfoServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        affiliatedInfoServiceStub.find.resolves(affiliatedInfoSample);
        route = {
          params: {
            affiliatedInfoId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(AffiliatedInfoDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.affiliatedInfo).toMatchObject(affiliatedInfoSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        affiliatedInfoServiceStub.find.resolves(affiliatedInfoSample);
        const wrapper = shallowMount(AffiliatedInfoDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
