import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import FileInfoService from './file-info.service';
import { type IFileInfo } from '@/shared/model/file-info.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'FileInfo',
  setup() {
    const { t: t$ } = useI18n();
    const fileInfoService = inject('fileInfoService', () => new FileInfoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const fileInfos: Ref<IFileInfo[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveFileInfos = async () => {
      isFetching.value = true;
      try {
        const res = await fileInfoService().retrieve();
        fileInfos.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveFileInfos();
    };

    onMounted(async () => {
      await retrieveFileInfos();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IFileInfo) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeFileInfo = async () => {
      try {
        await fileInfoService().delete(removeId.value);
        const message = t$('tossApp.fileInfo.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveFileInfos();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      fileInfos,
      handleSyncList,
      isFetching,
      retrieveFileInfos,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeFileInfo,
      t$,
    };
  },
});
