import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import MachineService from './machine.service';
import { type IMachine } from '@/shared/model/machine.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Machine',
  setup() {
    const { t: t$ } = useI18n();
    const machineService = inject('machineService', () => new MachineService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const machines: Ref<IMachine[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveMachines = async () => {
      isFetching.value = true;
      try {
        const res = await machineService().retrieve();
        machines.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveMachines();
    };

    onMounted(async () => {
      await retrieveMachines();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IMachine) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeMachine = async () => {
      try {
        await machineService().delete(removeId.value);
        const message = t$('tossApp.machine.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveMachines();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      machines,
      handleSyncList,
      isFetching,
      retrieveMachines,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeMachine,
      t$,
    };
  },
});
