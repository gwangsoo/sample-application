import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import AffiliatedInfoService from './affiliated-info.service';
import { type IAffiliatedInfo } from '@/shared/model/affiliated-info.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AffiliatedInfo',
  setup() {
    const { t: t$ } = useI18n();
    const affiliatedInfoService = inject('affiliatedInfoService', () => new AffiliatedInfoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const affiliatedInfos: Ref<IAffiliatedInfo[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveAffiliatedInfos = async () => {
      isFetching.value = true;
      try {
        const res = await affiliatedInfoService().retrieve();
        affiliatedInfos.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveAffiliatedInfos();
    };

    onMounted(async () => {
      await retrieveAffiliatedInfos();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IAffiliatedInfo) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeAffiliatedInfo = async () => {
      try {
        await affiliatedInfoService().delete(removeId.value);
        const message = t$('tossApp.affiliatedInfo.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveAffiliatedInfos();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      affiliatedInfos,
      handleSyncList,
      isFetching,
      retrieveAffiliatedInfos,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeAffiliatedInfo,
      t$,
    };
  },
});
