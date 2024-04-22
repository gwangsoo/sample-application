import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import FileInfoService from './file-info.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IFileInfo, FileInfo } from '@/shared/model/file-info.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'FileInfoUpdate',
  setup() {
    const fileInfoService = inject('fileInfoService', () => new FileInfoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const fileInfo: Ref<IFileInfo> = ref(new FileInfo());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      originalName: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 256 }).toString(), 256),
      },
      mimeType: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 128 }).toString(), 128),
      },
      fileSize: {},
      savedPath: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 256 }).toString(), 256),
      },
    };
    const v$ = useVuelidate(validationRules, fileInfo as any);
    v$.value.$validate();

    return {
      fileInfoService,
      alertService,
      fileInfo,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.fileInfo.id) {
        this.fileInfoService()
          .update(this.fileInfo)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.fileInfo.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.fileInfoService()
          .create(this.fileInfo)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.fileInfo.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
