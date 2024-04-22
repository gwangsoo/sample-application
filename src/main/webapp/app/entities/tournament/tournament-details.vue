<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="tournament">
        <h2 class="jh-entity-heading" data-cy="tournamentDetailsHeading">
          <span v-text="t$('tossApp.tournament.detail.title')"></span> {{ tournament.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.tournament.seq')"></span>
          </dt>
          <dd>
            <span>{{ tournament.seq }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.tournament.name')"></span>
          </dt>
          <dd>
            <span>{{ tournament.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.tournament.day')"></span>
          </dt>
          <dd>
            <span>{{ tournament.day }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.tournament.eventTournament')"></span>
          </dt>
          <dd>
            <span>{{ tournament.eventTournament }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.tournament.entryClose')"></span>
          </dt>
          <dd>
            <span>{{ tournament.entryClose }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.tournament.entryApprovalType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.EntryApprovalType.' + tournament.entryApprovalType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.tournament.tournamentType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.TournamentType.' + tournament.tournamentType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.tournament.seedingRule')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.SeedingRuleType.' + tournament.seedingRule)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.tournament.tournamentPlayMode')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.TournamentPlayMode.' + tournament.tournamentPlayMode)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.tournament.thirdPlaceDecision')"></span>
          </dt>
          <dd>
            <span>{{ tournament.thirdPlaceDecision }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.tournament.divisionRule')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.DivisionRuleType.' + tournament.divisionRule)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.tournament.entryLimit')"></span>
          </dt>
          <dd>
            <span>{{ tournament.entryLimit }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.tournament.numOfEntry')"></span>
          </dt>
          <dd>
            <span>{{ tournament.numOfEntry }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.tournament.divisionAssignMethod')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.DivisionAssignMethod.' + tournament.divisionAssignMethod)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.tournament.entryGenderType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.EntryGenderType.' + tournament.entryGenderType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.tournament.handicap')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.HandicapType.' + tournament.handicap)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.tournament.entryFee')"></span>
          </dt>
          <dd>
            <div v-if="tournament.entryFee">
              <router-link :to="{ name: 'EntryFeeView', params: { entryFeeId: tournament.entryFee.id } }">{{
                tournament.entryFee.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('tossApp.tournament.competition')"></span>
          </dt>
          <dd>
            <div v-if="tournament.competition">
              <router-link :to="{ name: 'CompetitionView', params: { competitionId: tournament.competition.id } }">{{
                tournament.competition.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="tournament.id"
          :to="{ name: 'TournamentEdit', params: { tournamentId: tournament.id } }"
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

<script lang="ts" src="./tournament-details.component.ts"></script>
