import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import AffiliatedInfoService from './affiliated-info.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import FileInfoService from '@/entities/file-info/file-info.service';
import { type IFileInfo } from '@/shared/model/file-info.model';
import { type IAffiliatedInfo, AffiliatedInfo } from '@/shared/model/affiliated-info.model';
import { AffiliatedType } from '@/shared/model/enumerations/affiliated-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AffiliatedInfoUpdate',
  setup() {
    const affiliatedInfoService = inject('affiliatedInfoService', () => new AffiliatedInfoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const affiliatedInfo: Ref<IAffiliatedInfo> = ref(new AffiliatedInfo());

    const fileInfoService = inject('fileInfoService', () => new FileInfoService());

    const fileInfos: Ref<IFileInfo[]> = ref([]);
    const affiliatedTypeValues: Ref<string[]> = ref(Object.keys(AffiliatedType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveAffiliatedInfo = async affiliatedInfoId => {
      try {
        const res = await affiliatedInfoService().find(affiliatedInfoId);
        affiliatedInfo.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.affiliatedInfoId) {
      retrieveAffiliatedInfo(route.params.affiliatedInfoId);
    }

    const initRelationships = () => {
      fileInfoService()
        .retrieve()
        .then(res => {
          fileInfos.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      affiliatedType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      seq: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 20 }).toString(), 20),
      },
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 256 }).toString(), 256),
      },
      address: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 500 }).toString(), 500),
      },
      telNo: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 30 }).toString(), 30),
      },
      homepageUrl: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 256 }).toString(), 256),
      },
      fileInfo: {},
    };
    const v$ = useVuelidate(validationRules, affiliatedInfo as any);
    v$.value.$validate();

    return {
      affiliatedInfoService,
      alertService,
      affiliatedInfo,
      previousState,
      affiliatedTypeValues,
      isSaving,
      currentLanguage,
      fileInfos,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.affiliatedInfo.id) {
        this.affiliatedInfoService()
          .update(this.affiliatedInfo)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.affiliatedInfo.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.affiliatedInfoService()
          .create(this.affiliatedInfo)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.affiliatedInfo.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
