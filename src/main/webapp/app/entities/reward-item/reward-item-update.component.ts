import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import RewardItemService from './reward-item.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import FileInfoService from '@/entities/file-info/file-info.service';
import { type IFileInfo } from '@/shared/model/file-info.model';
import RewardDetailService from '@/entities/reward-detail/reward-detail.service';
import { type IRewardDetail } from '@/shared/model/reward-detail.model';
import { type IRewardItem, RewardItem } from '@/shared/model/reward-item.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RewardItemUpdate',
  setup() {
    const rewardItemService = inject('rewardItemService', () => new RewardItemService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const rewardItem: Ref<IRewardItem> = ref(new RewardItem());

    const fileInfoService = inject('fileInfoService', () => new FileInfoService());

    const fileInfos: Ref<IFileInfo[]> = ref([]);

    const rewardDetailService = inject('rewardDetailService', () => new RewardDetailService());

    const rewardDetails: Ref<IRewardDetail[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      fileInfoService()
        .retrieve()
        .then(res => {
          fileInfos.value = res.data;
        });
      rewardDetailService()
        .retrieve()
        .then(res => {
          rewardDetails.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 50 }).toString(), 50),
      },
      itemImage: {},
      rewardDetail: {},
    };
    const v$ = useVuelidate(validationRules, rewardItem as any);
    v$.value.$validate();

    return {
      rewardItemService,
      alertService,
      rewardItem,
      previousState,
      isSaving,
      currentLanguage,
      fileInfos,
      rewardDetails,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.rewardItem.id) {
        this.rewardItemService()
          .update(this.rewardItem)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.rewardItem.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.rewardItemService()
          .create(this.rewardItem)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.rewardItem.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
