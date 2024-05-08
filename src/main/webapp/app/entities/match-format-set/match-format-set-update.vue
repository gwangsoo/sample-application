<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" auth="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.matchFormatSet.home.createOrEditLabel"
          data-cy="MatchFormatSetCreateUpdateHeading"
          v-text="t$('tossApp.matchFormatSet.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="matchFormatSet.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="matchFormatSet.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchFormatSet.point')" for="match-format-set-point"></label>
            <input
              type="number"
              class="form-control"
              name="point"
              id="match-format-set-point"
              data-cy="point"
              :class="{ valid: !v$.point.$invalid, invalid: v$.point.$invalid }"
              v-model.number="v$.point.$model"
              required
            />
            <div v-if="v$.point.$anyDirty && v$.point.$invalid">
              <small class="form-text text-danger" v-for="error of v$.point.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchFormatSet.matchFormat')" for="match-format-set-matchFormat"></label>
            <select
              class="form-control"
              id="match-format-set-matchFormat"
              data-cy="matchFormat"
              name="matchFormat"
              v-model="matchFormatSet.matchFormat"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  matchFormatSet.matchFormat && matchFormatOption.id === matchFormatSet.matchFormat.id
                    ? matchFormatSet.matchFormat
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
<script lang="ts" src="./match-format-set-update.component.ts"></script>
