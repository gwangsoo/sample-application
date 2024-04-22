import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import MatchFormatGameService from './match-format-game.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import GameService from '@/entities/game/game.service';
import { type IGame } from '@/shared/model/game.model';
import MatchFormatService from '@/entities/match-format/match-format.service';
import { type IMatchFormat } from '@/shared/model/match-format.model';
import { type IMatchFormatGame, MatchFormatGame } from '@/shared/model/match-format-game.model';
import { GameCategoryType } from '@/shared/model/enumerations/game-category-type.model';
import { GameType } from '@/shared/model/enumerations/game-type.model';
import { MachineCreditType } from '@/shared/model/enumerations/machine-credit-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MatchFormatGameUpdate',
  setup() {
    const matchFormatGameService = inject('matchFormatGameService', () => new MatchFormatGameService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const matchFormatGame: Ref<IMatchFormatGame> = ref(new MatchFormatGame());

    const gameService = inject('gameService', () => new GameService());

    const games: Ref<IGame[]> = ref([]);

    const matchFormatService = inject('matchFormatService', () => new MatchFormatService());

    const matchFormats: Ref<IMatchFormat[]> = ref([]);
    const gameCategoryTypeValues: Ref<string[]> = ref(Object.keys(GameCategoryType));
    const gameTypeValues: Ref<string[]> = ref(Object.keys(GameType));
    const machineCreditTypeValues: Ref<string[]> = ref(Object.keys(MachineCreditType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveMatchFormatGame = async matchFormatGameId => {
      try {
        const res = await matchFormatGameService().find(matchFormatGameId);
        matchFormatGame.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.matchFormatGameId) {
      retrieveMatchFormatGame(route.params.matchFormatGameId);
    }

    const initRelationships = () => {
      gameService()
        .retrieve()
        .then(res => {
          games.value = res.data;
        });
      matchFormatService()
        .retrieve()
        .then(res => {
          matchFormats.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      gameCategoryType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      gameType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      roundNum: {
        required: validations.required(t$('entity.validation.required').toString()),
        integer: validations.integer(t$('entity.validation.number').toString()),
      },
      machineCreditType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      includingChoiceGame: {},
      game: {},
      matchFormat: {},
    };
    const v$ = useVuelidate(validationRules, matchFormatGame as any);
    v$.value.$validate();

    return {
      matchFormatGameService,
      alertService,
      matchFormatGame,
      previousState,
      gameCategoryTypeValues,
      gameTypeValues,
      machineCreditTypeValues,
      isSaving,
      currentLanguage,
      games,
      matchFormats,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.matchFormatGame.id) {
        this.matchFormatGameService()
          .update(this.matchFormatGame)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('tossApp.matchFormatGame.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.matchFormatGameService()
          .create(this.matchFormatGame)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('tossApp.matchFormatGame.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
