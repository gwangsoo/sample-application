<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="matchFormat">
        <h2 class="jh-entity-heading" data-cy="matchFormatDetailsHeading">
          <span v-text="t$('tossApp.matchFormat.detail.title')"></span> {{ matchFormat.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.matchFormat.name')"></span>
          </dt>
          <dd>
            <span>{{ matchFormat.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormat.description')"></span>
          </dt>
          <dd>
            <span>{{ matchFormat.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormat.matchFormatType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.MatchFormatType.' + matchFormat.matchFormatType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormat.firstSet')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.FirstThrowType.' + matchFormat.firstSet)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormat.middleSet')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.FirstThrowType.' + matchFormat.middleSet)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormat.lastSet')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.FirstThrowType.' + matchFormat.lastSet)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormat.division')"></span>
          </dt>
          <dd>
            <div v-if="matchFormat.division">
              <router-link :to="{ name: 'DivisionView', params: { divisionId: matchFormat.division.id } }">{{
                matchFormat.division.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="matchFormat.id"
          :to="{ name: 'MatchFormatEdit', params: { matchFormatId: matchFormat.id } }"
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

<script lang="ts" src="./match-format-details.component.ts"></script>
