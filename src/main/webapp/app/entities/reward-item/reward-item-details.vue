<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="rewardItem">
        <h2 class="jh-entity-heading" data-cy="rewardItemDetailsHeading">
          <span v-text="t$('tossApp.rewardItem.detail.title')"></span> {{ rewardItem.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.rewardItem.name')"></span>
          </dt>
          <dd>
            <span>{{ rewardItem.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.rewardItem.itemImage')"></span>
          </dt>
          <dd>
            <div v-if="rewardItem.itemImage">
              <router-link :to="{ name: 'FileInfoView', params: { fileInfoId: rewardItem.itemImage.id } }">{{
                rewardItem.itemImage.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('tossApp.rewardItem.rewardDetail')"></span>
          </dt>
          <dd>
            <div v-if="rewardItem.rewardDetail">
              <router-link :to="{ name: 'RewardDetailView', params: { rewardDetailId: rewardItem.rewardDetail.id } }">{{
                rewardItem.rewardDetail.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="rewardItem.id"
          :to="{ name: 'RewardItemEdit', params: { rewardItemId: rewardItem.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./reward-item-details.component.ts"></script>
