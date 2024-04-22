import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import EntryService from './entry.service';
import { type IEntry } from '@/shared/model/entry.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EntryDetails',
  setup() {
    const entryService = inject('entryService', () => new EntryService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const entry: Ref<IEntry> = ref({});

    const retrieveEntry = async entryId => {
      try {
        const res = await entryService().find(entryId);
        entry.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.entryId) {
      retrieveEntry(route.params.entryId);
    }

    return {
      alertService,
      entry,

      previousState,
      t$: useI18n().t,
    };
  },
});
