import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import OperatorService from './operator.service';
import { type IOperator } from '@/shared/model/operator.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Operator',
  setup() {
    const { t: t$ } = useI18n();
    const operatorService = inject('operatorService', () => new OperatorService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const operators: Ref<IOperator[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveOperators = async () => {
      isFetching.value = true;
      try {
        const res = await operatorService().retrieve();
        operators.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveOperators();
    };

    onMounted(async () => {
      await retrieveOperators();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IOperator) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeOperator = async () => {
      try {
        await operatorService().delete(removeId.value);
        const message = t$('tossApp.operator.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveOperators();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      operators,
      handleSyncList,
      isFetching,
      retrieveOperators,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeOperator,
      t$,
    };
  },
});
