import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import MachineService from './machine.service';
import { type IMachine } from '@/shared/model/machine.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MachineDetails',
  setup() {
    const machineService = inject('machineService', () => new MachineService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const machine: Ref<IMachine> = ref({});

    const retrieveMachine = async machineId => {
      try {
        const res = await machineService().find(machineId);
        machine.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.machineId) {
      retrieveMachine(route.params.machineId);
    }

    return {
      alertService,
      machine,

      previousState,
      t$: useI18n().t,
    };
  },
});
