<template>
  <div>
    <h2 id="page-heading" data-cy="MatchScoreHeading">
      <span v-text="t$('tossApp.matchScore.home.title')" id="match-score-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.matchScore.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'MatchScoreCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-match-score"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.matchScore.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && matchScores && matchScores.length === 0">
      <span v-text="t$('tossApp.matchScore.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="matchScores && matchScores.length > 0">
      <table class="table table-striped" aria-describedby="matchScores">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchScore.setNo')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchScore.lgeNo')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchScore.legGameName')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchScore.homeLegScore')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchScore.awayLegScore')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchScore.match')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="matchScore in matchScores" :key="matchScore.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MatchScoreView', params: { matchScoreId: matchScore.id } }">{{ matchScore.id }}</router-link>
            </td>
            <td>{{ matchScore.setNo }}</td>
            <td>{{ matchScore.lgeNo }}</td>
            <td>{{ matchScore.legGameName }}</td>
            <td>{{ matchScore.homeLegScore }}</td>
            <td>{{ matchScore.awayLegScore }}</td>
            <td>
              <div v-if="matchScore.match">
                <router-link :to="{ name: 'MatchView', params: { matchId: matchScore.match.id } }">{{ matchScore.match.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'MatchScoreView', params: { matchScoreId: matchScore.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'MatchScoreEdit', params: { matchScoreId: matchScore.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(matchScore)"
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
        <span id="tossApp.matchScore.delete.question" data-cy="matchScoreDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-matchScore-heading" v-text="t$('tossApp.matchScore.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-matchScore"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeMatchScore()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./match-score.component.ts"></script>
