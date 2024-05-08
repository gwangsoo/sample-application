<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" auth="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.game.home.createOrEditLabel"
          data-cy="GameCreateUpdateHeading"
          v-text="t$('tossApp.game.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="game.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="game.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.game.gameCategoryType')" for="game-gameCategoryType"></label>
            <select
              class="form-control"
              name="gameCategoryType"
              :class="{ valid: !v$.gameCategoryType.$invalid, invalid: v$.gameCategoryType.$invalid }"
              v-model="v$.gameCategoryType.$model"
              id="game-gameCategoryType"
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
            <label class="form-control-label" v-text="t$('tossApp.game.name')" for="game-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="game-name"
              data-cy="name"
              :class="{ valid: !v$.name.$invalid, invalid: v$.name.$invalid }"
              v-model="v$.name.$model"
              required
            />
            <div v-if="v$.name.$anyDirty && v$.name.$invalid">
              <small class="form-text text-danger" v-for="error of v$.name.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.game.description')" for="game-description"></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="game-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
              required
            />
            <div v-if="v$.description.$anyDirty && v$.description.$invalid">
              <small class="form-text text-danger" v-for="error of v$.description.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.game.roundNumDefault')" for="game-roundNumDefault"></label>
            <input
              type="number"
              class="form-control"
              name="roundNumDefault"
              id="game-roundNumDefault"
              data-cy="roundNumDefault"
              :class="{ valid: !v$.roundNumDefault.$invalid, invalid: v$.roundNumDefault.$invalid }"
              v-model.number="v$.roundNumDefault.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.game.roundNumMin')" for="game-roundNumMin"></label>
            <input
              type="number"
              class="form-control"
              name="roundNumMin"
              id="game-roundNumMin"
              data-cy="roundNumMin"
              :class="{ valid: !v$.roundNumMin.$invalid, invalid: v$.roundNumMin.$invalid }"
              v-model.number="v$.roundNumMin.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.game.roundNumMax')" for="game-roundNumMax"></label>
            <input
              type="number"
              class="form-control"
              name="roundNumMax"
              id="game-roundNumMax"
              data-cy="roundNumMax"
              :class="{ valid: !v$.roundNumMax.$invalid, invalid: v$.roundNumMax.$invalid }"
              v-model.number="v$.roundNumMax.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.game.roundNumUnlimit')" for="game-roundNumUnlimit"></label>
            <input
              type="number"
              class="form-control"
              name="roundNumUnlimit"
              id="game-roundNumUnlimit"
              data-cy="roundNumUnlimit"
              :class="{ valid: !v$.roundNumUnlimit.$invalid, invalid: v$.roundNumUnlimit.$invalid }"
              v-model.number="v$.roundNumUnlimit.$model"
            />
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
<script lang="ts" src="./game-update.component.ts"></script>
