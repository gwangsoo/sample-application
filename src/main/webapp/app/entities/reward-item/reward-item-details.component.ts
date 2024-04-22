import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import RewardItemService from './reward-item.service';
import { type IRewardItem } from '@/shared/model/reward-item.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RewardItemDetails',
  setup() {
    const rewardItemService = inject('rewardItemService', () => new RewardItemService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const rewardItem: Ref<IRewardItem> = ref({});

    const retrieveRewardItem = async rewardItemId => {
      try {
        const res = await rewardItemService().find(rewardItemId);
        rewardItem.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.rewardItemId) {
      retrieveRewardItem(route.params.rewardItemId);
    }

    return {
      alertService,
      rewardItem,

      previousState,
      t$: useI18n().t,
    };
  },
});
