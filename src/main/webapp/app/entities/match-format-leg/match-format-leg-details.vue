<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="matchFormatLeg">
        <h2 class="jh-entity-heading" data-cy="matchFormatLegDetailsHeading">
          <span v-text="t$('tossApp.matchFormatLeg.detail.title')"></span> {{ matchFormatLeg.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.matchFormatLeg.seq')"></span>
          </dt>
          <dd>
            <span>{{ matchFormatLeg.seq }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormatLeg.firstThrowType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.FirstThrowType.' + matchFormatLeg.firstThrowType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormatLeg.playMode')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.LegPlayMode.' + matchFormatLeg.playMode)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormatLeg.option')"></span>
          </dt>
          <dd>
            <div v-if="matchFormatLeg.option">
              <router-link :to="{ name: 'MatchFormatOptionView', params: { matchFormatOptionId: matchFormatLeg.option.id } }">{{
                matchFormatLeg.option.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormatLeg.matchFormatSet')"></span>
          </dt>
          <dd>
            <div v-if="matchFormatLeg.matchFormatSet">
              <router-link :to="{ name: 'MatchFormatSetView', params: { matchFormatSetId: matchFormatLeg.matchFormatSet.id } }">{{
                matchFormatLeg.matchFormatSet.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="matchFormatLeg.id"
          :to="{ name: 'MatchFormatLegEdit', params: { matchFormatLegId: matchFormatLeg.id } }"
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

<script lang="ts" src="./match-format-leg-details.component.ts"></script>
