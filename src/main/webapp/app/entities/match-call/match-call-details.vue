<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="matchCall">
        <h2 class="jh-entity-heading" data-cy="matchCallDetailsHeading">
          <span v-text="t$('tossApp.matchCall.detail.title')"></span> {{ matchCall.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.matchCall.callDateTime')"></span>
          </dt>
          <dd>
            <span v-if="matchCall.callDateTime">{{ formatDateLong(matchCall.callDateTime) }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchCall.callMessage')"></span>
          </dt>
          <dd>
            <span>{{ matchCall.callMessage }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchCall.matchTeam')"></span>
          </dt>
          <dd>
            <div v-if="matchCall.matchTeam">
              <router-link :to="{ name: 'MatchTeamView', params: { matchTeamId: matchCall.matchTeam.id } }">{{
                matchCall.matchTeam.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="matchCall.id"
          :to="{ name: 'MatchCallEdit', params: { matchCallId: matchCall.id } }"
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

<script lang="ts" src="./match-call-details.component.ts"></script>
