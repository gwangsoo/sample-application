import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import OperatorService from './operator.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import OperatorRoleService from '@/entities/operator-role/operator-role.service';
import { type IOperatorRole } from '@/shared/model/operator-role.model';
import RegionService from '@/entities/region/region.service';
import { type IRegion } from '@/shared/model/region.model';
import { type IOperator, Operator } from '@/shared/model/operator.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OperatorUpdate',
  setup() {
    const operatorService = inject('operatorService', () => new OperatorService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const operator: Ref<IOperator> = ref(new Operator());

    const operatorRoleService = inject('operatorRoleService', () => new OperatorRoleService());

    const operatorRoles: Ref<IOperatorRole[]> = ref([]);

    const regionService = inject('regionService', () => new RegionService());

    const regions: Ref<IRegion[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveOperator = async operatorId => {
      try {
        const res = await operatorService().find(operatorId);
        operator.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.operatorId) {
      retrieveOperator(route.params.operatorId);
    }

    const initRelationships = () => {
      operatorRoleService()
        .retrieve()
        .then(res => {
          operatorRoles.value = res.data;
        });
      regionService()
        .retrieve()
        .then(res => {
          regions.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      userId: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 50 }).toString(), 50),
      },
      userName: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 50 }).toString(), 50),
      },
      phone: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 20 }).toString(), 20),
      },
      email: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 128 }).toString(), 128),
      },
      address: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 256 }).toString(), 256),
      },
      approvalStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      operatorRole: {},
      region: {},
    };
    const v$ = useVuelidate(validationRules, operator as any);
    v$.value.$validate();

    return {
      operatorService,
      alertService,
      operator,
      previousState,
      isSaving,
      currentLanguage,
      operatorRoles,
      regions,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.operator.id) {
        this.operatorService()
          .update(this.operator)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.operator.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.operatorService()
          .create(this.operator)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.operator.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
