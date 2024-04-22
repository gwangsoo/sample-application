import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import MatchFormatLegService from './match-format-leg.service';
import { type IMatchFormatLeg } from '@/shared/model/match-format-leg.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchFormatLegDetails',
  setup() {
    const matchFormatLegService = inject('matchFormatLegService', () => new MatchFormatLegService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const matchFormatLeg: Ref<IMatchFormatLeg> = ref({});

    const retrieveMatchFormatLeg = async matchFormatLegId => {
      try {
        const res = await matchFormatLegService().find(matchFormatLegId);
        matchFormatLeg.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.matchFormatLegId) {
      retrieveMatchFormatLeg(route.params.matchFormatLegId);
    }

    return {
      alertService,
      matchFormatLeg,

      previousState,
      t$: useI18n().t,
    };
  },
});
