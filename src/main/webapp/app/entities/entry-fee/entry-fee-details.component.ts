import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import EntryFeeService from './entry-fee.service';
import { type IEntryFee } from '@/shared/model/entry-fee.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EntryFeeDetails',
  setup() {
    const entryFeeService = inject('entryFeeService', () => new EntryFeeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const entryFee: Ref<IEntryFee> = ref({});

    const retrieveEntryFee = async entryFeeId => {
      try {
        const res = await entryFeeService().find(entryFeeId);
        entryFee.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.entryFeeId) {
      retrieveEntryFee(route.params.entryFeeId);
    }

    return {
      alertService,
      entryFee,

      previousState,
      t$: useI18n().t,
    };
  },
});
