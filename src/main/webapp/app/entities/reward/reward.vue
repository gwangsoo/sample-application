<template>
  <div>
    <h2 id="page-heading" data-cy="RewardHeading">
      <span v-text="t$('tossApp.reward.home.title')" id="reward-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.reward.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'RewardCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-reward"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.reward.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && rewards && rewards.length === 0">
      <span v-text="t$('tossApp.reward.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="rewards && rewards.length > 0">
      <table class="table table-striped" aria-describedby="rewards">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.reward.rewardMethodType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.reward.rewardMethodSubType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.reward.rewardCategoryNum')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.reward.machineKindType')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="reward in rewards" :key="reward.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RewardView', params: { rewardId: reward.id } }">{{ reward.id }}</router-link>
            </td>
            <td v-text="t$('tossApp.RewardMethodType.' + reward.rewardMethodType)"></td>
            <td v-text="t$('tossApp.RewardMethodSubType.' + reward.rewardMethodSubType)"></td>
            <td>{{ reward.rewardCategoryNum }}</td>
            <td v-text="t$('tossApp.MachineKindType.' + reward.machineKindType)"></td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'RewardView', params: { rewardId: reward.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'RewardEdit', params: { rewardId: reward.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(reward)"
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
        <span id="tossApp.reward.delete.question" data-cy="rewardDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-reward-heading" v-text="t$('tossApp.reward.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-reward"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeReward()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./reward.component.ts"></script>
