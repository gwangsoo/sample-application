<template>
  <div>
    <h2 id="page-heading" data-cy="MatchCallHeading">
      <span v-text="t$('tossApp.matchCall.home.title')" id="match-call-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.matchCall.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'MatchCallCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-match-call"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.matchCall.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && matchCalls && matchCalls.length === 0">
      <span v-text="t$('tossApp.matchCall.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="matchCalls && matchCalls.length > 0">
      <table class="table table-striped" aria-describedby="matchCalls">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchCall.callDateTime')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchCall.callMessage')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchCall.matchTeam')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="matchCall in matchCalls" :key="matchCall.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MatchCallView', params: { matchCallId: matchCall.id } }">{{ matchCall.id }}</router-link>
            </td>
            <td>{{ formatDateShort(matchCall.callDateTime) || '' }}</td>
            <td>{{ matchCall.callMessage }}</td>
            <td>
              <div v-if="matchCall.matchTeam">
                <router-link :to="{ name: 'MatchTeamView', params: { matchTeamId: matchCall.matchTeam.id } }">{{
                  matchCall.matchTeam.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'MatchCallView', params: { matchCallId: matchCall.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'MatchCallEdit', params: { matchCallId: matchCall.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(matchCall)"
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
        <span id="tossApp.matchCall.delete.question" data-cy="matchCallDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-matchCall-heading" v-text="t$('tossApp.matchCall.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-matchCall"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeMatchCall()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./match-call.component.ts"></script>
