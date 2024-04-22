/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CurrencyDetails from './currency-details.vue';
import CurrencyService from './currency.service';
import AlertService from '@/shared/alert/alert.service';

type CurrencyDetailsComponentType = InstanceType<typeof CurrencyDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const currencySample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Currency Management Detail Component', () => {
    let currencyServiceStub: SinonStubbedInstance<CurrencyService>;
    let mountOptions: MountingOptions<CurrencyDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      currencyServiceStub = sinon.createStubInstance<CurrencyService>(CurrencyService);

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
          currencyService: () => currencyServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        currencyServiceStub.find.resolves(currencySample);
        route = {
          params: {
            currencyId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(CurrencyDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.currency).toMatchObject(currencySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        currencyServiceStub.find.resolves(currencySample);
        const wrapper = shallowMount(CurrencyDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
