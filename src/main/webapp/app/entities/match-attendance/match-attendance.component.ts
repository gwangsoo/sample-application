import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import MatchAttendanceService from './match-attendance.service';
import { type IMatchAttendance } from '@/shared/model/match-attendance.model';
import { useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchAttendance',
  setup() {
    const { t: t$ } = useI18n();
    const dateFormat = useDateFormat();
    const matchAttendanceService = inject('matchAttendanceService', () => new MatchAttendanceService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const matchAttendances: Ref<IMatchAttendance[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveMatchAttendances = async () => {
      isFetching.value = true;
      try {
        const res = await matchAttendanceService().retrieve();
        matchAttendances.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveMatchAttendances();
    };

    onMounted(async () => {
      await retrieveMatchAttendances();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IMatchAttendance) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeMatchAttendance = async () => {
      try {
        await matchAttendanceService().delete(removeId.value);
        const message = t$('tossApp.matchAttendance.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveMatchAttendances();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      matchAttendances,
      handleSyncList,
      isFetching,
      retrieveMatchAttendances,
      clear,
      ...dateFormat,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeMatchAttendance,
      t$,
    };
  },
});
