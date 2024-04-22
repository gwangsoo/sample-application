import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import GameService from './game.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IGame, Game } from '@/shared/model/game.model';
import { GameCategoryType } from '@/shared/model/enumerations/game-category-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'GameUpdate',
  setup() {
    const gameService = inject('gameService', () => new GameService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const game: Ref<IGame> = ref(new Game());
    const gameCategoryTypeValues: Ref<string[]> = ref(Object.keys(GameCategoryType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveGame = async gameId => {
      try {
        const res = await gameService().find(gameId);
        game.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.gameId) {
      retrieveGame(route.params.gameId);
    }

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      gameCategoryType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 256 }).toString(), 256),
      },
      description: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 256 }).toString(), 256),
      },
      roundNumDefault: {},
      roundNumMin: {},
      roundNumMax: {},
      roundNumUnlimit: {},
    };
    const v$ = useVuelidate(validationRules, game as any);
    v$.value.$validate();

    return {
      gameService,
      alertService,
      game,
      previousState,
      gameCategoryTypeValues,
      isSaving,
      currentLanguage,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.game.id) {
        this.gameService()
          .update(this.game)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.game.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.gameService()
          .create(this.game)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.game.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
