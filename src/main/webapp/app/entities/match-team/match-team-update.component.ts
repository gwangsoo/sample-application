import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import MatchTeamService from './match-team.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import TeamService from '@/entities/team/team.service';
import { type ITeam } from '@/shared/model/team.model';
import { type IMatchTeam, MatchTeam } from '@/shared/model/match-team.model';
import { PlayerCallModeType } from '@/shared/model/enumerations/player-call-mode-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchTeamUpdate',
  setup() {
    const matchTeamService = inject('matchTeamService', () => new MatchTeamService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const matchTeam: Ref<IMatchTeam> = ref(new MatchTeam());

    const teamService = inject('teamService', () => new TeamService());

    const teams: Ref<ITeam[]> = ref([]);
    const playerCallModeTypeValues: Ref<string[]> = ref(Object.keys(PlayerCallModeType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      teamService()
        .retrieve()
        .then(res => {
          teams.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      isWinner: {},
      avgPpd: {},
      avgMpr: {},
      winSet: {},
      winLeg: {},
      playerCallModeType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      team: {},
    };
    const v$ = useVuelidate(validationRules, matchTeam as any);
    v$.value.$validate();

    return {
      matchTeamService,
      alertService,
      matchTeam,
      previousState,
      playerCallModeTypeValues,
      isSaving,
      currentLanguage,
      teams,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.matchTeam.id) {
        this.matchTeamService()
          .update(this.matchTeam)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.matchTeam.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.matchTeamService()
          .create(this.matchTeam)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.matchTeam.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
