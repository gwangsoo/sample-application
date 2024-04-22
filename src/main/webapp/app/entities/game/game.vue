<template>
  <div>
    <h2 id="page-heading" data-cy="GameHeading">
      <span v-text="t$('tossApp.game.home.title')" id="game-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.game.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'GameCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-game">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.game.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && games && games.length === 0">
      <span v-text="t$('tossApp.game.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="games && games.length > 0">
      <table class="table table-striped" aria-describedby="games">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.game.gameCategoryType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.game.name')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.game.description')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.game.roundNumDefault')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.game.roundNumMin')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.game.roundNumMax')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.game.roundNumUnlimit')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="game in games" :key="game.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'GameView', params: { gameId: game.id } }">{{ game.id }}</router-link>
            </td>
            <td v-text="t$('tossApp.GameCategoryType.' + game.gameCategoryType)"></td>
            <td>{{ game.name }}</td>
            <td>{{ game.description }}</td>
            <td>{{ game.roundNumDefault }}</td>
            <td>{{ game.roundNumMin }}</td>
            <td>{{ game.roundNumMax }}</td>
            <td>{{ game.roundNumUnlimit }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'GameView', params: { gameId: game.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'GameEdit', params: { gameId: game.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(game)"
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
        <span id="tossApp.game.delete.question" data-cy="gameDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-game-heading" v-text="t$('tossApp.game.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-game"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeGame()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./game.component.ts"></script>
