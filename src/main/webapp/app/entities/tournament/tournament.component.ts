import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import TournamentService from './tournament.service';
import { type ITournament } from '@/shared/model/tournament.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Tournament',
  setup() {
    const { t: t$ } = useI18n();
    const tournamentService = inject('tournamentService', () => new TournamentService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const tournaments: Ref<ITournament[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveTournaments = async () => {
      isFetching.value = true;
      try {
        const res = await tournamentService().retrieve();
        tournaments.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveTournaments();
    };

    onMounted(async () => {
      await retrieveTournaments();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: ITournament) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeTournament = async () => {
      try {
        await tournamentService().delete(removeId.value);
        const message = t$('tossApp.tournament.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveTournaments();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      tournaments,
      handleSyncList,
      isFetching,
      retrieveTournaments,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeTournament,
      t$,
    };
  },
});
