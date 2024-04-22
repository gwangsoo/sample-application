<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="matchAttendance">
        <h2 class="jh-entity-heading" data-cy="matchAttendanceDetailsHeading">
          <span v-text="t$('tossApp.matchAttendance.detail.title')"></span> {{ matchAttendance.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.matchAttendance.attendanceStatusType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.AttendanceStatusType.' + matchAttendance.attendanceStatusType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchAttendance.attendanceDateTime')"></span>
          </dt>
          <dd>
            <span v-if="matchAttendance.attendanceDateTime">{{ formatDateLong(matchAttendance.attendanceDateTime) }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchAttendance.entry')"></span>
          </dt>
          <dd>
            <div v-if="matchAttendance.entry">
              <router-link :to="{ name: 'EntryView', params: { entryId: matchAttendance.entry.id } }">{{
                matchAttendance.entry.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchAttendance.matchTeam')"></span>
          </dt>
          <dd>
            <div v-if="matchAttendance.matchTeam">
              <router-link :to="{ name: 'MatchTeamView', params: { matchTeamId: matchAttendance.matchTeam.id } }">{{
                matchAttendance.matchTeam.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="matchAttendance.id"
          :to="{ name: 'MatchAttendanceEdit', params: { matchAttendanceId: matchAttendance.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./match-attendance-details.component.ts"></script>
