import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import LanguageService from './language.service';
import { type ILanguage } from '@/shared/model/language.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Language',
  setup() {
    const { t: t$ } = useI18n();
    const languageService = inject('languageService', () => new LanguageService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const languages: Ref<ILanguage[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveLanguages = async () => {
      isFetching.value = true;
      try {
        const res = await languageService().retrieve();
        languages.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveLanguages();
    };

    onMounted(async () => {
      await retrieveLanguages();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: ILanguage) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeLanguage = async () => {
      try {
        await languageService().delete(removeId.value);
        const message = t$('tossApp.language.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveLanguages();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      languages,
      handleSyncList,
      isFetching,
      retrieveLanguages,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeLanguage,
      t$,
    };
  },
});
