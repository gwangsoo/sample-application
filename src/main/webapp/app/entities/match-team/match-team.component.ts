import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import MatchTeamService from './match-team.service';
import { type IMatchTeam } from '@/shared/model/match-team.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchTeam',
  setup() {
    const { t: t$ } = useI18n();
    const matchTeamService = inject('matchTeamService', () => new MatchTeamService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const matchTeams: Ref<IMatchTeam[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveMatchTeams = async () => {
      isFetching.value = true;
      try {
        const res = await matchTeamService().retrieve();
        matchTeams.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveMatchTeams();
    };

    onMounted(async () => {
      await retrieveMatchTeams();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IMatchTeam) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeMatchTeam = async () => {
      try {
        await matchTeamService().delete(removeId.value);
        const message = t$('tossApp.matchTeam.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveMatchTeams();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      matchTeams,
      handleSyncList,
      isFetching,
      retrieveMatchTeams,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeMatchTeam,
      t$,
    };
  },
});
