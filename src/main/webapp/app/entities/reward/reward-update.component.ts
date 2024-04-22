import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import RewardService from './reward.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IReward, Reward } from '@/shared/model/reward.model';
import { RewardMethodType } from '@/shared/model/enumerations/reward-method-type.model';
import { RewardMethodSubType } from '@/shared/model/enumerations/reward-method-sub-type.model';
import { MachineKindType } from '@/shared/model/enumerations/machine-kind-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RewardUpdate',
  setup() {
    const rewardService = inject('rewardService', () => new RewardService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const reward: Ref<IReward> = ref(new Reward());
    const rewardMethodTypeValues: Ref<string[]> = ref(Object.keys(RewardMethodType));
    const rewardMethodSubTypeValues: Ref<string[]> = ref(Object.keys(RewardMethodSubType));
    const machineKindTypeValues: Ref<string[]> = ref(Object.keys(MachineKindType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveReward = async rewardId => {
      try {
        const res = await rewardService().find(rewardId);
        reward.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.rewardId) {
      retrieveReward(route.params.rewardId);
    }

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      rewardMethodType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      rewardMethodSubType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      rewardCategoryNum: {
        required: validations.required(t$('entity.validation.required').toString()),
        integer: validations.integer(t$('entity.validation.number').toString()),
      },
      machineKindType: {},
    };
    const v$ = useVuelidate(validationRules, reward as any);
    v$.value.$validate();

    return {
      rewardService,
      alertService,
      reward,
      previousState,
      rewardMethodTypeValues,
      rewardMethodSubTypeValues,
      machineKindTypeValues,
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
      if (this.reward.id) {
        this.rewardService()
          .update(this.reward)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.reward.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.rewardService()
          .create(this.reward)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.reward.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
