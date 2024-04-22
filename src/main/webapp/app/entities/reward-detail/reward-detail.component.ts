import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import RewardDetailService from './reward-detail.service';
import { type IRewardDetail } from '@/shared/model/reward-detail.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RewardDetail',
  setup() {
    const { t: t$ } = useI18n();
    const rewardDetailService = inject('rewardDetailService', () => new RewardDetailService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const rewardDetails: Ref<IRewardDetail[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveRewardDetails = async () => {
      isFetching.value = true;
      try {
        const res = await rewardDetailService().retrieve();
        rewardDetails.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveRewardDetails();
    };

    onMounted(async () => {
      await retrieveRewardDetails();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IRewardDetail) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeRewardDetail = async () => {
      try {
        await rewardDetailService().delete(removeId.value);
        const message = t$('tossApp.rewardDetail.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveRewardDetails();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      rewardDetails,
      handleSyncList,
      isFetching,
      retrieveRewardDetails,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeRewardDetail,
      t$,
    };
  },
});
