import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import EntryFeeService from './entry-fee.service';
import { type IEntryFee } from '@/shared/model/entry-fee.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EntryFee',
  setup() {
    const { t: t$ } = useI18n();
    const entryFeeService = inject('entryFeeService', () => new EntryFeeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const entryFees: Ref<IEntryFee[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveEntryFees = async () => {
      isFetching.value = true;
      try {
        const res = await entryFeeService().retrieve();
        entryFees.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveEntryFees();
    };

    onMounted(async () => {
      await retrieveEntryFees();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IEntryFee) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeEntryFee = async () => {
      try {
        await entryFeeService().delete(removeId.value);
        const message = t$('tossApp.entryFee.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveEntryFees();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      entryFees,
      handleSyncList,
      isFetching,
      retrieveEntryFees,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeEntryFee,
      t$,
    };
  },
});
