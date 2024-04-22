import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import EntryFeeService from './entry-fee.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import CurrencyService from '@/entities/currency/currency.service';
import { type ICurrency } from '@/shared/model/currency.model';
import { type IEntryFee, EntryFee } from '@/shared/model/entry-fee.model';
import { EntryFeeType } from '@/shared/model/enumerations/entry-fee-type.model';
import { EntryFeeSubType } from '@/shared/model/enumerations/entry-fee-sub-type.model';
import { PaymentMethodType } from '@/shared/model/enumerations/payment-method-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EntryFeeUpdate',
  setup() {
    const entryFeeService = inject('entryFeeService', () => new EntryFeeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const entryFee: Ref<IEntryFee> = ref(new EntryFee());

    const currencyService = inject('currencyService', () => new CurrencyService());

    const currencies: Ref<ICurrency[]> = ref([]);
    const entryFeeTypeValues: Ref<string[]> = ref(Object.keys(EntryFeeType));
    const entryFeeSubTypeValues: Ref<string[]> = ref(Object.keys(EntryFeeSubType));
    const paymentMethodTypeValues: Ref<string[]> = ref(Object.keys(PaymentMethodType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveEntryFee = async entryFeeId => {
      try {
        const res = await entryFeeService().find(entryFeeId);
        entryFee.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.entryFeeId) {
      retrieveEntryFee(route.params.entryFeeId);
    }

    const initRelationships = () => {
      currencyService()
        .retrieve()
        .then(res => {
          currencies.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      entryFeeType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      entryFeeSubType: {},
      paymentMethodType: {},
      scheduleNumber: {
        required: validations.required(t$('entity.validation.required').toString()),
        integer: validations.integer(t$('entity.validation.number').toString()),
        max: validations.maxValue(t$('entity.validation.max', { max: 1 }).toString(), 1),
      },
      fee: {},
      currency: {},
    };
    const v$ = useVuelidate(validationRules, entryFee as any);
    v$.value.$validate();

    return {
      entryFeeService,
      alertService,
      entryFee,
      previousState,
      entryFeeTypeValues,
      entryFeeSubTypeValues,
      paymentMethodTypeValues,
      isSaving,
      currentLanguage,
      currencies,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.entryFee.id) {
        this.entryFeeService()
          .update(this.entryFee)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.entryFee.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.entryFeeService()
          .create(this.entryFee)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.entryFee.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
