import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import MachineAreaService from './machine-area.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import CompetitionService from '@/entities/competition/competition.service';
import { type ICompetition } from '@/shared/model/competition.model';
import { type IMachineArea, MachineArea } from '@/shared/model/machine-area.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MachineAreaUpdate',
  setup() {
    const machineAreaService = inject('machineAreaService', () => new MachineAreaService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const machineArea: Ref<IMachineArea> = ref(new MachineArea());

    const competitionService = inject('competitionService', () => new CompetitionService());

    const competitions: Ref<ICompetition[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveMachineArea = async machineAreaId => {
      try {
        const res = await machineAreaService().find(machineAreaId);
        machineArea.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.machineAreaId) {
      retrieveMachineArea(route.params.machineAreaId);
    }

    const initRelationships = () => {
      competitionService()
        .retrieve()
        .then(res => {
          competitions.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      mame: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 256 }).toString(), 256),
      },
      seq: {
        required: validations.required(t$('entity.validation.required').toString()),
        integer: validations.integer(t$('entity.validation.number').toString()),
      },
      numOfMachine: {},
      competition: {},
    };
    const v$ = useVuelidate(validationRules, machineArea as any);
    v$.value.$validate();

    return {
      machineAreaService,
      alertService,
      machineArea,
      previousState,
      isSaving,
      currentLanguage,
      competitions,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.machineArea.id) {
        this.machineAreaService()
          .update(this.machineArea)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.machineArea.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.machineAreaService()
          .create(this.machineArea)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.machineArea.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
