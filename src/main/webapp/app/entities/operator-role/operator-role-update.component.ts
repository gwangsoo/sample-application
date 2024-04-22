import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import OperatorRoleService from './operator-role.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IOperatorRole, OperatorRole } from '@/shared/model/operator-role.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OperatorRoleUpdate',
  setup() {
    const operatorRoleService = inject('operatorRoleService', () => new OperatorRoleService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const operatorRole: Ref<IOperatorRole> = ref(new OperatorRole());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveOperatorRole = async operatorRoleId => {
      try {
        const res = await operatorRoleService().find(operatorRoleId);
        operatorRole.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.operatorRoleId) {
      retrieveOperatorRole(route.params.operatorRoleId);
    }

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 50 }).toString(), 50),
      },
      displayOrder: {
        required: validations.required(t$('entity.validation.required').toString()),
        integer: validations.integer(t$('entity.validation.number').toString()),
      },
    };
    const v$ = useVuelidate(validationRules, operatorRole as any);
    v$.value.$validate();

    return {
      operatorRoleService,
      alertService,
      operatorRole,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.operatorRole.id) {
        this.operatorRoleService()
          .update(this.operatorRole)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.operatorRole.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.operatorRoleService()
          .create(this.operatorRole)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.operatorRole.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
