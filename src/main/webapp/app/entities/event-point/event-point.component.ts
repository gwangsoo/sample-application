import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import EventPointService from './event-point.service';
import { type IEventPoint } from '@/shared/model/event-point.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EventPoint',
  setup() {
    const { t: t$ } = useI18n();
    const eventPointService = inject('eventPointService', () => new EventPointService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const eventPoints: Ref<IEventPoint[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveEventPoints = async () => {
      isFetching.value = true;
      try {
        const res = await eventPointService().retrieve();
        eventPoints.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveEventPoints();
    };

    onMounted(async () => {
      await retrieveEventPoints();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IEventPoint) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeEventPoint = async () => {
      try {
        await eventPointService().delete(removeId.value);
        const message = t$('tossApp.eventPoint.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveEventPoints();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      eventPoints,
      handleSyncList,
      isFetching,
      retrieveEventPoints,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeEventPoint,
      t$,
    };
  },
});
