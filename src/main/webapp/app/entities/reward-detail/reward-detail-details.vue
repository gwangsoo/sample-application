<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="rewardDetail">
        <h2 class="jh-entity-heading" data-cy="rewardDetailDetailsHeading">
          <span v-text="t$('tossApp.rewardDetail.detail.title')"></span> {{ rewardDetail.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.rewardDetail.name')"></span>
          </dt>
          <dd>
            <span>{{ rewardDetail.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.rewardDetail.tournamentId')"></span>
          </dt>
          <dd>
            <span>{{ rewardDetail.tournamentId }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.rewardDetail.divisionId')"></span>
          </dt>
          <dd>
            <span>{{ rewardDetail.divisionId }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.rewardDetail.reward')"></span>
          </dt>
          <dd>
            <div v-if="rewardDetail.reward">
              <router-link :to="{ name: 'RewardView', params: { rewardId: rewardDetail.reward.id } }">{{
                rewardDetail.reward.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="rewardDetail.id"
          :to="{ name: 'RewardDetailEdit', params: { rewardDetailId: rewardDetail.id } }"
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

<script lang="ts" src="./reward-detail-details.component.ts"></script>
