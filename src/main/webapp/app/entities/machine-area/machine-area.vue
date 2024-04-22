<template>
  <div>
    <h2 id="page-heading" data-cy="MachineAreaHeading">
      <span v-text="t$('tossApp.machineArea.home.title')" id="machine-area-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.machineArea.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'MachineAreaCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-machine-area"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.machineArea.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && machineAreas && machineAreas.length === 0">
      <span v-text="t$('tossApp.machineArea.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="machineAreas && machineAreas.length > 0">
      <table class="table table-striped" aria-describedby="machineAreas">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.machineArea.mame')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.machineArea.seq')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.machineArea.numOfMachine')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.machineArea.competition')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="machineArea in machineAreas" :key="machineArea.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MachineAreaView', params: { machineAreaId: machineArea.id } }">{{ machineArea.id }}</router-link>
            </td>
            <td>{{ machineArea.mame }}</td>
            <td>{{ machineArea.seq }}</td>
            <td>{{ machineArea.numOfMachine }}</td>
            <td>
              <div v-if="machineArea.competition">
                <router-link :to="{ name: 'CompetitionView', params: { competitionId: machineArea.competition.id } }">{{
                  machineArea.competition.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'MachineAreaView', params: { machineAreaId: machineArea.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'MachineAreaEdit', params: { machineAreaId: machineArea.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(machineArea)"
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
        <span id="tossApp.machineArea.delete.question" data-cy="machineAreaDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-machineArea-heading" v-text="t$('tossApp.machineArea.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-machineArea"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeMachineArea()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./machine-area.component.ts"></script>
