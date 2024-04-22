<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="game">
        <h2 class="jh-entity-heading" data-cy="gameDetailsHeading"><span v-text="t$('tossApp.game.detail.title')"></span> {{ game.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.game.gameCategoryType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.GameCategoryType.' + game.gameCategoryType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.game.name')"></span>
          </dt>
          <dd>
            <span>{{ game.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.game.description')"></span>
          </dt>
          <dd>
            <span>{{ game.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.game.roundNumDefault')"></span>
          </dt>
          <dd>
            <span>{{ game.roundNumDefault }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.game.roundNumMin')"></span>
          </dt>
          <dd>
            <span>{{ game.roundNumMin }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.game.roundNumMax')"></span>
          </dt>
          <dd>
            <span>{{ game.roundNumMax }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.game.roundNumUnlimit')"></span>
          </dt>
          <dd>
            <span>{{ game.roundNumUnlimit }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="game.id" :to="{ name: 'GameEdit', params: { gameId: game.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./game-details.component.ts"></script>
