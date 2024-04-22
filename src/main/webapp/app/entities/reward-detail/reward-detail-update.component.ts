import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import RewardDetailService from './reward-detail.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import RewardService from '@/entities/reward/reward.service';
import { type IReward } from '@/shared/model/reward.model';
import { type IRewardDetail, RewardDetail } from '@/shared/model/reward-detail.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RewardDetailUpdate',
  setup() {
    const rewardDetailService = inject('rewardDetailService', () => new RewardDetailService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const rewardDetail: Ref<IRewardDetail> = ref(new RewardDetail());

    const rewardService = inject('rewardService', () => new RewardService());

    const rewards: Ref<IReward[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveRewardDetail = async rewardDetailId => {
      try {
        const res = await rewardDetailService().find(rewardDetailId);
        rewardDetail.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.rewardDetailId) {
      retrieveRewardDetail(route.params.rewardDetailId);
    }

    const initRelationships = () => {
      rewardService()
        .retrieve()
        .then(res => {
          rewards.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      name: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 256 }).toString(), 256),
      },
      tournamentId: {},
      divisionId: {},
      reward: {},
    };
    const v$ = useVuelidate(validationRules, rewardDetail as any);
    v$.value.$validate();

    return {
      rewardDetailService,
      alertService,
      rewardDetail,
      previousState,
      isSaving,
      currentLanguage,
      rewards,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.rewardDetail.id) {
        this.rewardDetailService()
          .update(this.rewardDetail)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.rewardDetail.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.rewardDetailService()
          .create(this.rewardDetail)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.rewardDetail.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
