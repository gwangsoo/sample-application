import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import MatchFormatService from './match-format.service';
import { type IMatchFormat } from '@/shared/model/match-format.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchFormat',
  setup() {
    const { t: t$ } = useI18n();
    const matchFormatService = inject('matchFormatService', () => new MatchFormatService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const matchFormats: Ref<IMatchFormat[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveMatchFormats = async () => {
      isFetching.value = true;
      try {
        const res = await matchFormatService().retrieve();
        matchFormats.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveMatchFormats();
    };

    onMounted(async () => {
      await retrieveMatchFormats();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IMatchFormat) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeMatchFormat = async () => {
      try {
        await matchFormatService().delete(removeId.value);
        const message = t$('tossApp.matchFormat.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveMatchFormats();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      matchFormats,
      handleSyncList,
      isFetching,
      retrieveMatchFormats,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeMatchFormat,
      t$,
    };
  },
});
