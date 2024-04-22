import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import MatchScoreService from './match-score.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import MatchService from '@/entities/match/match.service';
import { type IMatch } from '@/shared/model/match.model';
import { type IMatchScore, MatchScore } from '@/shared/model/match-score.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchScoreUpdate',
  setup() {
    const matchScoreService = inject('matchScoreService', () => new MatchScoreService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const matchScore: Ref<IMatchScore> = ref(new MatchScore());

    const matchService = inject('matchService', () => new MatchService());

    const matches: Ref<IMatch[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveMatchScore = async matchScoreId => {
      try {
        const res = await matchScoreService().find(matchScoreId);
        matchScore.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.matchScoreId) {
      retrieveMatchScore(route.params.matchScoreId);
    }

    const initRelationships = () => {
      matchService()
        .retrieve()
        .then(res => {
          matches.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      setNo: {},
      lgeNo: {},
      legGameName: {},
      homeLegScore: {},
      awayLegScore: {},
      match: {},
    };
    const v$ = useVuelidate(validationRules, matchScore as any);
    v$.value.$validate();

    return {
      matchScoreService,
      alertService,
      matchScore,
      previousState,
      isSaving,
      currentLanguage,
      matches,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.matchScore.id) {
        this.matchScoreService()
          .update(this.matchScore)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.matchScore.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.matchScoreService()
          .create(this.matchScore)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.matchScore.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
