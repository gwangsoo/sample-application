<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="matchFormatGame">
        <h2 class="jh-entity-heading" data-cy="matchFormatGameDetailsHeading">
          <span v-text="t$('tossApp.matchFormatGame.detail.title')"></span> {{ matchFormatGame.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.matchFormatGame.gameCategoryType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.GameCategoryType.' + matchFormatGame.gameCategoryType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormatGame.gameType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.GameType.' + matchFormatGame.gameType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormatGame.roundNum')"></span>
          </dt>
          <dd>
            <span>{{ matchFormatGame.roundNum }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormatGame.machineCreditType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.MachineCreditType.' + matchFormatGame.machineCreditType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormatGame.includingChoiceGame')"></span>
          </dt>
          <dd>
            <span>{{ matchFormatGame.includingChoiceGame }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormatGame.game')"></span>
          </dt>
          <dd>
            <div v-if="matchFormatGame.game">
              <router-link :to="{ name: 'GameView', params: { gameId: matchFormatGame.game.id } }">{{
                matchFormatGame.game.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormatGame.matchFormat')"></span>
          </dt>
          <dd>
            <div v-if="matchFormatGame.matchFormat">
              <router-link :to="{ name: 'MatchFormatView', params: { matchFormatId: matchFormatGame.matchFormat.id } }">{{
                matchFormatGame.matchFormat.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="matchFormatGame.id"
          :to="{ name: 'MatchFormatGameEdit', params: { matchFormatGameId: matchFormatGame.id } }"
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

<script lang="ts" src="./match-format-game-details.component.ts"></script>
