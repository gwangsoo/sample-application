import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import MatchFormatSetService from './match-format-set.service';
import { type IMatchFormatSet } from '@/shared/model/match-format-set.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchFormatSetDetails',
  setup() {
    const matchFormatSetService = inject('matchFormatSetService', () => new MatchFormatSetService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const matchFormatSet: Ref<IMatchFormatSet> = ref({});

    const retrieveMatchFormatSet = async matchFormatSetId => {
      try {
        const res = await matchFormatSetService().find(matchFormatSetId);
        matchFormatSet.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.matchFormatSetId) {
      retrieveMatchFormatSet(route.params.matchFormatSetId);
    }

    return {
      alertService,
      matchFormatSet,

      previousState,
      t$: useI18n().t,
    };
  },
});
