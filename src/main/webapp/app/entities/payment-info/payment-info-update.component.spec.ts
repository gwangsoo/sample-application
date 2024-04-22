/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import PaymentInfoUpdate from './payment-info-update.vue';
import PaymentInfoService from './payment-info.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

type PaymentInfoUpdateComponentType = InstanceType<typeof PaymentInfoUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const paymentInfoSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<PaymentInfoUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('PaymentInfo Management Update Component', () => {
    let comp: PaymentInfoUpdateComponentType;
    let paymentInfoServiceStub: SinonStubbedInstance<PaymentInfoService>;

    beforeEach(() => {
      route = {};
      paymentInfoServiceStub = sinon.createStubInstance<PaymentInfoService>(PaymentInfoService);
      paymentInfoServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          paymentInfoService: () => paymentInfoServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('load', () => {
      beforeEach(() => {
        const wrapper = shallowMount(PaymentInfoUpdate, { global: mountOptions });
        comp = wrapper.vm;
      });
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(PaymentInfoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.paymentInfo = paymentInfoSample;
        paymentInfoServiceStub.update.resolves(paymentInfoSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(paymentInfoServiceStub.update.calledWith(paymentInfoSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        paymentInfoServiceStub.create.resolves(entity);
        const wrapper = shallowMount(PaymentInfoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.paymentInfo = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(paymentInfoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        paymentInfoServiceStub.find.resolves(paymentInfoSample);
        paymentInfoServiceStub.retrieve.resolves([paymentInfoSample]);

        // WHEN
        route = {
          params: {
            paymentInfoId: '' + paymentInfoSample.id,
          },
        };
        const wrapper = shallowMount(PaymentInfoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.paymentInfo).toMatchObject(paymentInfoSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        paymentInfoServiceStub.find.resolves(paymentInfoSample);
        const wrapper = shallowMount(PaymentInfoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
