<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.matchFormat.home.createOrEditLabel"
          data-cy="MatchFormatCreateUpdateHeading"
          v-text="t$('tossApp.matchFormat.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="matchFormat.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="matchFormat.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchFormat.name')" for="match-format-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="match-format-name"
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
            <label class="form-control-label" v-text="t$('tossApp.matchFormat.description')" for="match-format-description"></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="match-format-description"
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
            <label class="form-control-label" v-text="t$('tossApp.matchFormat.matchFormatType')" for="match-format-matchFormatType"></label>
            <select
              class="form-control"
              name="matchFormatType"
              :class="{ valid: !v$.matchFormatType.$invalid, invalid: v$.matchFormatType.$invalid }"
              v-model="v$.matchFormatType.$model"
              id="match-format-matchFormatType"
              data-cy="matchFormatType"
              required
            >
              <option
                v-for="matchFormatType in matchFormatTypeValues"
                :key="matchFormatType"
                v-bind:value="matchFormatType"
                v-bind:label="t$('tossApp.MatchFormatType.' + matchFormatType)"
              >
                {{ matchFormatType }}
              </option>
            </select>
            <div v-if="v$.matchFormatType.$anyDirty && v$.matchFormatType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.matchFormatType.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchFormat.firstSet')" for="match-format-firstSet"></label>
            <select
              class="form-control"
              name="firstSet"
              :class="{ valid: !v$.firstSet.$invalid, invalid: v$.firstSet.$invalid }"
              v-model="v$.firstSet.$model"
              id="match-format-firstSet"
              data-cy="firstSet"
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
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchFormat.middleSet')" for="match-format-middleSet"></label>
            <select
              class="form-control"
              name="middleSet"
              :class="{ valid: !v$.middleSet.$invalid, invalid: v$.middleSet.$invalid }"
              v-model="v$.middleSet.$model"
              id="match-format-middleSet"
              data-cy="middleSet"
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
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchFormat.lastSet')" for="match-format-lastSet"></label>
            <select
              class="form-control"
              name="lastSet"
              :class="{ valid: !v$.lastSet.$invalid, invalid: v$.lastSet.$invalid }"
              v-model="v$.lastSet.$model"
              id="match-format-lastSet"
              data-cy="lastSet"
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
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchFormat.division')" for="match-format-division"></label>
            <select class="form-control" id="match-format-division" data-cy="division" name="division" v-model="matchFormat.division">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="matchFormat.division && divisionOption.id === matchFormat.division.id ? matchFormat.division : divisionOption"
                v-for="divisionOption in divisions"
                :key="divisionOption.id"
              >
                {{ divisionOption.id }}
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
<script lang="ts" src="./match-format-update.component.ts"></script>
