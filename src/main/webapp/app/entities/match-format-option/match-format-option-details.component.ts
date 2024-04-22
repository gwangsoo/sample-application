import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import MatchFormatOptionService from './match-format-option.service';
import { type IMatchFormatOption } from '@/shared/model/match-format-option.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchFormatOptionDetails',
  setup() {
    const matchFormatOptionService = inject('matchFormatOptionService', () => new MatchFormatOptionService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const matchFormatOption: Ref<IMatchFormatOption> = ref({});

    const retrieveMatchFormatOption = async matchFormatOptionId => {
      try {
        const res = await matchFormatOptionService().find(matchFormatOptionId);
        matchFormatOption.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.matchFormatOptionId) {
      retrieveMatchFormatOption(route.params.matchFormatOptionId);
    }

    return {
      alertService,
      matchFormatOption,

      previousState,
      t$: useI18n().t,
    };
  },
});
