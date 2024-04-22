<template>
  <div>
    <h2 id="page-heading" data-cy="MatchFormatHeading">
      <span v-text="t$('tossApp.matchFormat.home.title')" id="match-format-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.matchFormat.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'MatchFormatCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-match-format"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.matchFormat.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && matchFormats && matchFormats.length === 0">
      <span v-text="t$('tossApp.matchFormat.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="matchFormats && matchFormats.length > 0">
      <table class="table table-striped" aria-describedby="matchFormats">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormat.name')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormat.description')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormat.matchFormatType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormat.firstSet')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormat.middleSet')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormat.lastSet')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormat.division')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="matchFormat in matchFormats" :key="matchFormat.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MatchFormatView', params: { matchFormatId: matchFormat.id } }">{{ matchFormat.id }}</router-link>
            </td>
            <td>{{ matchFormat.name }}</td>
            <td>{{ matchFormat.description }}</td>
            <td v-text="t$('tossApp.MatchFormatType.' + matchFormat.matchFormatType)"></td>
            <td v-text="t$('tossApp.FirstThrowType.' + matchFormat.firstSet)"></td>
            <td v-text="t$('tossApp.FirstThrowType.' + matchFormat.middleSet)"></td>
            <td v-text="t$('tossApp.FirstThrowType.' + matchFormat.lastSet)"></td>
            <td>
              <div v-if="matchFormat.division">
                <router-link :to="{ name: 'DivisionView', params: { divisionId: matchFormat.division.id } }">{{
                  matchFormat.division.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'MatchFormatView', params: { matchFormatId: matchFormat.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'MatchFormatEdit', params: { matchFormatId: matchFormat.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(matchFormat)"
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
        <span id="tossApp.matchFormat.delete.question" data-cy="matchFormatDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-matchFormat-heading" v-text="t$('tossApp.matchFormat.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-matchFormat"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeMatchFormat()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./match-format.component.ts"></script>
