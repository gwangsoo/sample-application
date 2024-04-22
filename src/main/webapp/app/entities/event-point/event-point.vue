<template>
  <div>
    <h2 id="page-heading" data-cy="EventPointHeading">
      <span v-text="t$('tossApp.eventPoint.home.title')" id="event-point-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.eventPoint.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'EventPointCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-event-point"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.eventPoint.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && eventPoints && eventPoints.length === 0">
      <span v-text="t$('tossApp.eventPoint.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="eventPoints && eventPoints.length > 0">
      <table class="table table-striped" aria-describedby="eventPoints">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.eventPoint.seq')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.eventPoint.rating')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.eventPoint.ratingMin')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.eventPoint.ratingMax')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.eventPoint.division')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="eventPoint in eventPoints" :key="eventPoint.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EventPointView', params: { eventPointId: eventPoint.id } }">{{ eventPoint.id }}</router-link>
            </td>
            <td>{{ eventPoint.seq }}</td>
            <td>{{ eventPoint.rating }}</td>
            <td>{{ eventPoint.ratingMin }}</td>
            <td>{{ eventPoint.ratingMax }}</td>
            <td>
              <div v-if="eventPoint.division">
                <router-link :to="{ name: 'DivisionView', params: { divisionId: eventPoint.division.id } }">{{
                  eventPoint.division.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'EventPointView', params: { eventPointId: eventPoint.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'EventPointEdit', params: { eventPointId: eventPoint.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(eventPoint)"
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
        <span id="tossApp.eventPoint.delete.question" data-cy="eventPointDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-eventPoint-heading" v-text="t$('tossApp.eventPoint.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-eventPoint"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeEventPoint()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./event-point.component.ts"></script>
