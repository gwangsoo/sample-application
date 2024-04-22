import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import MatchFormatService from './match-format.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import DivisionService from '@/entities/division/division.service';
import { type IDivision } from '@/shared/model/division.model';
import { type IMatchFormat, MatchFormat } from '@/shared/model/match-format.model';
import { MatchFormatType } from '@/shared/model/enumerations/match-format-type.model';
import { FirstThrowType } from '@/shared/model/enumerations/first-throw-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchFormatUpdate',
  setup() {
    const matchFormatService = inject('matchFormatService', () => new MatchFormatService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const matchFormat: Ref<IMatchFormat> = ref(new MatchFormat());

    const divisionService = inject('divisionService', () => new DivisionService());

    const divisions: Ref<IDivision[]> = ref([]);
    const matchFormatTypeValues: Ref<string[]> = ref(Object.keys(MatchFormatType));
    const firstThrowTypeValues: Ref<string[]> = ref(Object.keys(FirstThrowType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveMatchFormat = async matchFormatId => {
      try {
        const res = await matchFormatService().find(matchFormatId);
        matchFormat.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.matchFormatId) {
      retrieveMatchFormat(route.params.matchFormatId);
    }

    const initRelationships = () => {
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
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 256 }).toString(), 256),
      },
      description: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 256 }).toString(), 256),
      },
      matchFormatType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      firstSet: {},
      middleSet: {},
      lastSet: {},
      division: {},
    };
    const v$ = useVuelidate(validationRules, matchFormat as any);
    v$.value.$validate();

    return {
      matchFormatService,
      alertService,
      matchFormat,
      previousState,
      matchFormatTypeValues,
      firstThrowTypeValues,
      isSaving,
      currentLanguage,
      divisions,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.matchFormat.id) {
        this.matchFormatService()
          .update(this.matchFormat)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.matchFormat.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.matchFormatService()
          .create(this.matchFormat)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.matchFormat.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
