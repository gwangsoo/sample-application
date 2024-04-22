<template>
  <div>
    <h2 id="page-heading" data-cy="MatchAttendanceHeading">
      <span v-text="t$('tossApp.matchAttendance.home.title')" id="match-attendance-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.matchAttendance.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'MatchAttendanceCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-match-attendance"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.matchAttendance.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && matchAttendances && matchAttendances.length === 0">
      <span v-text="t$('tossApp.matchAttendance.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="matchAttendances && matchAttendances.length > 0">
      <table class="table table-striped" aria-describedby="matchAttendances">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchAttendance.attendanceStatusType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchAttendance.attendanceDateTime')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchAttendance.entry')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchAttendance.matchTeam')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="matchAttendance in matchAttendances" :key="matchAttendance.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MatchAttendanceView', params: { matchAttendanceId: matchAttendance.id } }">{{
                matchAttendance.id
              }}</router-link>
            </td>
            <td v-text="t$('tossApp.AttendanceStatusType.' + matchAttendance.attendanceStatusType)"></td>
            <td>{{ formatDateShort(matchAttendance.attendanceDateTime) || '' }}</td>
            <td>
              <div v-if="matchAttendance.entry">
                <router-link :to="{ name: 'EntryView', params: { entryId: matchAttendance.entry.id } }">{{
                  matchAttendance.entry.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="matchAttendance.matchTeam">
                <router-link :to="{ name: 'MatchTeamView', params: { matchTeamId: matchAttendance.matchTeam.id } }">{{
                  matchAttendance.matchTeam.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'MatchAttendanceView', params: { matchAttendanceId: matchAttendance.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'MatchAttendanceEdit', params: { matchAttendanceId: matchAttendance.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(matchAttendance)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span
          id="tossApp.matchAttendance.delete.question"
          data-cy="matchAttendanceDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-matchAttendance-heading" v-text="t$('tossApp.matchAttendance.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-matchAttendance"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeMatchAttendance()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./match-attendance.component.ts"></script>
