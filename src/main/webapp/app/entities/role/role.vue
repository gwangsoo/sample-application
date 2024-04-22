<template>
  <div>
    <h2 id="page-heading" data-cy="RoleHeading">
      <span v-text="t$('tossApp.role.home.title')" id="role-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.role.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'RoleCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-role">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.role.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && roles && roles.length === 0">
      <span v-text="t$('tossApp.role.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="roles && roles.length > 0">
      <table class="table table-striped" aria-describedby="roles">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.role.name')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.role.authScopeType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.role.authLevelType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.role.displayOrder')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.role.operatorRole')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="role in roles" :key="role.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RoleView', params: { roleId: role.id } }">{{ role.id }}</router-link>
            </td>
            <td>{{ role.name }}</td>
            <td v-text="t$('tossApp.AuthScopeType.' + role.authScopeType)"></td>
            <td v-text="t$('tossApp.AuthLevelType.' + role.authLevelType)"></td>
            <td>{{ role.displayOrder }}</td>
            <td>
              <div v-if="role.operatorRole">
                <router-link :to="{ name: 'OperatorRoleView', params: { operatorRoleId: role.operatorRole.id } }">{{
                  role.operatorRole.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'RoleView', params: { roleId: role.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'RoleEdit', params: { roleId: role.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(role)"
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
        <span id="tossApp.role.delete.question" data-cy="roleDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-role-heading" v-text="t$('tossApp.role.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-role"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeRole()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./role.component.ts"></script>
