import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import PaymentInfoService from './payment-info.service';
import { useDateFormat } from '@/shared/composables';
import { type IPaymentInfo } from '@/shared/model/payment-info.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PaymentInfoDetails',
  setup() {
    const dateFormat = useDateFormat();
    const paymentInfoService = inject('paymentInfoService', () => new PaymentInfoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const paymentInfo: Ref<IPaymentInfo> = ref({});

    const retrievePaymentInfo = async paymentInfoId => {
      try {
        const res = await paymentInfoService().find(paymentInfoId);
        paymentInfo.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.paymentInfoId) {
      retrievePaymentInfo(route.params.paymentInfoId);
    }

    return {
      ...dateFormat,
      alertService,
      paymentInfo,

      previousState,
      t$: useI18n().t,
    };
  },
});
