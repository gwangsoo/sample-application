<template>
  <div>
    <h2 id="page-heading" data-cy="OperatorHeading">
      <span v-text="t$('tossApp.operator.home.title')" id="operator-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.operator.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'OperatorCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-operator"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.operator.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && operators && operators.length === 0">
      <span v-text="t$('tossApp.operator.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="operators && operators.length > 0">
      <table class="table table-striped" aria-describedby="operators">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.operator.userId')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.operator.userName')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.operator.phone')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.operator.email')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.operator.address')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.operator.approvalStatus')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.operator.operatorRole')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.operator.region')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="operator in operators" :key="operator.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'OperatorView', params: { operatorId: operator.id } }">{{ operator.id }}</router-link>
            </td>
            <td>{{ operator.userId }}</td>
            <td>{{ operator.userName }}</td>
            <td>{{ operator.phone }}</td>
            <td>{{ operator.email }}</td>
            <td>{{ operator.address }}</td>
            <td>{{ operator.approvalStatus }}</td>
            <td>
              <div v-if="operator.operatorRole">
                <router-link :to="{ name: 'OperatorRoleView', params: { operatorRoleId: operator.operatorRole.id } }">{{
                  operator.operatorRole.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="operator.region">
                <router-link :to="{ name: 'RegionView', params: { regionId: operator.region.id } }">{{ operator.region.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'OperatorView', params: { operatorId: operator.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'OperatorEdit', params: { operatorId: operator.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(operator)"
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
        <span id="tossApp.operator.delete.question" data-cy="operatorDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-operator-heading" v-text="t$('tossApp.operator.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-operator"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeOperator()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./operator.component.ts"></script>
