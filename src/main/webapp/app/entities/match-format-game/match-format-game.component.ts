import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import MatchFormatGameService from './match-format-game.service';
import { type IMatchFormatGame } from '@/shared/model/match-format-game.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchFormatGame',
  setup() {
    const { t: t$ } = useI18n();
    const matchFormatGameService = inject('matchFormatGameService', () => new MatchFormatGameService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const matchFormatGames: Ref<IMatchFormatGame[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveMatchFormatGames = async () => {
      isFetching.value = true;
      try {
        const res = await matchFormatGameService().retrieve();
        matchFormatGames.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveMatchFormatGames();
    };

    onMounted(async () => {
      await retrieveMatchFormatGames();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IMatchFormatGame) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeMatchFormatGame = async () => {
      try {
        await matchFormatGameService().delete(removeId.value);
        const message = t$('tossApp.matchFormatGame.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveMatchFormatGames();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      matchFormatGames,
      handleSyncList,
      isFetching,
      retrieveMatchFormatGames,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeMatchFormatGame,
      t$,
    };
  },
});
