<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="matchScore">
        <h2 class="jh-entity-heading" data-cy="matchScoreDetailsHeading">
          <span v-text="t$('tossApp.matchScore.detail.title')"></span> {{ matchScore.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.matchScore.setNo')"></span>
          </dt>
          <dd>
            <span>{{ matchScore.setNo }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchScore.lgeNo')"></span>
          </dt>
          <dd>
            <span>{{ matchScore.lgeNo }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchScore.legGameName')"></span>
          </dt>
          <dd>
            <span>{{ matchScore.legGameName }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchScore.homeLegScore')"></span>
          </dt>
          <dd>
            <span>{{ matchScore.homeLegScore }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchScore.awayLegScore')"></span>
          </dt>
          <dd>
            <span>{{ matchScore.awayLegScore }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchScore.match')"></span>
          </dt>
          <dd>
            <div v-if="matchScore.match">
              <router-link :to="{ name: 'MatchView', params: { matchId: matchScore.match.id } }">{{ matchScore.match.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="matchScore.id"
          :to="{ name: 'MatchScoreEdit', params: { matchScoreId: matchScore.id } }"
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

<script lang="ts" src="./match-score-details.component.ts"></script>
