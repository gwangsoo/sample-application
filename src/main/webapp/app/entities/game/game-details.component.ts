import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import GameService from './game.service';
import { type IGame } from '@/shared/model/game.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'GameDetails',
  setup() {
    const gameService = inject('gameService', () => new GameService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const game: Ref<IGame> = ref({});

    const retrieveGame = async gameId => {
      try {
        const res = await gameService().find(gameId);
        game.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.gameId) {
      retrieveGame(route.params.gameId);
    }

    return {
      alertService,
      game,

      previousState,
      t$: useI18n().t,
    };
  },
});
