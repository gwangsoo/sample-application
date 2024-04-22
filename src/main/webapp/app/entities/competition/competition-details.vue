<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="competition">
        <h2 class="jh-entity-heading" data-cy="competitionDetailsHeading">
          <span v-text="t$('tossApp.competition.detail.title')"></span> {{ competition.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.competition.name')"></span>
          </dt>
          <dd>
            <span>{{ competition.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.competition.startDateTime')"></span>
          </dt>
          <dd>
            <span v-if="competition.startDateTime">{{ formatDateLong(competition.startDateTime) }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.competition.endDateTime')"></span>
          </dt>
          <dd>
            <span v-if="competition.endDateTime">{{ formatDateLong(competition.endDateTime) }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.competition.entryStartDateTime')"></span>
          </dt>
          <dd>
            <span v-if="competition.entryStartDateTime">{{ formatDateLong(competition.entryStartDateTime) }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.competition.entryEndDateTime')"></span>
          </dt>
          <dd>
            <span v-if="competition.entryEndDateTime">{{ formatDateLong(competition.entryEndDateTime) }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.competition.status')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.CompetitionStatus.' + competition.status)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.competition.approval')"></span>
          </dt>
          <dd>
            <span>{{ competition.approval }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.competition.entryApplyType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.EntryApplyType.' + competition.entryApplyType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.competition.entryRatingType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.EntryRatingType.' + competition.entryRatingType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.competition.competitionImage')"></span>
          </dt>
          <dd>
            <div v-if="competition.competitionImage">
              <router-link :to="{ name: 'FileInfoView', params: { fileInfoId: competition.competitionImage.id } }">{{
                competition.competitionImage.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('tossApp.competition.reward')"></span>
          </dt>
          <dd>
            <div v-if="competition.reward">
              <router-link :to="{ name: 'RewardView', params: { rewardId: competition.reward.id } }">{{
                competition.reward.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('tossApp.competition.country')"></span>
          </dt>
          <dd>
            <div v-if="competition.country">
              <router-link :to="{ name: 'CountryView', params: { countryId: competition.country.id } }">{{
                competition.country.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('tossApp.competition.operator')"></span>
          </dt>
          <dd>
            <div v-if="competition.operator">
              <router-link :to="{ name: 'OperatorView', params: { operatorId: competition.operator.id } }">{{
                competition.operator.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('tossApp.competition.entryFee')"></span>
          </dt>
          <dd>
            <div v-if="competition.entryFee">
              <router-link :to="{ name: 'EntryFeeView', params: { entryFeeId: competition.entryFee.id } }">{{
                competition.entryFee.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="competition.id"
          :to="{ name: 'CompetitionEdit', params: { competitionId: competition.id } }"
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

<script lang="ts" src="./competition-details.component.ts"></script>
