<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" auth="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.matchAttendance.home.createOrEditLabel"
          data-cy="MatchAttendanceCreateUpdateHeading"
          v-text="t$('tossApp.matchAttendance.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="matchAttendance.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="matchAttendance.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.matchAttendance.attendanceStatusType')"
              for="match-attendance-attendanceStatusType"
            ></label>
            <select
              class="form-control"
              name="attendanceStatusType"
              :class="{ valid: !v$.attendanceStatusType.$invalid, invalid: v$.attendanceStatusType.$invalid }"
              v-model="v$.attendanceStatusType.$model"
              id="match-attendance-attendanceStatusType"
              data-cy="attendanceStatusType"
              required
            >
              <option
                v-for="attendanceStatusType in attendanceStatusTypeValues"
                :key="attendanceStatusType"
                v-bind:value="attendanceStatusType"
                v-bind:label="t$('tossApp.AttendanceStatusType.' + attendanceStatusType)"
              >
                {{ attendanceStatusType }}
              </option>
            </select>
            <div v-if="v$.attendanceStatusType.$anyDirty && v$.attendanceStatusType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.attendanceStatusType.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.matchAttendance.attendanceDateTime')"
              for="match-attendance-attendanceDateTime"
            ></label>
            <div class="d-flex">
              <input
                id="match-attendance-attendanceDateTime"
                data-cy="attendanceDateTime"
                type="datetime-local"
                class="form-control"
                name="attendanceDateTime"
                :class="{ valid: !v$.attendanceDateTime.$invalid, invalid: v$.attendanceDateTime.$invalid }"
                :value="convertDateTimeFromServer(v$.attendanceDateTime.$model)"
                @change="updateZonedDateTimeField('attendanceDateTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchAttendance.entry')" for="match-attendance-entry"></label>
            <select class="form-control" id="match-attendance-entry" data-cy="entry" name="entry" v-model="matchAttendance.entry">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="matchAttendance.entry && entryOption.id === matchAttendance.entry.id ? matchAttendance.entry : entryOption"
                v-for="entryOption in entries"
                :key="entryOption.id"
              >
                {{ entryOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchAttendance.matchTeam')" for="match-attendance-matchTeam"></label>
            <select
              class="form-control"
              id="match-attendance-matchTeam"
              data-cy="matchTeam"
              name="matchTeam"
              v-model="matchAttendance.matchTeam"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  matchAttendance.matchTeam && matchTeamOption.id === matchAttendance.matchTeam.id
                    ? matchAttendance.matchTeam
                    : matchTeamOption
                "
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
<script lang="ts" src="./match-attendance-update.component.ts"></script>
