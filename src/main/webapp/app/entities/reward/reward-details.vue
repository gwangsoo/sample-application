<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="reward">
        <h2 class="jh-entity-heading" data-cy="rewardDetailsHeading">
          <span v-text="t$('tossApp.reward.detail.title')"></span> {{ reward.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.reward.rewardMethodType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.RewardMethodType.' + reward.rewardMethodType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.reward.rewardMethodSubType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.RewardMethodSubType.' + reward.rewardMethodSubType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.reward.rewardCategoryNum')"></span>
          </dt>
          <dd>
            <span>{{ reward.rewardCategoryNum }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.reward.machineKindType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.MachineKindType.' + reward.machineKindType)"></span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="reward.id" :to="{ name: 'RewardEdit', params: { rewardId: reward.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./reward-details.component.ts"></script>
