import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import OperatorRoleService from './operator-role.service';
import { type IOperatorRole } from '@/shared/model/operator-role.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OperatorRole',
  setup() {
    const { t: t$ } = useI18n();
    const operatorRoleService = inject('operatorRoleService', () => new OperatorRoleService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const operatorRoles: Ref<IOperatorRole[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveOperatorRoles = async () => {
      isFetching.value = true;
      try {
        const res = await operatorRoleService().retrieve();
        operatorRoles.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveOperatorRoles();
    };

    onMounted(async () => {
      await retrieveOperatorRoles();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IOperatorRole) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeOperatorRole = async () => {
      try {
        await operatorRoleService().delete(removeId.value);
        const message = t$('tossApp.operatorRole.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveOperatorRoles();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      operatorRoles,
      handleSyncList,
      isFetching,
      retrieveOperatorRoles,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeOperatorRole,
      t$,
    };
  },
});
