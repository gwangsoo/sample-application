import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import TournamentService from './tournament.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import EntryFeeService from '@/entities/entry-fee/entry-fee.service';
import { type IEntryFee } from '@/shared/model/entry-fee.model';
import CompetitionService from '@/entities/competition/competition.service';
import { type ICompetition } from '@/shared/model/competition.model';
import { type ITournament, Tournament } from '@/shared/model/tournament.model';
import { EntryApprovalType } from '@/shared/model/enumerations/entry-approval-type.model';
import { TournamentType } from '@/shared/model/enumerations/tournament-type.model';
import { SeedingRuleType } from '@/shared/model/enumerations/seeding-rule-type.model';
import { TournamentPlayMode } from '@/shared/model/enumerations/tournament-play-mode.model';
import { DivisionRuleType } from '@/shared/model/enumerations/division-rule-type.model';
import { DivisionAssignMethod } from '@/shared/model/enumerations/division-assign-method.model';
import { EntryGenderType } from '@/shared/model/enumerations/entry-gender-type.model';
import { HandicapType } from '@/shared/model/enumerations/handicap-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TournamentUpdate',
  setup() {
    const tournamentService = inject('tournamentService', () => new TournamentService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const tournament: Ref<ITournament> = ref(new Tournament());

    const entryFeeService = inject('entryFeeService', () => new EntryFeeService());

    const entryFees: Ref<IEntryFee[]> = ref([]);

    const competitionService = inject('competitionService', () => new CompetitionService());

    const competitions: Ref<ICompetition[]> = ref([]);
    const entryApprovalTypeValues: Ref<string[]> = ref(Object.keys(EntryApprovalType));
    const tournamentTypeValues: Ref<string[]> = ref(Object.keys(TournamentType));
    const seedingRuleTypeValues: Ref<string[]> = ref(Object.keys(SeedingRuleType));
    const tournamentPlayModeValues: Ref<string[]> = ref(Object.keys(TournamentPlayMode));
    const divisionRuleTypeValues: Ref<string[]> = ref(Object.keys(DivisionRuleType));
    const divisionAssignMethodValues: Ref<string[]> = ref(Object.keys(DivisionAssignMethod));
    const entryGenderTypeValues: Ref<string[]> = ref(Object.keys(EntryGenderType));
    const handicapTypeValues: Ref<string[]> = ref(Object.keys(HandicapType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveTournament = async tournamentId => {
      try {
        const res = await tournamentService().find(tournamentId);
        tournament.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.tournamentId) {
      retrieveTournament(route.params.tournamentId);
    }

    const initRelationships = () => {
      entryFeeService()
        .retrieve()
        .then(res => {
          entryFees.value = res.data;
        });
      competitionService()
        .retrieve()
        .then(res => {
          competitions.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      seq: {
        required: validations.required(t$('entity.validation.required').toString()),
        integer: validations.integer(t$('entity.validation.number').toString()),
        min: validations.minValue(t$('entity.validation.min', { min: 1 }).toString(), 1),
        max: validations.maxValue(t$('entity.validation.max', { max: 99 }).toString(), 99),
      },
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 256 }).toString(), 256),
      },
      day: {
        required: validations.required(t$('entity.validation.required').toString()),
        integer: validations.integer(t$('entity.validation.number').toString()),
        min: validations.minValue(t$('entity.validation.min', { min: 1 }).toString(), 1),
      },
      eventTournament: {},
      entryClose: {},
      entryApprovalType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      tournamentType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      seedingRule: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      tournamentPlayMode: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      thirdPlaceDecision: {},
      divisionRule: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      entryLimit: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      numOfEntry: {
        required: validations.required(t$('entity.validation.required').toString()),
        integer: validations.integer(t$('entity.validation.number').toString()),
        min: validations.minValue(t$('entity.validation.min', { min: 0 }).toString(), 0),
      },
      divisionAssignMethod: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      entryGenderType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      handicap: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      entryFee: {},
      competition: {},
    };
    const v$ = useVuelidate(validationRules, tournament as any);
    v$.value.$validate();

    return {
      tournamentService,
      alertService,
      tournament,
      previousState,
      entryApprovalTypeValues,
      tournamentTypeValues,
      seedingRuleTypeValues,
      tournamentPlayModeValues,
      divisionRuleTypeValues,
      divisionAssignMethodValues,
      entryGenderTypeValues,
      handicapTypeValues,
      isSaving,
      currentLanguage,
      entryFees,
      competitions,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.tournament.id) {
        this.tournamentService()
          .update(this.tournament)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.tournament.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.tournamentService()
          .create(this.tournament)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.tournament.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
