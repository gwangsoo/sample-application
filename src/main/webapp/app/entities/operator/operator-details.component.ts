import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import OperatorService from './operator.service';
import { type IOperator } from '@/shared/model/operator.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OperatorDetails',
  setup() {
    const operatorService = inject('operatorService', () => new OperatorService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const operator: Ref<IOperator> = ref({});

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

    return {
      alertService,
      operator,

      previousState,
      t$: useI18n().t,
    };
  },
});
