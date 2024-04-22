import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import DivisionService from './division.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import TournamentService from '@/entities/tournament/tournament.service';
import { type ITournament } from '@/shared/model/tournament.model';
import { type IDivision, Division } from '@/shared/model/division.model';
import { RoundRobinRankingDecisionType } from '@/shared/model/enumerations/round-robin-ranking-decision-type.model';
import { RoundRobinGroupType } from '@/shared/model/enumerations/round-robin-group-type.model';
import { NextRoundDecisionType } from '@/shared/model/enumerations/next-round-decision-type.model';
import { ThirdDecisionRankingRule } from '@/shared/model/enumerations/third-decision-ranking-rule.model';
import { EventRangeType } from '@/shared/model/enumerations/event-range-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'DivisionUpdate',
  setup() {
    const divisionService = inject('divisionService', () => new DivisionService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const division: Ref<IDivision> = ref(new Division());

    const tournamentService = inject('tournamentService', () => new TournamentService());

    const tournaments: Ref<ITournament[]> = ref([]);
    const roundRobinRankingDecisionTypeValues: Ref<string[]> = ref(Object.keys(RoundRobinRankingDecisionType));
    const roundRobinGroupTypeValues: Ref<string[]> = ref(Object.keys(RoundRobinGroupType));
    const nextRoundDecisionTypeValues: Ref<string[]> = ref(Object.keys(NextRoundDecisionType));
    const thirdDecisionRankingRuleValues: Ref<string[]> = ref(Object.keys(ThirdDecisionRankingRule));
    const eventRangeTypeValues: Ref<string[]> = ref(Object.keys(EventRangeType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveDivision = async divisionId => {
      try {
        const res = await divisionService().find(divisionId);
        division.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.divisionId) {
      retrieveDivision(route.params.divisionId);
    }

    const initRelationships = () => {
      tournamentService()
        .retrieve()
        .then(res => {
          tournaments.value = res.data;
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
      ratingRuleTeamMin: {
        min: validations.minValue(t$('entity.validation.min', { min: 0 }).toString(), 0),
      },
      ratingRuleTeamMax: {
        min: validations.minValue(t$('entity.validation.min', { min: 0 }).toString(), 0),
      },
      ratingRuleIndividualLimit: {},
      ratingRuleIndividualMin: {
        min: validations.minValue(t$('entity.validation.min', { min: 0 }).toString(), 0),
      },
      ratingRuleIndividualMax: {
        min: validations.minValue(t$('entity.validation.min', { min: 0 }).toString(), 0),
      },
      entryLimit: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      roundRobinRankingDecisionType: {},
      roundRobinGroupType: {},
      nextRoundDecisionType: {},
      roundRoginThirdDecision: {},
      thirdDecisionRankingRule: {},
      useAllRoundSameFormat: {},
      eventRangeType: {},
      eliminationTeamCount: {},
      tournament: {},
    };
    const v$ = useVuelidate(validationRules, division as any);
    v$.value.$validate();

    return {
      divisionService,
      alertService,
      division,
      previousState,
      roundRobinRankingDecisionTypeValues,
      roundRobinGroupTypeValues,
      nextRoundDecisionTypeValues,
      thirdDecisionRankingRuleValues,
      eventRangeTypeValues,
      isSaving,
      currentLanguage,
      tournaments,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.division.id) {
        this.divisionService()
          .update(this.division)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.division.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.divisionService()
          .create(this.division)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.division.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
