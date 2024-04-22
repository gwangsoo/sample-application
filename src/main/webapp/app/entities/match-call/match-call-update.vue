<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.matchCall.home.createOrEditLabel"
          data-cy="MatchCallCreateUpdateHeading"
          v-text="t$('tossApp.matchCall.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="matchCall.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="matchCall.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchCall.callDateTime')" for="match-call-callDateTime"></label>
            <div class="d-flex">
              <input
                id="match-call-callDateTime"
                data-cy="callDateTime"
                type="datetime-local"
                class="form-control"
                name="callDateTime"
                :class="{ valid: !v$.callDateTime.$invalid, invalid: v$.callDateTime.$invalid }"
                :value="convertDateTimeFromServer(v$.callDateTime.$model)"
                @change="updateZonedDateTimeField('callDateTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchCall.callMessage')" for="match-call-callMessage"></label>
            <input
              type="text"
              class="form-control"
              name="callMessage"
              id="match-call-callMessage"
              data-cy="callMessage"
              :class="{ valid: !v$.callMessage.$invalid, invalid: v$.callMessage.$invalid }"
              v-model="v$.callMessage.$model"
            />
            <div v-if="v$.callMessage.$anyDirty && v$.callMessage.$invalid">
              <small class="form-text text-danger" v-for="error of v$.callMessage.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchCall.matchTeam')" for="match-call-matchTeam"></label>
            <select class="form-control" id="match-call-matchTeam" data-cy="matchTeam" name="matchTeam" v-model="matchCall.matchTeam">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="matchCall.matchTeam && matchTeamOption.id === matchCall.matchTeam.id ? matchCall.matchTeam : matchTeamOption"
                v-for="matchTeamOption in matchTeams"
                :key="matchTeamOption.id"
              >
                {{ matchTeamOption.id }}
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
<script lang="ts" src="./match-call-update.component.ts"></script>
