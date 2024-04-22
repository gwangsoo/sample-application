<template>
  <div>
    <h2 id="page-heading" data-cy="MatchTeamHeading">
      <span v-text="t$('tossApp.matchTeam.home.title')" id="match-team-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.matchTeam.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'MatchTeamCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-match-team"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.matchTeam.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && matchTeams && matchTeams.length === 0">
      <span v-text="t$('tossApp.matchTeam.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="matchTeams && matchTeams.length > 0">
      <table class="table table-striped" aria-describedby="matchTeams">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchTeam.isWinner')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchTeam.avgPpd')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchTeam.avgMpr')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchTeam.winSet')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchTeam.winLeg')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchTeam.playerCallModeType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchTeam.team')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="matchTeam in matchTeams" :key="matchTeam.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MatchTeamView', params: { matchTeamId: matchTeam.id } }">{{ matchTeam.id }}</router-link>
            </td>
            <td>{{ matchTeam.isWinner }}</td>
            <td>{{ matchTeam.avgPpd }}</td>
            <td>{{ matchTeam.avgMpr }}</td>
            <td>{{ matchTeam.winSet }}</td>
            <td>{{ matchTeam.winLeg }}</td>
            <td v-text="t$('tossApp.PlayerCallModeType.' + matchTeam.playerCallModeType)"></td>
            <td>
              <div v-if="matchTeam.team">
                <router-link :to="{ name: 'TeamView', params: { teamId: matchTeam.team.id } }">{{ matchTeam.team.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'MatchTeamView', params: { matchTeamId: matchTeam.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'MatchTeamEdit', params: { matchTeamId: matchTeam.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(matchTeam)"
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
        <span id="tossApp.matchTeam.delete.question" data-cy="matchTeamDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-matchTeam-heading" v-text="t$('tossApp.matchTeam.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-matchTeam"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeMatchTeam()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./match-team.component.ts"></script>
