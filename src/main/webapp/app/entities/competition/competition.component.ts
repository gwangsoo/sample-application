import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import CompetitionService from './competition.service';
import { type ICompetition } from '@/shared/model/competition.model';
import { useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Competition',
  setup() {
    const { t: t$ } = useI18n();
    const dateFormat = useDateFormat();
    const competitionService = inject('competitionService', () => new CompetitionService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const competitions: Ref<ICompetition[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveCompetitions = async () => {
      isFetching.value = true;
      try {
        const res = await competitionService().retrieve();
        competitions.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveCompetitions();
    };

    onMounted(async () => {
      await retrieveCompetitions();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: ICompetition) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeCompetition = async () => {
      try {
        await competitionService().delete(removeId.value);
        const message = t$('tossApp.competition.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveCompetitions();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      competitions,
      handleSyncList,
      isFetching,
      retrieveCompetitions,
      clear,
      ...dateFormat,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeCompetition,
      t$,
    };
  },
});
