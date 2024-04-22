import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import MatchTeamService from './match-team.service';
import { type IMatchTeam } from '@/shared/model/match-team.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchTeamDetails',
  setup() {
    const matchTeamService = inject('matchTeamService', () => new MatchTeamService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const matchTeam: Ref<IMatchTeam> = ref({});

    const retrieveMatchTeam = async matchTeamId => {
      try {
        const res = await matchTeamService().find(matchTeamId);
        matchTeam.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.matchTeamId) {
      retrieveMatchTeam(route.params.matchTeamId);
    }

    return {
      alertService,
      matchTeam,

      previousState,
      t$: useI18n().t,
    };
  },
});
