import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import MatchFormatLegService from './match-format-leg.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import MatchFormatOptionService from '@/entities/match-format-option/match-format-option.service';
import { type IMatchFormatOption } from '@/shared/model/match-format-option.model';
import MatchFormatSetService from '@/entities/match-format-set/match-format-set.service';
import { type IMatchFormatSet } from '@/shared/model/match-format-set.model';
import { type IMatchFormatLeg, MatchFormatLeg } from '@/shared/model/match-format-leg.model';
import { FirstThrowType } from '@/shared/model/enumerations/first-throw-type.model';
import { LegPlayMode } from '@/shared/model/enumerations/leg-play-mode.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchFormatLegUpdate',
  setup() {
    const matchFormatLegService = inject('matchFormatLegService', () => new MatchFormatLegService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const matchFormatLeg: Ref<IMatchFormatLeg> = ref(new MatchFormatLeg());

    const matchFormatOptionService = inject('matchFormatOptionService', () => new MatchFormatOptionService());

    const matchFormatOptions: Ref<IMatchFormatOption[]> = ref([]);

    const matchFormatSetService = inject('matchFormatSetService', () => new MatchFormatSetService());

    const matchFormatSets: Ref<IMatchFormatSet[]> = ref([]);
    const firstThrowTypeValues: Ref<string[]> = ref(Object.keys(FirstThrowType));
    const legPlayModeValues: Ref<string[]> = ref(Object.keys(LegPlayMode));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveMatchFormatLeg = async matchFormatLegId => {
      try {
        const res = await matchFormatLegService().find(matchFormatLegId);
        matchFormatLeg.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.matchFormatLegId) {
      retrieveMatchFormatLeg(route.params.matchFormatLegId);
    }

    const initRelationships = () => {
      matchFormatOptionService()
        .retrieve()
        .then(res => {
          matchFormatOptions.value = res.data;
        });
      matchFormatSetService()
        .retrieve()
        .then(res => {
          matchFormatSets.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      seq: {},
      firstThrowType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      playMode: {},
      option: {},
      matchFormatSet: {},
    };
    const v$ = useVuelidate(validationRules, matchFormatLeg as any);
    v$.value.$validate();

    return {
      matchFormatLegService,
      alertService,
      matchFormatLeg,
      previousState,
      firstThrowTypeValues,
      legPlayModeValues,
      isSaving,
      currentLanguage,
      matchFormatOptions,
      matchFormatSets,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.matchFormatLeg.id) {
        this.matchFormatLegService()
          .update(this.matchFormatLeg)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.matchFormatLeg.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.matchFormatLegService()
          .create(this.matchFormatLeg)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.matchFormatLeg.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
