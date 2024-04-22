import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import TeamService from './team.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import EntryService from '@/entities/entry/entry.service';
import { type IEntry } from '@/shared/model/entry.model';
import DivisionService from '@/entities/division/division.service';
import { type IDivision } from '@/shared/model/division.model';
import AffiliatedInfoService from '@/entities/affiliated-info/affiliated-info.service';
import { type IAffiliatedInfo } from '@/shared/model/affiliated-info.model';
import PaymentInfoService from '@/entities/payment-info/payment-info.service';
import { type IPaymentInfo } from '@/shared/model/payment-info.model';
import { type ITeam, Team } from '@/shared/model/team.model';
import { EntryApprovalStatusType } from '@/shared/model/enumerations/entry-approval-status-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TeamUpdate',
  setup() {
    const teamService = inject('teamService', () => new TeamService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const team: Ref<ITeam> = ref(new Team());

    const entryService = inject('entryService', () => new EntryService());

    const entries: Ref<IEntry[]> = ref([]);

    const divisionService = inject('divisionService', () => new DivisionService());

    const divisions: Ref<IDivision[]> = ref([]);

    const affiliatedInfoService = inject('affiliatedInfoService', () => new AffiliatedInfoService());

    const affiliatedInfos: Ref<IAffiliatedInfo[]> = ref([]);

    const paymentInfoService = inject('paymentInfoService', () => new PaymentInfoService());

    const paymentInfos: Ref<IPaymentInfo[]> = ref([]);
    const entryApprovalStatusTypeValues: Ref<string[]> = ref(Object.keys(EntryApprovalStatusType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveTeam = async teamId => {
      try {
        const res = await teamService().find(teamId);
        res.entryDate = new Date(res.entryDate);
        team.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.teamId) {
      retrieveTeam(route.params.teamId);
    }

    const initRelationships = () => {
      entryService()
        .retrieve()
        .then(res => {
          entries.value = res.data;
        });
      divisionService()
        .retrieve()
        .then(res => {
          divisions.value = res.data;
        });
      affiliatedInfoService()
        .retrieve()
        .then(res => {
          affiliatedInfos.value = res.data;
        });
      paymentInfoService()
        .retrieve()
        .then(res => {
          paymentInfos.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      teamNo: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 8 }).toString(), 8),
      },
      approvalStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      entryDate: {},
      memo: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 256 }).toString(), 256),
      },
      groupNo: {
        integer: validations.integer(t$('entity.validation.number').toString()),
        min: validations.minValue(t$('entity.validation.min', { min: 1 }).toString(), 1),
        max: validations.maxValue(t$('entity.validation.max', { max: 999 }).toString(), 999),
      },
      groupSeq: {
        integer: validations.integer(t$('entity.validation.number').toString()),
        min: validations.minValue(t$('entity.validation.min', { min: 1 }).toString(), 1),
        max: validations.maxValue(t$('entity.validation.max', { max: 9 }).toString(), 9),
      },
      seedNo: {
        integer: validations.integer(t$('entity.validation.number').toString()),
        min: validations.minValue(t$('entity.validation.min', { min: 1 }).toString(), 1),
        max: validations.maxValue(t$('entity.validation.max', { max: 999 }).toString(), 999),
      },
      captain: {},
      tteam: {},
      affiliatedInfo: {},
      paymentInfo: {},
      division: {},
    };
    const v$ = useVuelidate(validationRules, team as any);
    v$.value.$validate();

    return {
      teamService,
      alertService,
      team,
      previousState,
      entryApprovalStatusTypeValues,
      isSaving,
      currentLanguage,
      entries,
      divisions,
      affiliatedInfos,
      paymentInfos,
      v$,
      ...useDateFormat({ entityRef: team }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.team.id) {
        this.teamService()
          .update(this.team)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.team.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.teamService()
          .create(this.team)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.team.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
