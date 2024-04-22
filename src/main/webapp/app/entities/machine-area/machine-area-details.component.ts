import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import MachineAreaService from './machine-area.service';
import { type IMachineArea } from '@/shared/model/machine-area.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MachineAreaDetails',
  setup() {
    const machineAreaService = inject('machineAreaService', () => new MachineAreaService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const machineArea: Ref<IMachineArea> = ref({});

    const retrieveMachineArea = async machineAreaId => {
      try {
        const res = await machineAreaService().find(machineAreaId);
        machineArea.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.machineAreaId) {
      retrieveMachineArea(route.params.machineAreaId);
    }

    return {
      alertService,
      machineArea,

      previousState,
      t$: useI18n().t,
    };
  },
});
