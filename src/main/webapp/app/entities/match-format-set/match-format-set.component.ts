import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import MatchFormatSetService from './match-format-set.service';
import { type IMatchFormatSet } from '@/shared/model/match-format-set.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchFormatSet',
  setup() {
    const { t: t$ } = useI18n();
    const matchFormatSetService = inject('matchFormatSetService', () => new MatchFormatSetService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const matchFormatSets: Ref<IMatchFormatSet[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveMatchFormatSets = async () => {
      isFetching.value = true;
      try {
        const res = await matchFormatSetService().retrieve();
        matchFormatSets.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveMatchFormatSets();
    };

    onMounted(async () => {
      await retrieveMatchFormatSets();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IMatchFormatSet) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeMatchFormatSet = async () => {
      try {
        await matchFormatSetService().delete(removeId.value);
        const message = t$('tossApp.matchFormatSet.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveMatchFormatSets();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      matchFormatSets,
      handleSyncList,
      isFetching,
      retrieveMatchFormatSets,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeMatchFormatSet,
      t$,
    };
  },
});
