import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import MatchScoreService from './match-score.service';
import { type IMatchScore } from '@/shared/model/match-score.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchScore',
  setup() {
    const { t: t$ } = useI18n();
    const matchScoreService = inject('matchScoreService', () => new MatchScoreService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const matchScores: Ref<IMatchScore[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveMatchScores = async () => {
      isFetching.value = true;
      try {
        const res = await matchScoreService().retrieve();
        matchScores.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveMatchScores();
    };

    onMounted(async () => {
      await retrieveMatchScores();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IMatchScore) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeMatchScore = async () => {
      try {
        await matchScoreService().delete(removeId.value);
        const message = t$('tossApp.matchScore.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveMatchScores();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      matchScores,
      handleSyncList,
      isFetching,
      retrieveMatchScores,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeMatchScore,
      t$,
    };
  },
});
