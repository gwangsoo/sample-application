/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import PaymentInfoDetails from './payment-info-details.vue';
import PaymentInfoService from './payment-info.service';
import AlertService from '@/shared/alert/alert.service';

type PaymentInfoDetailsComponentType = InstanceType<typeof PaymentInfoDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const paymentInfoSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('PaymentInfo Management Detail Component', () => {
    let paymentInfoServiceStub: SinonStubbedInstance<PaymentInfoService>;
    let mountOptions: MountingOptions<PaymentInfoDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      paymentInfoServiceStub = sinon.createStubInstance<PaymentInfoService>(PaymentInfoService);

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
          paymentInfoService: () => paymentInfoServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        paymentInfoServiceStub.find.resolves(paymentInfoSample);
        route = {
          params: {
            paymentInfoId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(PaymentInfoDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.paymentInfo).toMatchObject(paymentInfoSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        paymentInfoServiceStub.find.resolves(paymentInfoSample);
        const wrapper = shallowMount(PaymentInfoDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
