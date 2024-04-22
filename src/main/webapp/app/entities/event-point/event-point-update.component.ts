import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import EventPointService from './event-point.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import DivisionService from '@/entities/division/division.service';
import { type IDivision } from '@/shared/model/division.model';
import { type IEventPoint, EventPoint } from '@/shared/model/event-point.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EventPointUpdate',
  setup() {
    const eventPointService = inject('eventPointService', () => new EventPointService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const eventPoint: Ref<IEventPoint> = ref(new EventPoint());

    const divisionService = inject('divisionService', () => new DivisionService());

    const divisions: Ref<IDivision[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveEventPoint = async eventPointId => {
      try {
        const res = await eventPointService().find(eventPointId);
        eventPoint.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.eventPointId) {
      retrieveEventPoint(route.params.eventPointId);
    }

    const initRelationships = () => {
      divisionService()
        .retrieve()
        .then(res => {
          divisions.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      seq: {
        required: validations.required(t$('entity.validation.required').toString()),
        integer: validations.integer(t$('entity.validation.number').toString()),
      },
      rating: {
        required: validations.required(t$('entity.validation.required').toString()),
        integer: validations.integer(t$('entity.validation.number').toString()),
      },
      ratingMin: {},
      ratingMax: {},
      division: {},
    };
    const v$ = useVuelidate(validationRules, eventPoint as any);
    v$.value.$validate();

    return {
      eventPointService,
      alertService,
      eventPoint,
      previousState,
      isSaving,
      currentLanguage,
      divisions,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.eventPoint.id) {
        this.eventPointService()
          .update(this.eventPoint)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.eventPoint.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.eventPointService()
          .create(this.eventPoint)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.eventPoint.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
