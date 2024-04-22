<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.matchFormatGame.home.createOrEditLabel"
          data-cy="MatchFormatGameCreateUpdateHeading"
          v-text="t$('tossApp.matchFormatGame.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="matchFormatGame.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="matchFormatGame.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.matchFormatGame.gameCategoryType')"
              for="match-format-game-gameCategoryType"
            ></label>
            <select
              class="form-control"
              name="gameCategoryType"
              :class="{ valid: !v$.gameCategoryType.$invalid, invalid: v$.gameCategoryType.$invalid }"
              v-model="v$.gameCategoryType.$model"
              id="match-format-game-gameCategoryType"
              data-cy="gameCategoryType"
              required
            >
              <option
                v-for="gameCategoryType in gameCategoryTypeValues"
                :key="gameCategoryType"
                v-bind:value="gameCategoryType"
                v-bind:label="t$('tossApp.GameCategoryType.' + gameCategoryType)"
              >
                {{ gameCategoryType }}
              </option>
            </select>
            <div v-if="v$.gameCategoryType.$anyDirty && v$.gameCategoryType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.gameCategoryType.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchFormatGame.gameType')" for="match-format-game-gameType"></label>
            <select
              class="form-control"
              name="gameType"
              :class="{ valid: !v$.gameType.$invalid, invalid: v$.gameType.$invalid }"
              v-model="v$.gameType.$model"
              id="match-format-game-gameType"
              data-cy="gameType"
              required
            >
              <option
                v-for="gameType in gameTypeValues"
                :key="gameType"
                v-bind:value="gameType"
                v-bind:label="t$('tossApp.GameType.' + gameType)"
              >
                {{ gameType }}
              </option>
            </select>
            <div v-if="v$.gameType.$anyDirty && v$.gameType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.gameType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchFormatGame.roundNum')" for="match-format-game-roundNum"></label>
            <input
              type="number"
              class="form-control"
              name="roundNum"
              id="match-format-game-roundNum"
              data-cy="roundNum"
              :class="{ valid: !v$.roundNum.$invalid, invalid: v$.roundNum.$invalid }"
              v-model.number="v$.roundNum.$model"
              required
            />
            <div v-if="v$.roundNum.$anyDirty && v$.roundNum.$invalid">
              <small class="form-text text-danger" v-for="error of v$.roundNum.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.matchFormatGame.machineCreditType')"
              for="match-format-game-machineCreditType"
            ></label>
            <select
              class="form-control"
              name="machineCreditType"
              :class="{ valid: !v$.machineCreditType.$invalid, invalid: v$.machineCreditType.$invalid }"
              v-model="v$.machineCreditType.$model"
              id="match-format-game-machineCreditType"
              data-cy="machineCreditType"
              required
            >
              <option
                v-for="machineCreditType in machineCreditTypeValues"
                :key="machineCreditType"
                v-bind:value="machineCreditType"
                v-bind:label="t$('tossApp.MachineCreditType.' + machineCreditType)"
              >
                {{ machineCreditType }}
              </option>
            </select>
            <div v-if="v$.machineCreditType.$anyDirty && v$.machineCreditType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.machineCreditType.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.matchFormatGame.includingChoiceGame')"
              for="match-format-game-includingChoiceGame"
            ></label>
            <input
              type="checkbox"
              class="form-check"
              name="includingChoiceGame"
              id="match-format-game-includingChoiceGame"
              data-cy="includingChoiceGame"
              :class="{ valid: !v$.includingChoiceGame.$invalid, invalid: v$.includingChoiceGame.$invalid }"
              v-model="v$.includingChoiceGame.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchFormatGame.game')" for="match-format-game-game"></label>
            <select class="form-control" id="match-format-game-game" data-cy="game" name="game" v-model="matchFormatGame.game">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="matchFormatGame.game && gameOption.id === matchFormatGame.game.id ? matchFormatGame.game : gameOption"
                v-for="gameOption in games"
                :key="gameOption.id"
              >
                {{ gameOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.matchFormatGame.matchFormat')"
              for="match-format-game-matchFormat"
            ></label>
            <select
              class="form-control"
              id="match-format-game-matchFormat"
              data-cy="matchFormat"
              name="matchFormat"
              v-model="matchFormatGame.matchFormat"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  matchFormatGame.matchFormat && matchFormatOption.id === matchFormatGame.matchFormat.id
                    ? matchFormatGame.matchFormat
                    : matchFormatOption
                "
                v-for="matchFormatOption in matchFormats"
                :key="matchFormatOption.id"
              >
                {{ matchFormatOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./match-format-game-update.component.ts"></script>
