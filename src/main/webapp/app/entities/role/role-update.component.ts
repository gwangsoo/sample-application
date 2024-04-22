import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import RoleService from './role.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import OperatorRoleService from '@/entities/operator-role/operator-role.service';
import { type IOperatorRole } from '@/shared/model/operator-role.model';
import { type IRole, Role } from '@/shared/model/role.model';
import { AuthScopeType } from '@/shared/model/enumerations/auth-scope-type.model';
import { AuthLevelType } from '@/shared/model/enumerations/auth-level-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RoleUpdate',
  setup() {
    const roleService = inject('roleService', () => new RoleService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const role: Ref<IRole> = ref(new Role());

    const operatorRoleService = inject('operatorRoleService', () => new OperatorRoleService());

    const operatorRoles: Ref<IOperatorRole[]> = ref([]);
    const authScopeTypeValues: Ref<string[]> = ref(Object.keys(AuthScopeType));
    const authLevelTypeValues: Ref<string[]> = ref(Object.keys(AuthLevelType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveRole = async roleId => {
      try {
        const res = await roleService().find(roleId);
        role.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.roleId) {
      retrieveRole(route.params.roleId);
    }

    const initRelationships = () => {
      operatorRoleService()
        .retrieve()
        .then(res => {
          operatorRoles.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 50 }).toString(), 50),
      },
      authScopeType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      authLevelType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      displayOrder: {
        required: validations.required(t$('entity.validation.required').toString()),
        integer: validations.integer(t$('entity.validation.number').toString()),
      },
      operatorRole: {},
    };
    const v$ = useVuelidate(validationRules, role as any);
    v$.value.$validate();

    return {
      roleService,
      alertService,
      role,
      previousState,
      authScopeTypeValues,
      authLevelTypeValues,
      isSaving,
      currentLanguage,
      operatorRoles,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.role.id) {
        this.roleService()
          .update(this.role)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.role.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.roleService()
          .create(this.role)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.role.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
