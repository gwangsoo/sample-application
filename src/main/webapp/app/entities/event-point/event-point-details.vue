<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="eventPoint">
        <h2 class="jh-entity-heading" data-cy="eventPointDetailsHeading">
          <span v-text="t$('tossApp.eventPoint.detail.title')"></span> {{ eventPoint.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.eventPoint.seq')"></span>
          </dt>
          <dd>
            <span>{{ eventPoint.seq }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.eventPoint.rating')"></span>
          </dt>
          <dd>
            <span>{{ eventPoint.rating }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.eventPoint.ratingMin')"></span>
          </dt>
          <dd>
            <span>{{ eventPoint.ratingMin }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.eventPoint.ratingMax')"></span>
          </dt>
          <dd>
            <span>{{ eventPoint.ratingMax }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.eventPoint.division')"></span>
          </dt>
          <dd>
            <div v-if="eventPoint.division">
              <router-link :to="{ name: 'DivisionView', params: { divisionId: eventPoint.division.id } }">{{
                eventPoint.division.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="eventPoint.id"
          :to="{ name: 'EventPointEdit', params: { eventPointId: eventPoint.id } }"
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

<script lang="ts" src="./event-point-details.component.ts"></script>
