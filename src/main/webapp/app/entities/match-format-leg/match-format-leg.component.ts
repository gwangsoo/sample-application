import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import MatchFormatLegService from './match-format-leg.service';
import { type IMatchFormatLeg } from '@/shared/model/match-format-leg.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchFormatLeg',
  setup() {
    const { t: t$ } = useI18n();
    const matchFormatLegService = inject('matchFormatLegService', () => new MatchFormatLegService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const matchFormatLegs: Ref<IMatchFormatLeg[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveMatchFormatLegs = async () => {
      isFetching.value = true;
      try {
        const res = await matchFormatLegService().retrieve();
        matchFormatLegs.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveMatchFormatLegs();
    };

    onMounted(async () => {
      await retrieveMatchFormatLegs();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IMatchFormatLeg) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeMatchFormatLeg = async () => {
      try {
        await matchFormatLegService().delete(removeId.value);
        const message = t$('tossApp.matchFormatLeg.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveMatchFormatLegs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      matchFormatLegs,
      handleSyncList,
      isFetching,
      retrieveMatchFormatLegs,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeMatchFormatLeg,
      t$,
    };
  },
});
