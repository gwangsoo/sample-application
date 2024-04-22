<template>
  <div>
    <h2 id="page-heading" data-cy="RewardItemHeading">
      <span v-text="t$('tossApp.rewardItem.home.title')" id="reward-item-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.rewardItem.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'RewardItemCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-reward-item"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.rewardItem.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && rewardItems && rewardItems.length === 0">
      <span v-text="t$('tossApp.rewardItem.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="rewardItems && rewardItems.length > 0">
      <table class="table table-striped" aria-describedby="rewardItems">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.rewardItem.name')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.rewardItem.itemImage')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.rewardItem.rewardDetail')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="rewardItem in rewardItems" :key="rewardItem.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RewardItemView', params: { rewardItemId: rewardItem.id } }">{{ rewardItem.id }}</router-link>
            </td>
            <td>{{ rewardItem.name }}</td>
            <td>
              <div v-if="rewardItem.itemImage">
                <router-link :to="{ name: 'FileInfoView', params: { fileInfoId: rewardItem.itemImage.id } }">{{
                  rewardItem.itemImage.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="rewardItem.rewardDetail">
                <router-link :to="{ name: 'RewardDetailView', params: { rewardDetailId: rewardItem.rewardDetail.id } }">{{
                  rewardItem.rewardDetail.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'RewardItemView', params: { rewardItemId: rewardItem.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'RewardItemEdit', params: { rewardItemId: rewardItem.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(rewardItem)"
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
        <span id="tossApp.rewardItem.delete.question" data-cy="rewardItemDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-rewardItem-heading" v-text="t$('tossApp.rewardItem.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-rewardItem"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeRewardItem()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./reward-item.component.ts"></script>
