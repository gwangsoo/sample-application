import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import MatchAttendanceService from './match-attendance.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import EntryService from '@/entities/entry/entry.service';
import { type IEntry } from '@/shared/model/entry.model';
import MatchTeamService from '@/entities/match-team/match-team.service';
import { type IMatchTeam } from '@/shared/model/match-team.model';
import { type IMatchAttendance, MatchAttendance } from '@/shared/model/match-attendance.model';
import { AttendanceStatusType } from '@/shared/model/enumerations/attendance-status-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchAttendanceUpdate',
  setup() {
    const matchAttendanceService = inject('matchAttendanceService', () => new MatchAttendanceService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const matchAttendance: Ref<IMatchAttendance> = ref(new MatchAttendance());

    const entryService = inject('entryService', () => new EntryService());

    const entries: Ref<IEntry[]> = ref([]);

    const matchTeamService = inject('matchTeamService', () => new MatchTeamService());

    const matchTeams: Ref<IMatchTeam[]> = ref([]);
    const attendanceStatusTypeValues: Ref<string[]> = ref(Object.keys(AttendanceStatusType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveMatchAttendance = async matchAttendanceId => {
      try {
        const res = await matchAttendanceService().find(matchAttendanceId);
        res.attendanceDateTime = new Date(res.attendanceDateTime);
        matchAttendance.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.matchAttendanceId) {
      retrieveMatchAttendance(route.params.matchAttendanceId);
    }

    const initRelationships = () => {
      entryService()
        .retrieve()
        .then(res => {
          entries.value = res.data;
        });
      matchTeamService()
        .retrieve()
        .then(res => {
          matchTeams.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      attendanceStatusType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      attendanceDateTime: {},
      entry: {},
      matchTeam: {},
    };
    const v$ = useVuelidate(validationRules, matchAttendance as any);
    v$.value.$validate();

    return {
      matchAttendanceService,
      alertService,
      matchAttendance,
      previousState,
      attendanceStatusTypeValues,
      isSaving,
      currentLanguage,
      entries,
      matchTeams,
      v$,
      ...useDateFormat({ entityRef: matchAttendance }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.matchAttendance.id) {
        this.matchAttendanceService()
          .update(this.matchAttendance)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.matchAttendance.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.matchAttendanceService()
          .create(this.matchAttendance)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.matchAttendance.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
