import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import MatchScoreService from './match-score.service';
import { type IMatchScore } from '@/shared/model/match-score.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchScoreDetails',
  setup() {
    const matchScoreService = inject('matchScoreService', () => new MatchScoreService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const matchScore: Ref<IMatchScore> = ref({});

    const retrieveMatchScore = async matchScoreId => {
      try {
        const res = await matchScoreService().find(matchScoreId);
        matchScore.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.matchScoreId) {
      retrieveMatchScore(route.params.matchScoreId);
    }

    return {
      alertService,
      matchScore,

      previousState,
      t$: useI18n().t,
    };
  },
});
