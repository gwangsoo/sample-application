<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.matchFormatLeg.home.createOrEditLabel"
          data-cy="MatchFormatLegCreateUpdateHeading"
          v-text="t$('tossApp.matchFormatLeg.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="matchFormatLeg.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="matchFormatLeg.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchFormatLeg.seq')" for="match-format-leg-seq"></label>
            <input
              type="number"
              class="form-control"
              name="seq"
              id="match-format-leg-seq"
              data-cy="seq"
              :class="{ valid: !v$.seq.$invalid, invalid: v$.seq.$invalid }"
              v-model.number="v$.seq.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.matchFormatLeg.firstThrowType')"
              for="match-format-leg-firstThrowType"
            ></label>
            <select
              class="form-control"
              name="firstThrowType"
              :class="{ valid: !v$.firstThrowType.$invalid, invalid: v$.firstThrowType.$invalid }"
              v-model="v$.firstThrowType.$model"
              id="match-format-leg-firstThrowType"
              data-cy="firstThrowType"
              required
            >
              <option
                v-for="firstThrowType in firstThrowTypeValues"
                :key="firstThrowType"
                v-bind:value="firstThrowType"
                v-bind:label="t$('tossApp.FirstThrowType.' + firstThrowType)"
              >
                {{ firstThrowType }}
              </option>
            </select>
            <div v-if="v$.firstThrowType.$anyDirty && v$.firstThrowType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.firstThrowType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchFormatLeg.playMode')" for="match-format-leg-playMode"></label>
            <select
              class="form-control"
              name="playMode"
              :class="{ valid: !v$.playMode.$invalid, invalid: v$.playMode.$invalid }"
              v-model="v$.playMode.$model"
              id="match-format-leg-playMode"
              data-cy="playMode"
            >
              <option
                v-for="legPlayMode in legPlayModeValues"
                :key="legPlayMode"
                v-bind:value="legPlayMode"
                v-bind:label="t$('tossApp.LegPlayMode.' + legPlayMode)"
              >
                {{ legPlayMode }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchFormatLeg.option')" for="match-format-leg-option"></label>
            <select class="form-control" id="match-format-leg-option" data-cy="option" name="option" v-model="matchFormatLeg.option">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  matchFormatLeg.option && matchFormatOptionOption.id === matchFormatLeg.option.id
                    ? matchFormatLeg.option
                    : matchFormatOptionOption
                "
                v-for="matchFormatOptionOption in matchFormatOptions"
                :key="matchFormatOptionOption.id"
              >
                {{ matchFormatOptionOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.matchFormatLeg.matchFormatSet')"
              for="match-format-leg-matchFormatSet"
            ></label>
            <select
              class="form-control"
              id="match-format-leg-matchFormatSet"
              data-cy="matchFormatSet"
              name="matchFormatSet"
              v-model="matchFormatLeg.matchFormatSet"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  matchFormatLeg.matchFormatSet && matchFormatSetOption.id === matchFormatLeg.matchFormatSet.id
                    ? matchFormatLeg.matchFormatSet
                    : matchFormatSetOption
                "
                v-for="matchFormatSetOption in matchFormatSets"
                :key="matchFormatSetOption.id"
              >
                {{ matchFormatSetOption.id }}
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
<script lang="ts" src="./match-format-leg-update.component.ts"></script>
