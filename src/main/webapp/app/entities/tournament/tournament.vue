<template>
  <div>
    <h2 id="page-heading" data-cy="TournamentHeading">
      <span v-text="t$('tossApp.tournament.home.title')" id="tournament-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.tournament.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'TournamentCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-tournament"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.tournament.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && tournaments && tournaments.length === 0">
      <span v-text="t$('tossApp.tournament.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="tournaments && tournaments.length > 0">
      <table class="table table-striped" aria-describedby="tournaments">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.tournament.seq')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.tournament.name')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.tournament.day')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.tournament.eventTournament')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.tournament.entryClose')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.tournament.entryApprovalType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.tournament.tournamentType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.tournament.seedingRule')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.tournament.tournamentPlayMode')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.tournament.thirdPlaceDecision')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.tournament.divisionRule')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.tournament.entryLimit')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.tournament.numOfEntry')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.tournament.divisionAssignMethod')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.tournament.entryGenderType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.tournament.handicap')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.tournament.entryFee')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.tournament.competition')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="tournament in tournaments" :key="tournament.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TournamentView', params: { tournamentId: tournament.id } }">{{ tournament.id }}</router-link>
            </td>
            <td>{{ tournament.seq }}</td>
            <td>{{ tournament.name }}</td>
            <td>{{ tournament.day }}</td>
            <td>{{ tournament.eventTournament }}</td>
            <td>{{ tournament.entryClose }}</td>
            <td v-text="t$('tossApp.EntryApprovalType.' + tournament.entryApprovalType)"></td>
            <td v-text="t$('tossApp.TournamentType.' + tournament.tournamentType)"></td>
            <td v-text="t$('tossApp.SeedingRuleType.' + tournament.seedingRule)"></td>
            <td v-text="t$('tossApp.TournamentPlayMode.' + tournament.tournamentPlayMode)"></td>
            <td>{{ tournament.thirdPlaceDecision }}</td>
            <td v-text="t$('tossApp.DivisionRuleType.' + tournament.divisionRule)"></td>
            <td>{{ tournament.entryLimit }}</td>
            <td>{{ tournament.numOfEntry }}</td>
            <td v-text="t$('tossApp.DivisionAssignMethod.' + tournament.divisionAssignMethod)"></td>
            <td v-text="t$('tossApp.EntryGenderType.' + tournament.entryGenderType)"></td>
            <td v-text="t$('tossApp.HandicapType.' + tournament.handicap)"></td>
            <td>
              <div v-if="tournament.entryFee">
                <router-link :to="{ name: 'EntryFeeView', params: { entryFeeId: tournament.entryFee.id } }">{{
                  tournament.entryFee.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="tournament.competition">
                <router-link :to="{ name: 'CompetitionView', params: { competitionId: tournament.competition.id } }">{{
                  tournament.competition.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'TournamentView', params: { tournamentId: tournament.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'TournamentEdit', params: { tournamentId: tournament.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(tournament)"
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
        <span id="tossApp.tournament.delete.question" data-cy="tournamentDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-tournament-heading" v-text="t$('tossApp.tournament.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-tournament"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeTournament()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./tournament.component.ts"></script>
