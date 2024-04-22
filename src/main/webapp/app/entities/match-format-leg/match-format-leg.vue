<template>
  <div>
    <h2 id="page-heading" data-cy="MatchFormatLegHeading">
      <span v-text="t$('tossApp.matchFormatLeg.home.title')" id="match-format-leg-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.matchFormatLeg.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'MatchFormatLegCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-match-format-leg"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.matchFormatLeg.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && matchFormatLegs && matchFormatLegs.length === 0">
      <span v-text="t$('tossApp.matchFormatLeg.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="matchFormatLegs && matchFormatLegs.length > 0">
      <table class="table table-striped" aria-describedby="matchFormatLegs">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatLeg.seq')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatLeg.firstThrowType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatLeg.playMode')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatLeg.option')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatLeg.matchFormatSet')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="matchFormatLeg in matchFormatLegs" :key="matchFormatLeg.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MatchFormatLegView', params: { matchFormatLegId: matchFormatLeg.id } }">{{
                matchFormatLeg.id
              }}</router-link>
            </td>
            <td>{{ matchFormatLeg.seq }}</td>
            <td v-text="t$('tossApp.FirstThrowType.' + matchFormatLeg.firstThrowType)"></td>
            <td v-text="t$('tossApp.LegPlayMode.' + matchFormatLeg.playMode)"></td>
            <td>
              <div v-if="matchFormatLeg.option">
                <router-link :to="{ name: 'MatchFormatOptionView', params: { matchFormatOptionId: matchFormatLeg.option.id } }">{{
                  matchFormatLeg.option.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="matchFormatLeg.matchFormatSet">
                <router-link :to="{ name: 'MatchFormatSetView', params: { matchFormatSetId: matchFormatLeg.matchFormatSet.id } }">{{
                  matchFormatLeg.matchFormatSet.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'MatchFormatLegView', params: { matchFormatLegId: matchFormatLeg.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'MatchFormatLegEdit', params: { matchFormatLegId: matchFormatLeg.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(matchFormatLeg)"
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
          id="tossApp.matchFormatLeg.delete.question"
          data-cy="matchFormatLegDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-matchFormatLeg-heading" v-text="t$('tossApp.matchFormatLeg.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-matchFormatLeg"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeMatchFormatLeg()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./match-format-leg.component.ts"></script>
