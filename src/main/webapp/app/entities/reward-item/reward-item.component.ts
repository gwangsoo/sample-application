import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import RewardItemService from './reward-item.service';
import { type IRewardItem } from '@/shared/model/reward-item.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RewardItem',
  setup() {
    const { t: t$ } = useI18n();
    const rewardItemService = inject('rewardItemService', () => new RewardItemService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const rewardItems: Ref<IRewardItem[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveRewardItems = async () => {
      isFetching.value = true;
      try {
        const res = await rewardItemService().retrieve();
        rewardItems.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveRewardItems();
    };

    onMounted(async () => {
      await retrieveRewardItems();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IRewardItem) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeRewardItem = async () => {
      try {
        await rewardItemService().delete(removeId.value);
        const message = t$('tossApp.rewardItem.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveRewardItems();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      rewardItems,
      handleSyncList,
      isFetching,
      retrieveRewardItems,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeRewardItem,
      t$,
    };
  },
});
