import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import CompetitionService from './competition.service';
import { useDateFormat } from '@/shared/composables';
import { type ICompetition } from '@/shared/model/competition.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CompetitionDetails',
  setup() {
    const dateFormat = useDateFormat();
    const competitionService = inject('competitionService', () => new CompetitionService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const competition: Ref<ICompetition> = ref({});

    const retrieveCompetition = async competitionId => {
      try {
        const res = await competitionService().find(competitionId);
        competition.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.competitionId) {
      retrieveCompetition(route.params.competitionId);
    }

    return {
      ...dateFormat,
      alertService,
      competition,

      previousState,
      t$: useI18n().t,
    };
  },
});
