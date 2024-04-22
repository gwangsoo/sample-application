import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import MatchService from './match.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import MatchTeamService from '@/entities/match-team/match-team.service';
import { type IMatchTeam } from '@/shared/model/match-team.model';
import MachineService from '@/entities/machine/machine.service';
import { type IMachine } from '@/shared/model/machine.model';
import DivisionService from '@/entities/division/division.service';
import { type IDivision } from '@/shared/model/division.model';
import { type IMatch, Match } from '@/shared/model/match.model';
import { MatchType } from '@/shared/model/enumerations/match-type.model';
import { MatchStatus } from '@/shared/model/enumerations/match-status.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchUpdate',
  setup() {
    const matchService = inject('matchService', () => new MatchService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const match: Ref<IMatch> = ref(new Match());

    const matchTeamService = inject('matchTeamService', () => new MatchTeamService());

    const matchTeams: Ref<IMatchTeam[]> = ref([]);

    const machineService = inject('machineService', () => new MachineService());

    const machines: Ref<IMachine[]> = ref([]);

    const divisionService = inject('divisionService', () => new DivisionService());

    const divisions: Ref<IDivision[]> = ref([]);
    const matchTypeValues: Ref<string[]> = ref(Object.keys(MatchType));
    const matchStatusValues: Ref<string[]> = ref(Object.keys(MatchStatus));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveMatch = async matchId => {
      try {
        const res = await matchService().find(matchId);
        match.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.matchId) {
      retrieveMatch(route.params.matchId);
    }

    const initRelationships = () => {
      matchTeamService()
        .retrieve()
        .then(res => {
          matchTeams.value = res.data;
        });
      machineService()
        .retrieve()
        .then(res => {
          machines.value = res.data;
        });
      divisionService()
        .retrieve()
        .then(res => {
          divisions.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      matchNo: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 9 }).toString(), 9),
      },
      matchType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      groupNo: {
        integer: validations.integer(t$('entity.validation.number').toString()),
        min: validations.minValue(t$('entity.validation.min', { min: 1 }).toString(), 1),
        max: validations.maxValue(t$('entity.validation.max', { max: 999 }).toString(), 999),
      },
      groupMatchSeq: {
        integer: validations.integer(t$('entity.validation.number').toString()),
        min: validations.minValue(t$('entity.validation.min', { min: 1 }).toString(), 1),
        max: validations.maxValue(t$('entity.validation.max', { max: 999 }).toString(), 999),
      },
      roundNum: {},
      matchStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      nextMatchNo: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 9 }).toString(), 9),
      },
      home: {},
      away: {},
      tmatch: {},
      division: {},
    };
    const v$ = useVuelidate(validationRules, match as any);
    v$.value.$validate();

    return {
      matchService,
      alertService,
      match,
      previousState,
      matchTypeValues,
      matchStatusValues,
      isSaving,
      currentLanguage,
      matchTeams,
      machines,
      divisions,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.match.id) {
        this.matchService()
          .update(this.match)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.match.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.matchService()
          .create(this.match)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.match.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
