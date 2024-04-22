<template>
  <div>
    <h2 id="page-heading" data-cy="MatchFormatGameHeading">
      <span v-text="t$('tossApp.matchFormatGame.home.title')" id="match-format-game-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.matchFormatGame.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'MatchFormatGameCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-match-format-game"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.matchFormatGame.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && matchFormatGames && matchFormatGames.length === 0">
      <span v-text="t$('tossApp.matchFormatGame.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="matchFormatGames && matchFormatGames.length > 0">
      <table class="table table-striped" aria-describedby="matchFormatGames">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatGame.gameCategoryType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatGame.gameType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatGame.roundNum')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatGame.machineCreditType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatGame.includingChoiceGame')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatGame.game')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatGame.matchFormat')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="matchFormatGame in matchFormatGames" :key="matchFormatGame.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MatchFormatGameView', params: { matchFormatGameId: matchFormatGame.id } }">{{
                matchFormatGame.id
              }}</router-link>
            </td>
            <td v-text="t$('tossApp.GameCategoryType.' + matchFormatGame.gameCategoryType)"></td>
            <td v-text="t$('tossApp.GameType.' + matchFormatGame.gameType)"></td>
            <td>{{ matchFormatGame.roundNum }}</td>
            <td v-text="t$('tossApp.MachineCreditType.' + matchFormatGame.machineCreditType)"></td>
            <td>{{ matchFormatGame.includingChoiceGame }}</td>
            <td>
              <div v-if="matchFormatGame.game">
                <router-link :to="{ name: 'GameView', params: { gameId: matchFormatGame.game.id } }">{{
                  matchFormatGame.game.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="matchFormatGame.matchFormat">
                <router-link :to="{ name: 'MatchFormatView', params: { matchFormatId: matchFormatGame.matchFormat.id } }">{{
                  matchFormatGame.matchFormat.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'MatchFormatGameView', params: { matchFormatGameId: matchFormatGame.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'MatchFormatGameEdit', params: { matchFormatGameId: matchFormatGame.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(matchFormatGame)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span
          id="tossApp.matchFormatGame.delete.question"
          data-cy="matchFormatGameDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-matchFormatGame-heading" v-text="t$('tossApp.matchFormatGame.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-matchFormatGame"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeMatchFormatGame()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./match-format-game.component.ts"></script>
