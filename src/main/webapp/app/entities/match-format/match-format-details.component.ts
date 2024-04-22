import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import MatchFormatService from './match-format.service';
import { type IMatchFormat } from '@/shared/model/match-format.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchFormatDetails',
  setup() {
    const matchFormatService = inject('matchFormatService', () => new MatchFormatService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const matchFormat: Ref<IMatchFormat> = ref({});

    const retrieveMatchFormat = async matchFormatId => {
      try {
        const res = await matchFormatService().find(matchFormatId);
        matchFormat.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.matchFormatId) {
      retrieveMatchFormat(route.params.matchFormatId);
    }

    return {
      alertService,
      matchFormat,

      previousState,
      t$: useI18n().t,
    };
  },
});
