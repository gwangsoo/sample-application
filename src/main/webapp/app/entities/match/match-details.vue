<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="match">
        <h2 class="jh-entity-heading" data-cy="matchDetailsHeading">
          <span v-text="t$('tossApp.match.detail.title')"></span> {{ match.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.match.matchNo')"></span>
          </dt>
          <dd>
            <span>{{ match.matchNo }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.match.matchType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.MatchType.' + match.matchType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.match.groupNo')"></span>
          </dt>
          <dd>
            <span>{{ match.groupNo }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.match.groupMatchSeq')"></span>
          </dt>
          <dd>
            <span>{{ match.groupMatchSeq }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.match.roundNum')"></span>
          </dt>
          <dd>
            <span>{{ match.roundNum }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.match.matchStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.MatchStatus.' + match.matchStatus)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.match.nextMatchNo')"></span>
          </dt>
          <dd>
            <span>{{ match.nextMatchNo }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.match.home')"></span>
          </dt>
          <dd>
            <div v-if="match.home">
              <router-link :to="{ name: 'MatchTeamView', params: { matchTeamId: match.home.id } }">{{ match.home.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('tossApp.match.away')"></span>
          </dt>
          <dd>
            <div v-if="match.away">
              <router-link :to="{ name: 'MatchTeamView', params: { matchTeamId: match.away.id } }">{{ match.away.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('tossApp.match.tmatch')"></span>
          </dt>
          <dd>
            <div v-if="match.tmatch">
              <router-link :to="{ name: 'MachineView', params: { machineId: match.tmatch.id } }">{{ match.tmatch.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('tossApp.match.division')"></span>
          </dt>
          <dd>
            <div v-if="match.division">
              <router-link :to="{ name: 'DivisionView', params: { divisionId: match.division.id } }">{{ match.division.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="match.id" :to="{ name: 'MatchEdit', params: { matchId: match.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./match-details.component.ts"></script>
