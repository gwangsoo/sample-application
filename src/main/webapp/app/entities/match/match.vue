<template>
  <div>
    <h2 id="page-heading" data-cy="MatchHeading">
      <span v-text="t$('tossApp.match.home.title')" id="match-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.match.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'MatchCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-match"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.match.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && matches && matches.length === 0">
      <span v-text="t$('tossApp.match.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="matches && matches.length > 0">
      <table class="table table-striped" aria-describedby="matches">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.match.matchNo')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.match.matchType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.match.groupNo')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.match.groupMatchSeq')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.match.roundNum')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.match.matchStatus')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.match.nextMatchNo')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.match.home')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.match.away')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.match.tmatch')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.match.division')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="match in matches" :key="match.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MatchView', params: { matchId: match.id } }">{{ match.id }}</router-link>
            </td>
            <td>{{ match.matchNo }}</td>
            <td v-text="t$('tossApp.MatchType.' + match.matchType)"></td>
            <td>{{ match.groupNo }}</td>
            <td>{{ match.groupMatchSeq }}</td>
            <td>{{ match.roundNum }}</td>
            <td v-text="t$('tossApp.MatchStatus.' + match.matchStatus)"></td>
            <td>{{ match.nextMatchNo }}</td>
            <td>
              <div v-if="match.home">
                <router-link :to="{ name: 'MatchTeamView', params: { matchTeamId: match.home.id } }">{{ match.home.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="match.away">
                <router-link :to="{ name: 'MatchTeamView', params: { matchTeamId: match.away.id } }">{{ match.away.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="match.tmatch">
                <router-link :to="{ name: 'MachineView', params: { machineId: match.tmatch.id } }">{{ match.tmatch.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="match.division">
                <router-link :to="{ name: 'DivisionView', params: { divisionId: match.division.id } }">{{ match.division.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'MatchView', params: { matchId: match.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'MatchEdit', params: { matchId: match.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(match)"
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
        <span id="tossApp.match.delete.question" data-cy="matchDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-match-heading" v-text="t$('tossApp.match.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-match"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeMatch()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./match.component.ts"></script>
