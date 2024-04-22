import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import MatchFormatSetService from './match-format-set.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import MatchFormatService from '@/entities/match-format/match-format.service';
import { type IMatchFormat } from '@/shared/model/match-format.model';
import { type IMatchFormatSet, MatchFormatSet } from '@/shared/model/match-format-set.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchFormatSetUpdate',
  setup() {
    const matchFormatSetService = inject('matchFormatSetService', () => new MatchFormatSetService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const matchFormatSet: Ref<IMatchFormatSet> = ref(new MatchFormatSet());

    const matchFormatService = inject('matchFormatService', () => new MatchFormatService());

    const matchFormats: Ref<IMatchFormat[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveMatchFormatSet = async matchFormatSetId => {
      try {
        const res = await matchFormatSetService().find(matchFormatSetId);
        matchFormatSet.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.matchFormatSetId) {
      retrieveMatchFormatSet(route.params.matchFormatSetId);
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
      point: {
        required: validations.required(t$('entity.validation.required').toString()),
        integer: validations.integer(t$('entity.validation.number').toString()),
        min: validations.minValue(t$('entity.validation.min', { min: 1 }).toString(), 1),
      },
      matchFormat: {},
    };
    const v$ = useVuelidate(validationRules, matchFormatSet as any);
    v$.value.$validate();

    return {
      matchFormatSetService,
      alertService,
      matchFormatSet,
      previousState,
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
      if (this.matchFormatSet.id) {
        this.matchFormatSetService()
          .update(this.matchFormatSet)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.matchFormatSet.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.matchFormatSetService()
          .create(this.matchFormatSet)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.matchFormatSet.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
