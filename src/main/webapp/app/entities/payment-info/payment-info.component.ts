import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import PaymentInfoService from './payment-info.service';
import { type IPaymentInfo } from '@/shared/model/payment-info.model';
import { useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PaymentInfo',
  setup() {
    const { t: t$ } = useI18n();
    const dateFormat = useDateFormat();
    const paymentInfoService = inject('paymentInfoService', () => new PaymentInfoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const paymentInfos: Ref<IPaymentInfo[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrievePaymentInfos = async () => {
      isFetching.value = true;
      try {
        const res = await paymentInfoService().retrieve();
        paymentInfos.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrievePaymentInfos();
    };

    onMounted(async () => {
      await retrievePaymentInfos();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IPaymentInfo) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removePaymentInfo = async () => {
      try {
        await paymentInfoService().delete(removeId.value);
        const message = t$('tossApp.paymentInfo.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrievePaymentInfos();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      paymentInfos,
      handleSyncList,
      isFetching,
      retrievePaymentInfos,
      clear,
      ...dateFormat,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removePaymentInfo,
      t$,
    };
  },
});
