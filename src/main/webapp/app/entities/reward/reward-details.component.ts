import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import RewardService from './reward.service';
import { type IReward } from '@/shared/model/reward.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RewardDetails',
  setup() {
    const rewardService = inject('rewardService', () => new RewardService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const reward: Ref<IReward> = ref({});

    const retrieveReward = async rewardId => {
      try {
        const res = await rewardService().find(rewardId);
        reward.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.rewardId) {
      retrieveReward(route.params.rewardId);
    }

    return {
      alertService,
      reward,

      previousState,
      t$: useI18n().t,
    };
  },
});
