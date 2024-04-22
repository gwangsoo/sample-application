import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import MachineAreaService from './machine-area.service';
import { type IMachineArea } from '@/shared/model/machine-area.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MachineArea',
  setup() {
    const { t: t$ } = useI18n();
    const machineAreaService = inject('machineAreaService', () => new MachineAreaService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const machineAreas: Ref<IMachineArea[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveMachineAreas = async () => {
      isFetching.value = true;
      try {
        const res = await machineAreaService().retrieve();
        machineAreas.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveMachineAreas();
    };

    onMounted(async () => {
      await retrieveMachineAreas();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IMachineArea) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeMachineArea = async () => {
      try {
        await machineAreaService().delete(removeId.value);
        const message = t$('tossApp.machineArea.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveMachineAreas();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      machineAreas,
      handleSyncList,
      isFetching,
      retrieveMachineAreas,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeMachineArea,
      t$,
    };
  },
});
