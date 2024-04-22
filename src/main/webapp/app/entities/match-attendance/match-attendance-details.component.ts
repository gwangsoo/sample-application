import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import MatchAttendanceService from './match-attendance.service';
import { useDateFormat } from '@/shared/composables';
import { type IMatchAttendance } from '@/shared/model/match-attendance.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchAttendanceDetails',
  setup() {
    const dateFormat = useDateFormat();
    const matchAttendanceService = inject('matchAttendanceService', () => new MatchAttendanceService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const matchAttendance: Ref<IMatchAttendance> = ref({});

    const retrieveMatchAttendance = async matchAttendanceId => {
      try {
        const res = await matchAttendanceService().find(matchAttendanceId);
        matchAttendance.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.matchAttendanceId) {
      retrieveMatchAttendance(route.params.matchAttendanceId);
    }

    return {
      ...dateFormat,
      alertService,
      matchAttendance,

      previousState,
      t$: useI18n().t,
    };
  },
});
