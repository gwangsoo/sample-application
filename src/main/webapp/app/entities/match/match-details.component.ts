import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import MatchService from './match.service';
import { type IMatch } from '@/shared/model/match.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchDetails',
  setup() {
    const matchService = inject('matchService', () => new MatchService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const match: Ref<IMatch> = ref({});

    const retrieveMatch = async matchId => {
      try {
        const res = await matchService().find(matchId);
        match.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.matchId) {
      retrieveMatch(route.params.matchId);
    }

    return {
      alertService,
      match,

      previousState,
      t$: useI18n().t,
    };
  },
});
