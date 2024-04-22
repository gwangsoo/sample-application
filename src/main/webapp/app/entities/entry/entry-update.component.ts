import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import EntryService from './entry.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import TeamService from '@/entities/team/team.service';
import { type ITeam } from '@/shared/model/team.model';
import { type IEntry, Entry } from '@/shared/model/entry.model';
import { GenderType } from '@/shared/model/enumerations/gender-type.model';
import { AttendanceStatusType } from '@/shared/model/enumerations/attendance-status-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EntryUpdate',
  setup() {
    const entryService = inject('entryService', () => new EntryService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const entry: Ref<IEntry> = ref(new Entry());

    const teamService = inject('teamService', () => new TeamService());

    const teams: Ref<ITeam[]> = ref([]);
    const genderTypeValues: Ref<string[]> = ref(Object.keys(GenderType));
    const attendanceStatusTypeValues: Ref<string[]> = ref(Object.keys(AttendanceStatusType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveEntry = async entryId => {
      try {
        const res = await entryService().find(entryId);
        entry.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.entryId) {
      retrieveEntry(route.params.entryId);
    }

    const initRelationships = () => {
      teamService()
        .retrieve()
        .then(res => {
          teams.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      entryNo: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 4 }).toString(), 4),
      },
      phoenixNo: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 50 }).toString(), 50),
      },
      cardNo: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 16 }).toString(), 16),
      },
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 256 }).toString(), 256),
      },
      englishName: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 256 }).toString(), 256),
      },
      rating: {
        required: validations.required(t$('entity.validation.required').toString()),
        min: validations.minValue(t$('entity.validation.min', { min: 0 }).toString(), 0),
      },
      mobileNo: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 20 }).toString(), 20),
      },
      birthDate: {},
      email: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 256 }).toString(), 256),
      },
      genderType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      attendanceStatusType: {},
      team: {},
    };
    const v$ = useVuelidate(validationRules, entry as any);
    v$.value.$validate();

    return {
      entryService,
      alertService,
      entry,
      previousState,
      genderTypeValues,
      attendanceStatusTypeValues,
      isSaving,
      currentLanguage,
      teams,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.entry.id) {
        this.entryService()
          .update(this.entry)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.entry.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.entryService()
          .create(this.entry)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.entry.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
