import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import RewardService from './reward.service';
import { type IReward } from '@/shared/model/reward.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Reward',
  setup() {
    const { t: t$ } = useI18n();
    const rewardService = inject('rewardService', () => new RewardService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const rewards: Ref<IReward[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveRewards = async () => {
      isFetching.value = true;
      try {
        const res = await rewardService().retrieve();
        rewards.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveRewards();
    };

    onMounted(async () => {
      await retrieveRewards();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IReward) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeReward = async () => {
      try {
        await rewardService().delete(removeId.value);
        const message = t$('tossApp.reward.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveRewards();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      rewards,
      handleSyncList,
      isFetching,
      retrieveRewards,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeReward,
      t$,
    };
  },
});
