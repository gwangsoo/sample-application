<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="matchTeam">
        <h2 class="jh-entity-heading" data-cy="matchTeamDetailsHeading">
          <span v-text="t$('tossApp.matchTeam.detail.title')"></span> {{ matchTeam.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.matchTeam.isWinner')"></span>
          </dt>
          <dd>
            <span>{{ matchTeam.isWinner }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchTeam.avgPpd')"></span>
          </dt>
          <dd>
            <span>{{ matchTeam.avgPpd }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchTeam.avgMpr')"></span>
          </dt>
          <dd>
            <span>{{ matchTeam.avgMpr }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchTeam.winSet')"></span>
          </dt>
          <dd>
            <span>{{ matchTeam.winSet }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchTeam.winLeg')"></span>
          </dt>
          <dd>
            <span>{{ matchTeam.winLeg }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchTeam.playerCallModeType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.PlayerCallModeType.' + matchTeam.playerCallModeType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchTeam.team')"></span>
          </dt>
          <dd>
            <div v-if="matchTeam.team">
              <router-link :to="{ name: 'TeamView', params: { teamId: matchTeam.team.id } }">{{ matchTeam.team.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="matchTeam.id"
          :to="{ name: 'MatchTeamEdit', params: { matchTeamId: matchTeam.id } }"
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

<script lang="ts" src="./match-team-details.component.ts"></script>
