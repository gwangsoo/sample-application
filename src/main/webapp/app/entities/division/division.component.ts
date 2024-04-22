import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import DivisionService from './division.service';
import { type IDivision } from '@/shared/model/division.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Division',
  setup() {
    const { t: t$ } = useI18n();
    const divisionService = inject('divisionService', () => new DivisionService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const divisions: Ref<IDivision[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveDivisions = async () => {
      isFetching.value = true;
      try {
        const res = await divisionService().retrieve();
        divisions.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveDivisions();
    };

    onMounted(async () => {
      await retrieveDivisions();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IDivision) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeDivision = async () => {
      try {
        await divisionService().delete(removeId.value);
        const message = t$('tossApp.division.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveDivisions();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      divisions,
      handleSyncList,
      isFetching,
      retrieveDivisions,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeDivision,
      t$,
    };
  },
});
