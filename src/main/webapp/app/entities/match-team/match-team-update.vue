<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" auth="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.matchTeam.home.createOrEditLabel"
          data-cy="MatchTeamCreateUpdateHeading"
          v-text="t$('tossApp.matchTeam.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="matchTeam.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="matchTeam.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchTeam.isWinner')" for="match-team-isWinner"></label>
            <input
              type="checkbox"
              class="form-check"
              name="isWinner"
              id="match-team-isWinner"
              data-cy="isWinner"
              :class="{ valid: !v$.isWinner.$invalid, invalid: v$.isWinner.$invalid }"
              v-model="v$.isWinner.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchTeam.avgPpd')" for="match-team-avgPpd"></label>
            <input
              type="number"
              class="form-control"
              name="avgPpd"
              id="match-team-avgPpd"
              data-cy="avgPpd"
              :class="{ valid: !v$.avgPpd.$invalid, invalid: v$.avgPpd.$invalid }"
              v-model.number="v$.avgPpd.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchTeam.avgMpr')" for="match-team-avgMpr"></label>
            <input
              type="number"
              class="form-control"
              name="avgMpr"
              id="match-team-avgMpr"
              data-cy="avgMpr"
              :class="{ valid: !v$.avgMpr.$invalid, invalid: v$.avgMpr.$invalid }"
              v-model.number="v$.avgMpr.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchTeam.winSet')" for="match-team-winSet"></label>
            <input
              type="number"
              class="form-control"
              name="winSet"
              id="match-team-winSet"
              data-cy="winSet"
              :class="{ valid: !v$.winSet.$invalid, invalid: v$.winSet.$invalid }"
              v-model.number="v$.winSet.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchTeam.winLeg')" for="match-team-winLeg"></label>
            <input
              type="number"
              class="form-control"
              name="winLeg"
              id="match-team-winLeg"
              data-cy="winLeg"
              :class="{ valid: !v$.winLeg.$invalid, invalid: v$.winLeg.$invalid }"
              v-model.number="v$.winLeg.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.matchTeam.playerCallModeType')"
              for="match-team-playerCallModeType"
            ></label>
            <select
              class="form-control"
              name="playerCallModeType"
              :class="{ valid: !v$.playerCallModeType.$invalid, invalid: v$.playerCallModeType.$invalid }"
              v-model="v$.playerCallModeType.$model"
              id="match-team-playerCallModeType"
              data-cy="playerCallModeType"
              required
            >
              <option
                v-for="playerCallModeType in playerCallModeTypeValues"
                :key="playerCallModeType"
                v-bind:value="playerCallModeType"
                v-bind:label="t$('tossApp.PlayerCallModeType.' + playerCallModeType)"
              >
                {{ playerCallModeType }}
              </option>
            </select>
            <div v-if="v$.playerCallModeType.$anyDirty && v$.playerCallModeType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.playerCallModeType.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchTeam.team')" for="match-team-team"></label>
            <select class="form-control" id="match-team-team" data-cy="team" name="team" v-model="matchTeam.team">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="matchTeam.team && teamOption.id === matchTeam.team.id ? matchTeam.team : teamOption"
                v-for="teamOption in teams"
                :key="teamOption.id"
              >
                {{ teamOption.id }}
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
<script lang="ts" src="./match-team-update.component.ts"></script>
