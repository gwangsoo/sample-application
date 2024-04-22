import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import MatchCallService from './match-call.service';
import { useDateFormat } from '@/shared/composables';
import { type IMatchCall } from '@/shared/model/match-call.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchCallDetails',
  setup() {
    const dateFormat = useDateFormat();
    const matchCallService = inject('matchCallService', () => new MatchCallService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const matchCall: Ref<IMatchCall> = ref({});

    const retrieveMatchCall = async matchCallId => {
      try {
        const res = await matchCallService().find(matchCallId);
        matchCall.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.matchCallId) {
      retrieveMatchCall(route.params.matchCallId);
    }

    return {
      ...dateFormat,
      alertService,
      matchCall,

      previousState,
      t$: useI18n().t,
    };
  },
});
