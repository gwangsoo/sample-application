import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import CurrencyService from './currency.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type ICurrency, Currency } from '@/shared/model/currency.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CurrencyUpdate',
  setup() {
    const currencyService = inject('currencyService', () => new CurrencyService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const currency: Ref<ICurrency> = ref(new Currency());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveCurrency = async currencyId => {
      try {
        const res = await currencyService().find(currencyId);
        currency.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.currencyId) {
      retrieveCurrency(route.params.currencyId);
    }

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 128 }).toString(), 128),
      },
    };
    const v$ = useVuelidate(validationRules, currency as any);
    v$.value.$validate();

    return {
      currencyService,
      alertService,
      currency,
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
      if (this.currency.id) {
        this.currencyService()
          .update(this.currency)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.currency.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.currencyService()
          .create(this.currency)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.currency.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
