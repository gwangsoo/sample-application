import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import GameService from './game.service';
import { type IGame } from '@/shared/model/game.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Game',
  setup() {
    const { t: t$ } = useI18n();
    const gameService = inject('gameService', () => new GameService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const games: Ref<IGame[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveGames = async () => {
      isFetching.value = true;
      try {
        const res = await gameService().retrieve();
        games.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveGames();
    };

    onMounted(async () => {
      await retrieveGames();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IGame) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeGame = async () => {
      try {
        await gameService().delete(removeId.value);
        const message = t$('tossApp.game.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveGames();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      games,
      handleSyncList,
      isFetching,
      retrieveGames,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeGame,
      t$,
    };
  },
});
