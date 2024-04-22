import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import MatchService from './match.service';
import { type IMatch } from '@/shared/model/match.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Match',
  setup() {
    const { t: t$ } = useI18n();
    const matchService = inject('matchService', () => new MatchService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const matches: Ref<IMatch[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveMatchs = async () => {
      isFetching.value = true;
      try {
        const res = await matchService().retrieve();
        matches.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveMatchs();
    };

    onMounted(async () => {
      await retrieveMatchs();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IMatch) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeMatch = async () => {
      try {
        await matchService().delete(removeId.value);
        const message = t$('tossApp.match.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveMatchs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      matches,
      handleSyncList,
      isFetching,
      retrieveMatchs,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeMatch,
      t$,
    };
  },
});
