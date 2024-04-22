<template>
  <div>
    <h2 id="page-heading" data-cy="MachineHeading">
      <span v-text="t$('tossApp.machine.home.title')" id="machine-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.machine.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'MachineCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-machine"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.machine.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && machines && machines.length === 0">
      <span v-text="t$('tossApp.machine.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="machines && machines.length > 0">
      <table class="table table-striped" aria-describedby="machines">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.machine.machineNo')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.machine.machineStatusType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.machine.match')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.machine.machineArea')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="machine in machines" :key="machine.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MachineView', params: { machineId: machine.id } }">{{ machine.id }}</router-link>
            </td>
            <td>{{ machine.machineNo }}</td>
            <td v-text="t$('tossApp.MachineStatusType.' + machine.machineStatusType)"></td>
            <td>
              <div v-if="machine.match">
                <router-link :to="{ name: 'MatchView', params: { matchId: machine.match.id } }">{{ machine.match.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="machine.machineArea">
                <router-link :to="{ name: 'MachineAreaView', params: { machineAreaId: machine.machineArea.id } }">{{
                  machine.machineArea.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'MachineView', params: { machineId: machine.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'MachineEdit', params: { machineId: machine.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(machine)"
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
        <span id="tossApp.machine.delete.question" data-cy="machineDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-machine-heading" v-text="t$('tossApp.machine.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-machine"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeMachine()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./machine.component.ts"></script>
