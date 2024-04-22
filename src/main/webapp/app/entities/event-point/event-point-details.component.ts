import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import EventPointService from './event-point.service';
import { type IEventPoint } from '@/shared/model/event-point.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EventPointDetails',
  setup() {
    const eventPointService = inject('eventPointService', () => new EventPointService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const eventPoint: Ref<IEventPoint> = ref({});

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

    return {
      alertService,
      eventPoint,

      previousState,
      t$: useI18n().t,
    };
  },
});
