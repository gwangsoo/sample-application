<template>
  <div>
    <h2 id="page-heading" data-cy="DivisionHeading">
      <span v-text="t$('tossApp.division.home.title')" id="division-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.division.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'DivisionCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-division"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.division.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && divisions && divisions.length === 0">
      <span v-text="t$('tossApp.division.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="divisions && divisions.length > 0">
      <table class="table table-striped" aria-describedby="divisions">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.division.seq')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.division.name')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.division.ratingRuleTeamMin')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.division.ratingRuleTeamMax')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.division.ratingRuleIndividualLimit')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.division.ratingRuleIndividualMin')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.division.ratingRuleIndividualMax')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.division.entryLimit')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.division.roundRobinRankingDecisionType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.division.roundRobinGroupType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.division.nextRoundDecisionType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.division.roundRoginThirdDecision')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.division.thirdDecisionRankingRule')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.division.useAllRoundSameFormat')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.division.eventRangeType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.division.eliminationTeamCount')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.division.tournament')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="division in divisions" :key="division.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DivisionView', params: { divisionId: division.id } }">{{ division.id }}</router-link>
            </td>
            <td>{{ division.seq }}</td>
            <td>{{ division.name }}</td>
            <td>{{ division.ratingRuleTeamMin }}</td>
            <td>{{ division.ratingRuleTeamMax }}</td>
            <td>{{ division.ratingRuleIndividualLimit }}</td>
            <td>{{ division.ratingRuleIndividualMin }}</td>
            <td>{{ division.ratingRuleIndividualMax }}</td>
            <td>{{ division.entryLimit }}</td>
            <td v-text="t$('tossApp.RoundRobinRankingDecisionType.' + division.roundRobinRankingDecisionType)"></td>
            <td v-text="t$('tossApp.RoundRobinGroupType.' + division.roundRobinGroupType)"></td>
            <td v-text="t$('tossApp.NextRoundDecisionType.' + division.nextRoundDecisionType)"></td>
            <td>{{ division.roundRoginThirdDecision }}</td>
            <td v-text="t$('tossApp.ThirdDecisionRankingRule.' + division.thirdDecisionRankingRule)"></td>
            <td>{{ division.useAllRoundSameFormat }}</td>
            <td v-text="t$('tossApp.EventRangeType.' + division.eventRangeType)"></td>
            <td>{{ division.eliminationTeamCount }}</td>
            <td>
              <div v-if="division.tournament">
                <router-link :to="{ name: 'TournamentView', params: { tournamentId: division.tournament.id } }">{{
                  division.tournament.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'DivisionView', params: { divisionId: division.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'DivisionEdit', params: { divisionId: division.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(division)"
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
        <span id="tossApp.division.delete.question" data-cy="divisionDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-division-heading" v-text="t$('tossApp.division.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-division"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeDivision()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./division.component.ts"></script>
