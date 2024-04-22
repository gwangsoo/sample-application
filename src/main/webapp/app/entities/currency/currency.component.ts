import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import CurrencyService from './currency.service';
import { type ICurrency } from '@/shared/model/currency.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Currency',
  setup() {
    const { t: t$ } = useI18n();
    const currencyService = inject('currencyService', () => new CurrencyService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const currencies: Ref<ICurrency[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveCurrencys = async () => {
      isFetching.value = true;
      try {
        const res = await currencyService().retrieve();
        currencies.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveCurrencys();
    };

    onMounted(async () => {
      await retrieveCurrencys();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: ICurrency) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeCurrency = async () => {
      try {
        await currencyService().delete(removeId.value);
        const message = t$('tossApp.currency.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveCurrencys();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      currencies,
      handleSyncList,
      isFetching,
      retrieveCurrencys,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeCurrency,
      t$,
    };
  },
});
