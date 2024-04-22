import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import AffiliatedInfoService from './affiliated-info.service';
import { type IAffiliatedInfo } from '@/shared/model/affiliated-info.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AffiliatedInfoDetails',
  setup() {
    const affiliatedInfoService = inject('affiliatedInfoService', () => new AffiliatedInfoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const affiliatedInfo: Ref<IAffiliatedInfo> = ref({});

    const retrieveAffiliatedInfo = async affiliatedInfoId => {
      try {
        const res = await affiliatedInfoService().find(affiliatedInfoId);
        affiliatedInfo.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.affiliatedInfoId) {
      retrieveAffiliatedInfo(route.params.affiliatedInfoId);
    }

    return {
      alertService,
      affiliatedInfo,

      previousState,
      t$: useI18n().t,
    };
  },
});
