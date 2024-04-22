<template>
  <div>
    <h2 id="page-heading" data-cy="OperatorRoleHeading">
      <span v-text="t$('tossApp.operatorRole.home.title')" id="operator-role-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.operatorRole.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'OperatorRoleCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-operator-role"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.operatorRole.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && operatorRoles && operatorRoles.length === 0">
      <span v-text="t$('tossApp.operatorRole.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="operatorRoles && operatorRoles.length > 0">
      <table class="table table-striped" aria-describedby="operatorRoles">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.operatorRole.name')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.operatorRole.displayOrder')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="operatorRole in operatorRoles" :key="operatorRole.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'OperatorRoleView', params: { operatorRoleId: operatorRole.id } }">{{
                operatorRole.id
              }}</router-link>
            </td>
            <td>{{ operatorRole.name }}</td>
            <td>{{ operatorRole.displayOrder }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'OperatorRoleView', params: { operatorRoleId: operatorRole.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'OperatorRoleEdit', params: { operatorRoleId: operatorRole.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(operatorRole)"
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
        <span id="tossApp.operatorRole.delete.question" data-cy="operatorRoleDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-operatorRole-heading" v-text="t$('tossApp.operatorRole.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-operatorRole"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeOperatorRole()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./operator-role.component.ts"></script>
