import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import CompetitionService from './competition.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import FileInfoService from '@/entities/file-info/file-info.service';
import { type IFileInfo } from '@/shared/model/file-info.model';
import RewardService from '@/entities/reward/reward.service';
import { type IReward } from '@/shared/model/reward.model';
import CountryService from '@/entities/country/country.service';
import { type ICountry } from '@/shared/model/country.model';
import OperatorService from '@/entities/operator/operator.service';
import { type IOperator } from '@/shared/model/operator.model';
import EntryFeeService from '@/entities/entry-fee/entry-fee.service';
import { type IEntryFee } from '@/shared/model/entry-fee.model';
import { type ICompetition, Competition } from '@/shared/model/competition.model';
import { CompetitionStatus } from '@/shared/model/enumerations/competition-status.model';
import { EntryApplyType } from '@/shared/model/enumerations/entry-apply-type.model';
import { EntryRatingType } from '@/shared/model/enumerations/entry-rating-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CompetitionUpdate',
  setup() {
    const competitionService = inject('competitionService', () => new CompetitionService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const competition: Ref<ICompetition> = ref(new Competition());

    const fileInfoService = inject('fileInfoService', () => new FileInfoService());

    const fileInfos: Ref<IFileInfo[]> = ref([]);

    const rewardService = inject('rewardService', () => new RewardService());

    const rewards: Ref<IReward[]> = ref([]);

    const countryService = inject('countryService', () => new CountryService());

    const countries: Ref<ICountry[]> = ref([]);

    const operatorService = inject('operatorService', () => new OperatorService());

    const operators: Ref<IOperator[]> = ref([]);

    const entryFeeService = inject('entryFeeService', () => new EntryFeeService());

    const entryFees: Ref<IEntryFee[]> = ref([]);
    const competitionStatusValues: Ref<string[]> = ref(Object.keys(CompetitionStatus));
    const entryApplyTypeValues: Ref<string[]> = ref(Object.keys(EntryApplyType));
    const entryRatingTypeValues: Ref<string[]> = ref(Object.keys(EntryRatingType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveCompetition = async competitionId => {
      try {
        const res = await competitionService().find(competitionId);
        res.startDateTime = new Date(res.startDateTime);
        res.endDateTime = new Date(res.endDateTime);
        res.entryStartDateTime = new Date(res.entryStartDateTime);
        res.entryEndDateTime = new Date(res.entryEndDateTime);
        competition.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.competitionId) {
      retrieveCompetition(route.params.competitionId);
    }

    const initRelationships = () => {
      fileInfoService()
        .retrieve()
        .then(res => {
          fileInfos.value = res.data;
        });
      rewardService()
        .retrieve()
        .then(res => {
          rewards.value = res.data;
        });
      countryService()
        .retrieve()
        .then(res => {
          countries.value = res.data;
        });
      operatorService()
        .retrieve()
        .then(res => {
          operators.value = res.data;
        });
      entryFeeService()
        .retrieve()
        .then(res => {
          entryFees.value = res.data;
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
      startDateTime: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      endDateTime: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      entryStartDateTime: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      entryEndDateTime: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      status: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      approval: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      entryApplyType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      entryRatingType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      competitionImage: {},
      reward: {},
      country: {},
      operator: {},
      entryFee: {},
    };
    const v$ = useVuelidate(validationRules, competition as any);
    v$.value.$validate();

    return {
      competitionService,
      alertService,
      competition,
      previousState,
      competitionStatusValues,
      entryApplyTypeValues,
      entryRatingTypeValues,
      isSaving,
      currentLanguage,
      fileInfos,
      rewards,
      countries,
      operators,
      entryFees,
      v$,
      ...useDateFormat({ entityRef: competition }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.competition.id) {
        this.competitionService()
          .update(this.competition)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.competition.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.competitionService()
          .create(this.competition)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.competition.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
