import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import RewardDetailService from './reward-detail.service';
import { type IRewardDetail } from '@/shared/model/reward-detail.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RewardDetailDetails',
  setup() {
    const rewardDetailService = inject('rewardDetailService', () => new RewardDetailService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const rewardDetail: Ref<IRewardDetail> = ref({});

    const retrieveRewardDetail = async rewardDetailId => {
      try {
        const res = await rewardDetailService().find(rewardDetailId);
        rewardDetail.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.rewardDetailId) {
      retrieveRewardDetail(route.params.rewardDetailId);
    }

    return {
      alertService,
      rewardDetail,

      previousState,
      t$: useI18n().t,
    };
  },
});
