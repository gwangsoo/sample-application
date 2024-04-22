import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import DivisionService from './division.service';
import { type IDivision } from '@/shared/model/division.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'DivisionDetails',
  setup() {
    const divisionService = inject('divisionService', () => new DivisionService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const division: Ref<IDivision> = ref({});

    const retrieveDivision = async divisionId => {
      try {
        const res = await divisionService().find(divisionId);
        division.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.divisionId) {
      retrieveDivision(route.params.divisionId);
    }

    return {
      alertService,
      division,

      previousState,
      t$: useI18n().t,
    };
  },
});
