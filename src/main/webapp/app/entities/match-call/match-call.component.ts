import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import MatchCallService from './match-call.service';
import { type IMatchCall } from '@/shared/model/match-call.model';
import { useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchCall',
  setup() {
    const { t: t$ } = useI18n();
    const dateFormat = useDateFormat();
    const matchCallService = inject('matchCallService', () => new MatchCallService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const matchCalls: Ref<IMatchCall[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveMatchCalls = async () => {
      isFetching.value = true;
      try {
        const res = await matchCallService().retrieve();
        matchCalls.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveMatchCalls();
    };

    onMounted(async () => {
      await retrieveMatchCalls();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IMatchCall) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeMatchCall = async () => {
      try {
        await matchCallService().delete(removeId.value);
        const message = t$('tossApp.matchCall.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveMatchCalls();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      matchCalls,
      handleSyncList,
      isFetching,
      retrieveMatchCalls,
      clear,
      ...dateFormat,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeMatchCall,
      t$,
    };
  },
});
