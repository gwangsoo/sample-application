import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import MatchFormatOptionService from './match-format-option.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import MatchFormatService from '@/entities/match-format/match-format.service';
import { type IMatchFormat } from '@/shared/model/match-format.model';
import { type IMatchFormatOption, MatchFormatOption } from '@/shared/model/match-format-option.model';
import { MatchFormatInOptionType } from '@/shared/model/enumerations/match-format-in-option-type.model';
import { MatchFormatOutOptionType } from '@/shared/model/enumerations/match-format-out-option-type.model';
import { MatchFormatBullOptionType } from '@/shared/model/enumerations/match-format-bull-option-type.model';
import { MatchFormatFreezeOptionType } from '@/shared/model/enumerations/match-format-freeze-option-type.model';
import { MatchFormatTeamFinishOptionType } from '@/shared/model/enumerations/match-format-team-finish-option-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchFormatOptionUpdate',
  setup() {
    const matchFormatOptionService = inject('matchFormatOptionService', () => new MatchFormatOptionService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const matchFormatOption: Ref<IMatchFormatOption> = ref(new MatchFormatOption());

    const matchFormatService = inject('matchFormatService', () => new MatchFormatService());

    const matchFormats: Ref<IMatchFormat[]> = ref([]);
    const matchFormatInOptionTypeValues: Ref<string[]> = ref(Object.keys(MatchFormatInOptionType));
    const matchFormatOutOptionTypeValues: Ref<string[]> = ref(Object.keys(MatchFormatOutOptionType));
    const matchFormatBullOptionTypeValues: Ref<string[]> = ref(Object.keys(MatchFormatBullOptionType));
    const matchFormatFreezeOptionTypeValues: Ref<string[]> = ref(Object.keys(MatchFormatFreezeOptionType));
    const matchFormatTeamFinishOptionTypeValues: Ref<string[]> = ref(Object.keys(MatchFormatTeamFinishOptionType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      matchFormatService()
        .retrieve()
        .then(res => {
          matchFormats.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      game01InOptionType: {},
      game01OutOptionType: {},
      game01BullOptionType: {},
      game01Arrange: {},
      cricketOverKill: {},
      cricketScore: {},
      teamGame01FreezeView: {},
      teamGame01Point: {},
      teamCricketMark: {},
      teamCricketFinish: {},
      teamCricketPoint: {},
      matchFormat: {},
    };
    const v$ = useVuelidate(validationRules, matchFormatOption as any);
    v$.value.$validate();

    return {
      matchFormatOptionService,
      alertService,
      matchFormatOption,
      previousState,
      matchFormatInOptionTypeValues,
      matchFormatOutOptionTypeValues,
      matchFormatBullOptionTypeValues,
      matchFormatFreezeOptionTypeValues,
      matchFormatTeamFinishOptionTypeValues,
      isSaving,
      currentLanguage,
      matchFormats,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.matchFormatOption.id) {
        this.matchFormatOptionService()
          .update(this.matchFormatOption)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.matchFormatOption.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.matchFormatOptionService()
          .create(this.matchFormatOption)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.matchFormatOption.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
