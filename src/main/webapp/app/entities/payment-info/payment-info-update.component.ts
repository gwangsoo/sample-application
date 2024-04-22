import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import PaymentInfoService from './payment-info.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IPaymentInfo, PaymentInfo } from '@/shared/model/payment-info.model';
import { PaymentStatusType } from '@/shared/model/enumerations/payment-status-type.model';
import { PaymentMethodType } from '@/shared/model/enumerations/payment-method-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PaymentInfoUpdate',
  setup() {
    const paymentInfoService = inject('paymentInfoService', () => new PaymentInfoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const paymentInfo: Ref<IPaymentInfo> = ref(new PaymentInfo());
    const paymentStatusTypeValues: Ref<string[]> = ref(Object.keys(PaymentStatusType));
    const paymentMethodTypeValues: Ref<string[]> = ref(Object.keys(PaymentMethodType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrievePaymentInfo = async paymentInfoId => {
      try {
        const res = await paymentInfoService().find(paymentInfoId);
        res.paymentCompletedDate = new Date(res.paymentCompletedDate);
        paymentInfo.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.paymentInfoId) {
      retrievePaymentInfo(route.params.paymentInfoId);
    }

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      orderNumber: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 50 }).toString(), 50),
      },
      paymentCompletedDate: {},
      status: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      paymentMethodType: {},
      pgTID: {},
      pgStatus: {
        integer: validations.integer(t$('entity.validation.number').toString()),
        min: validations.minValue(t$('entity.validation.min', { min: 1 }).toString(), 1),
      },
      pgDetail: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 255 }).toString(), 255),
      },
      payer: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 50 }).toString(), 50),
      },
      payerPhone: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 20 }).toString(), 20),
      },
    };
    const v$ = useVuelidate(validationRules, paymentInfo as any);
    v$.value.$validate();

    return {
      paymentInfoService,
      alertService,
      paymentInfo,
      previousState,
      paymentStatusTypeValues,
      paymentMethodTypeValues,
      isSaving,
      currentLanguage,
      v$,
      ...useDateFormat({ entityRef: paymentInfo }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.paymentInfo.id) {
        this.paymentInfoService()
          .update(this.paymentInfo)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.paymentInfo.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.paymentInfoService()
          .create(this.paymentInfo)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.paymentInfo.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
