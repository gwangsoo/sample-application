<template>
  <div>
    <h2 id="page-heading" data-cy="MatchFormatSetHeading">
      <span v-text="t$('tossApp.matchFormatSet.home.title')" id="match-format-set-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.matchFormatSet.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'MatchFormatSetCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-match-format-set"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.matchFormatSet.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && matchFormatSets && matchFormatSets.length === 0">
      <span v-text="t$('tossApp.matchFormatSet.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="matchFormatSets && matchFormatSets.length > 0">
      <table class="table table-striped" aria-describedby="matchFormatSets">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatSet.point')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatSet.matchFormat')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="matchFormatSet in matchFormatSets" :key="matchFormatSet.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MatchFormatSetView', params: { matchFormatSetId: matchFormatSet.id } }">{{
                matchFormatSet.id
              }}</router-link>
            </td>
            <td>{{ matchFormatSet.point }}</td>
            <td>
              <div v-if="matchFormatSet.matchFormat">
                <router-link :to="{ name: 'MatchFormatView', params: { matchFormatId: matchFormatSet.matchFormat.id } }">{{
                  matchFormatSet.matchFormat.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'MatchFormatSetView', params: { matchFormatSetId: matchFormatSet.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'MatchFormatSetEdit', params: { matchFormatSetId: matchFormatSet.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(matchFormatSet)"
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
          id="tossApp.matchFormatSet.delete.question"
          data-cy="matchFormatSetDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-matchFormatSet-heading" v-text="t$('tossApp.matchFormatSet.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-matchFormatSet"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeMatchFormatSet()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./match-format-set.component.ts"></script>
