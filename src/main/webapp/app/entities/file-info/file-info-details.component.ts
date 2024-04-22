import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import FileInfoService from './file-info.service';
import { type IFileInfo } from '@/shared/model/file-info.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'FileInfoDetails',
  setup() {
    const fileInfoService = inject('fileInfoService', () => new FileInfoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const fileInfo: Ref<IFileInfo> = ref({});

    const retrieveFileInfo = async fileInfoId => {
      try {
        const res = await fileInfoService().find(fileInfoId);
        fileInfo.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.fileInfoId) {
      retrieveFileInfo(route.params.fileInfoId);
    }

    return {
      alertService,
      fileInfo,

      previousState,
      t$: useI18n().t,
    };
  },
});
