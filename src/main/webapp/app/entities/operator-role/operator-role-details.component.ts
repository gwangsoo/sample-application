import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import OperatorRoleService from './operator-role.service';
import { type IOperatorRole } from '@/shared/model/operator-role.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OperatorRoleDetails',
  setup() {
    const operatorRoleService = inject('operatorRoleService', () => new OperatorRoleService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const operatorRole: Ref<IOperatorRole> = ref({});

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

    return {
      alertService,
      operatorRole,

      previousState,
      t$: useI18n().t,
    };
  },
});
