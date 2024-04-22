import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import MachineService from './machine.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import MatchService from '@/entities/match/match.service';
import { type IMatch } from '@/shared/model/match.model';
import MachineAreaService from '@/entities/machine-area/machine-area.service';
import { type IMachineArea } from '@/shared/model/machine-area.model';
import { type IMachine, Machine } from '@/shared/model/machine.model';
import { MachineStatusType } from '@/shared/model/enumerations/machine-status-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MachineUpdate',
  setup() {
    const machineService = inject('machineService', () => new MachineService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const machine: Ref<IMachine> = ref(new Machine());

    const matchService = inject('matchService', () => new MatchService());

    const matches: Ref<IMatch[]> = ref([]);

    const machineAreaService = inject('machineAreaService', () => new MachineAreaService());

    const machineAreas: Ref<IMachineArea[]> = ref([]);
    const machineStatusTypeValues: Ref<string[]> = ref(Object.keys(MachineStatusType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveMachine = async machineId => {
      try {
        const res = await machineService().find(machineId);
        machine.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.machineId) {
      retrieveMachine(route.params.machineId);
    }

    const initRelationships = () => {
      matchService()
        .retrieve()
        .then(res => {
          matches.value = res.data;
        });
      machineAreaService()
        .retrieve()
        .then(res => {
          machineAreas.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      machineNo: {
        required: validations.required(t$('entity.validation.required').toString()),
        integer: validations.integer(t$('entity.validation.number').toString()),
      },
      machineStatusType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      match: {},
      machineArea: {},
    };
    const v$ = useVuelidate(validationRules, machine as any);
    v$.value.$validate();

    return {
      machineService,
      alertService,
      machine,
      previousState,
      machineStatusTypeValues,
      isSaving,
      currentLanguage,
      matches,
      machineAreas,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.machine.id) {
        this.machineService()
          .update(this.machine)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.machine.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.machineService()
          .create(this.machine)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.machine.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
