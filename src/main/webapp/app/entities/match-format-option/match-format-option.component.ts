import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import MatchFormatOptionService from './match-format-option.service';
import { type IMatchFormatOption } from '@/shared/model/match-format-option.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchFormatOption',
  setup() {
    const { t: t$ } = useI18n();
    const matchFormatOptionService = inject('matchFormatOptionService', () => new MatchFormatOptionService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const matchFormatOptions: Ref<IMatchFormatOption[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveMatchFormatOptions = async () => {
      isFetching.value = true;
      try {
        const res = await matchFormatOptionService().retrieve();
        matchFormatOptions.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveMatchFormatOptions();
    };

    onMounted(async () => {
      await retrieveMatchFormatOptions();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IMatchFormatOption) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeMatchFormatOption = async () => {
      try {
        await matchFormatOptionService().delete(removeId.value);
        const message = t$('tossApp.matchFormatOption.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveMatchFormatOptions();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      matchFormatOptions,
      handleSyncList,
      isFetching,
      retrieveMatchFormatOptions,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeMatchFormatOption,
      t$,
    };
  },
});
