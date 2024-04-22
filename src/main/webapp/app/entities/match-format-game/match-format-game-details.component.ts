import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import MatchFormatGameService from './match-format-game.service';
import { type IMatchFormatGame } from '@/shared/model/match-format-game.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchFormatGameDetails',
  setup() {
    const matchFormatGameService = inject('matchFormatGameService', () => new MatchFormatGameService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const matchFormatGame: Ref<IMatchFormatGame> = ref({});

    const retrieveMatchFormatGame = async matchFormatGameId => {
      try {
        const res = await matchFormatGameService().find(matchFormatGameId);
        matchFormatGame.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.matchFormatGameId) {
      retrieveMatchFormatGame(route.params.matchFormatGameId);
    }

    return {
      alertService,
      matchFormatGame,

      previousState,
      t$: useI18n().t,
    };
  },
});
