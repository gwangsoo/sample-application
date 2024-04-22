<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.match.home.createOrEditLabel"
          data-cy="MatchCreateUpdateHeading"
          v-text="t$('tossApp.match.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="match.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="match.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.match.matchNo')" for="match-matchNo"></label>
            <input
              type="text"
              class="form-control"
              name="matchNo"
              id="match-matchNo"
              data-cy="matchNo"
              :class="{ valid: !v$.matchNo.$invalid, invalid: v$.matchNo.$invalid }"
              v-model="v$.matchNo.$model"
              required
            />
            <div v-if="v$.matchNo.$anyDirty && v$.matchNo.$invalid">
              <small class="form-text text-danger" v-for="error of v$.matchNo.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.match.matchType')" for="match-matchType"></label>
            <select
              class="form-control"
              name="matchType"
              :class="{ valid: !v$.matchType.$invalid, invalid: v$.matchType.$invalid }"
              v-model="v$.matchType.$model"
              id="match-matchType"
              data-cy="matchType"
              required
            >
              <option
                v-for="matchType in matchTypeValues"
                :key="matchType"
                v-bind:value="matchType"
                v-bind:label="t$('tossApp.MatchType.' + matchType)"
              >
                {{ matchType }}
              </option>
            </select>
            <div v-if="v$.matchType.$anyDirty && v$.matchType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.matchType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.match.groupNo')" for="match-groupNo"></label>
            <input
              type="number"
              class="form-control"
              name="groupNo"
              id="match-groupNo"
              data-cy="groupNo"
              :class="{ valid: !v$.groupNo.$invalid, invalid: v$.groupNo.$invalid }"
              v-model.number="v$.groupNo.$model"
            />
            <div v-if="v$.groupNo.$anyDirty && v$.groupNo.$invalid">
              <small class="form-text text-danger" v-for="error of v$.groupNo.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.match.groupMatchSeq')" for="match-groupMatchSeq"></label>
            <input
              type="number"
              class="form-control"
              name="groupMatchSeq"
              id="match-groupMatchSeq"
              data-cy="groupMatchSeq"
              :class="{ valid: !v$.groupMatchSeq.$invalid, invalid: v$.groupMatchSeq.$invalid }"
              v-model.number="v$.groupMatchSeq.$model"
            />
            <div v-if="v$.groupMatchSeq.$anyDirty && v$.groupMatchSeq.$invalid">
              <small class="form-text text-danger" v-for="error of v$.groupMatchSeq.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.match.roundNum')" for="match-roundNum"></label>
            <input
              type="number"
              class="form-control"
              name="roundNum"
              id="match-roundNum"
              data-cy="roundNum"
              :class="{ valid: !v$.roundNum.$invalid, invalid: v$.roundNum.$invalid }"
              v-model.number="v$.roundNum.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.match.matchStatus')" for="match-matchStatus"></label>
            <select
              class="form-control"
              name="matchStatus"
              :class="{ valid: !v$.matchStatus.$invalid, invalid: v$.matchStatus.$invalid }"
              v-model="v$.matchStatus.$model"
              id="match-matchStatus"
              data-cy="matchStatus"
              required
            >
              <option
                v-for="matchStatus in matchStatusValues"
                :key="matchStatus"
                v-bind:value="matchStatus"
                v-bind:label="t$('tossApp.MatchStatus.' + matchStatus)"
              >
                {{ matchStatus }}
              </option>
            </select>
            <div v-if="v$.matchStatus.$anyDirty && v$.matchStatus.$invalid">
              <small class="form-text text-danger" v-for="error of v$.matchStatus.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.match.nextMatchNo')" for="match-nextMatchNo"></label>
            <input
              type="text"
              class="form-control"
              name="nextMatchNo"
              id="match-nextMatchNo"
              data-cy="nextMatchNo"
              :class="{ valid: !v$.nextMatchNo.$invalid, invalid: v$.nextMatchNo.$invalid }"
              v-model="v$.nextMatchNo.$model"
            />
            <div v-if="v$.nextMatchNo.$anyDirty && v$.nextMatchNo.$invalid">
              <small class="form-text text-danger" v-for="error of v$.nextMatchNo.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.match.home')" for="match-home"></label>
            <select class="form-control" id="match-home" data-cy="home" name="home" v-model="match.home">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="match.home && matchTeamOption.id === match.home.id ? match.home : matchTeamOption"
                v-for="matchTeamOption in matchTeams"
                :key="matchTeamOption.id"
              >
                {{ matchTeamOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.match.away')" for="match-away"></label>
            <select class="form-control" id="match-away" data-cy="away" name="away" v-model="match.away">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="match.away && matchTeamOption.id === match.away.id ? match.away : matchTeamOption"
                v-for="matchTeamOption in matchTeams"
                :key="matchTeamOption.id"
              >
                {{ matchTeamOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.match.tmatch')" for="match-tmatch"></label>
            <select class="form-control" id="match-tmatch" data-cy="tmatch" name="tmatch" v-model="match.tmatch">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="match.tmatch && machineOption.id === match.tmatch.id ? match.tmatch : machineOption"
                v-for="machineOption in machines"
                :key="machineOption.id"
              >
                {{ machineOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.match.division')" for="match-division"></label>
            <select class="form-control" id="match-division" data-cy="division" name="division" v-model="match.division">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="match.division && divisionOption.id === match.division.id ? match.division : divisionOption"
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
<script lang="ts" src="./match-update.component.ts"></script>
