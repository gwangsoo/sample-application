import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import MatchCallService from './match-call.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import MatchTeamService from '@/entities/match-team/match-team.service';
import { type IMatchTeam } from '@/shared/model/match-team.model';
import { type IMatchCall, MatchCall } from '@/shared/model/match-call.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchCallUpdate',
  setup() {
    const matchCallService = inject('matchCallService', () => new MatchCallService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const matchCall: Ref<IMatchCall> = ref(new MatchCall());

    const matchTeamService = inject('matchTeamService', () => new MatchTeamService());

    const matchTeams: Ref<IMatchTeam[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveMatchCall = async matchCallId => {
      try {
        const res = await matchCallService().find(matchCallId);
        res.callDateTime = new Date(res.callDateTime);
        matchCall.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.matchCallId) {
      retrieveMatchCall(route.params.matchCallId);
    }

    const initRelationships = () => {
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
      callDateTime: {},
      callMessage: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 2048 }).toString(), 2048),
      },
      matchTeam: {},
    };
    const v$ = useVuelidate(validationRules, matchCall as any);
    v$.value.$validate();

    return {
      matchCallService,
      alertService,
      matchCall,
      previousState,
      isSaving,
      currentLanguage,
      matchTeams,
      v$,
      ...useDateFormat({ entityRef: matchCall }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.matchCall.id) {
        this.matchCallService()
          .update(this.matchCall)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.matchCall.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.matchCallService()
          .create(this.matchCall)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.matchCall.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
