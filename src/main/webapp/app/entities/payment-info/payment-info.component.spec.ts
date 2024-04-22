/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import PaymentInfo from './payment-info.vue';
import PaymentInfoService from './payment-info.service';
import AlertService from '@/shared/alert/alert.service';

type PaymentInfoComponentType = InstanceType<typeof PaymentInfo>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('PaymentInfo Management Component', () => {
    let paymentInfoServiceStub: SinonStubbedInstance<PaymentInfoService>;
    let mountOptions: MountingOptions<PaymentInfoComponentType>['global'];

    beforeEach(() => {
      paymentInfoServiceStub = sinon.createStubInstance<PaymentInfoService>(PaymentInfoService);
      paymentInfoServiceStub.retrieve.resolves({ headers: {} });

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
          paymentInfoService: () => paymentInfoServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        paymentInfoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(PaymentInfo, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(paymentInfoServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.paymentInfos[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: PaymentInfoComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(PaymentInfo, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        paymentInfoServiceStub.retrieve.reset();
        paymentInfoServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        paymentInfoServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removePaymentInfo();
        await comp.$nextTick(); // clear components

        // THEN
        expect(paymentInfoServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(paymentInfoServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
