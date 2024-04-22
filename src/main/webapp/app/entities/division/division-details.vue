<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="division">
        <h2 class="jh-entity-heading" data-cy="divisionDetailsHeading">
          <span v-text="t$('tossApp.division.detail.title')"></span> {{ division.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.division.seq')"></span>
          </dt>
          <dd>
            <span>{{ division.seq }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.division.name')"></span>
          </dt>
          <dd>
            <span>{{ division.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.division.ratingRuleTeamMin')"></span>
          </dt>
          <dd>
            <span>{{ division.ratingRuleTeamMin }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.division.ratingRuleTeamMax')"></span>
          </dt>
          <dd>
            <span>{{ division.ratingRuleTeamMax }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.division.ratingRuleIndividualLimit')"></span>
          </dt>
          <dd>
            <span>{{ division.ratingRuleIndividualLimit }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.division.ratingRuleIndividualMin')"></span>
          </dt>
          <dd>
            <span>{{ division.ratingRuleIndividualMin }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.division.ratingRuleIndividualMax')"></span>
          </dt>
          <dd>
            <span>{{ division.ratingRuleIndividualMax }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.division.entryLimit')"></span>
          </dt>
          <dd>
            <span>{{ division.entryLimit }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.division.roundRobinRankingDecisionType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.RoundRobinRankingDecisionType.' + division.roundRobinRankingDecisionType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.division.roundRobinGroupType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.RoundRobinGroupType.' + division.roundRobinGroupType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.division.nextRoundDecisionType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.NextRoundDecisionType.' + division.nextRoundDecisionType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.division.roundRoginThirdDecision')"></span>
          </dt>
          <dd>
            <span>{{ division.roundRoginThirdDecision }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.division.thirdDecisionRankingRule')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.ThirdDecisionRankingRule.' + division.thirdDecisionRankingRule)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.division.useAllRoundSameFormat')"></span>
          </dt>
          <dd>
            <span>{{ division.useAllRoundSameFormat }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.division.eventRangeType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.EventRangeType.' + division.eventRangeType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.division.eliminationTeamCount')"></span>
          </dt>
          <dd>
            <span>{{ division.eliminationTeamCount }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.division.tournament')"></span>
          </dt>
          <dd>
            <div v-if="division.tournament">
              <router-link :to="{ name: 'TournamentView', params: { tournamentId: division.tournament.id } }">{{
                division.tournament.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="division.id" :to="{ name: 'DivisionEdit', params: { divisionId: division.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./division-details.component.ts"></script>
