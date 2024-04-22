<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="matchFormatSet">
        <h2 class="jh-entity-heading" data-cy="matchFormatSetDetailsHeading">
          <span v-text="t$('tossApp.matchFormatSet.detail.title')"></span> {{ matchFormatSet.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.matchFormatSet.point')"></span>
          </dt>
          <dd>
            <span>{{ matchFormatSet.point }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormatSet.matchFormat')"></span>
          </dt>
          <dd>
            <div v-if="matchFormatSet.matchFormat">
              <router-link :to="{ name: 'MatchFormatView', params: { matchFormatId: matchFormatSet.matchFormat.id } }">{{
                matchFormatSet.matchFormat.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="matchFormatSet.id"
          :to="{ name: 'MatchFormatSetEdit', params: { matchFormatSetId: matchFormatSet.id } }"
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

<script lang="ts" src="./match-format-set-details.component.ts"></script>
