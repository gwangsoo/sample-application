import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import EntryService from './entry.service';
import { type IEntry } from '@/shared/model/entry.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entry',
  setup() {
    const { t: t$ } = useI18n();
    const entryService = inject('entryService', () => new EntryService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const entries: Ref<IEntry[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveEntrys = async () => {
      isFetching.value = true;
      try {
        const res = await entryService().retrieve();
        entries.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveEntrys();
    };

    onMounted(async () => {
      await retrieveEntrys();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IEntry) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeEntry = async () => {
      try {
        await entryService().delete(removeId.value);
        const message = t$('tossApp.entry.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveEntrys();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      entries,
      handleSyncList,
      isFetching,
      retrieveEntrys,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeEntry,
      t$,
    };
  },
});
