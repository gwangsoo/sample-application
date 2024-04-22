<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="team">
        <h2 class="jh-entity-heading" data-cy="teamDetailsHeading"><span v-text="t$('tossApp.team.detail.title')"></span> {{ team.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.team.teamNo')"></span>
          </dt>
          <dd>
            <span>{{ team.teamNo }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.team.approvalStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.EntryApprovalStatusType.' + team.approvalStatus)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.team.entryDate')"></span>
          </dt>
          <dd>
            <span v-if="team.entryDate">{{ formatDateLong(team.entryDate) }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.team.memo')"></span>
          </dt>
          <dd>
            <span>{{ team.memo }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.team.groupNo')"></span>
          </dt>
          <dd>
            <span>{{ team.groupNo }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.team.groupSeq')"></span>
          </dt>
          <dd>
            <span>{{ team.groupSeq }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.team.seedNo')"></span>
          </dt>
          <dd>
            <span>{{ team.seedNo }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.team.captain')"></span>
          </dt>
          <dd>
            <div v-if="team.captain">
              <router-link :to="{ name: 'EntryView', params: { entryId: team.captain.id } }">{{ team.captain.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('tossApp.team.tteam')"></span>
          </dt>
          <dd>
            <div v-if="team.tteam">
              <router-link :to="{ name: 'DivisionView', params: { divisionId: team.tteam.id } }">{{ team.tteam.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('tossApp.team.affiliatedInfo')"></span>
          </dt>
          <dd>
            <div v-if="team.affiliatedInfo">
              <router-link :to="{ name: 'AffiliatedInfoView', params: { affiliatedInfoId: team.affiliatedInfo.id } }">{{
                team.affiliatedInfo.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('tossApp.team.paymentInfo')"></span>
          </dt>
          <dd>
            <div v-if="team.paymentInfo">
              <router-link :to="{ name: 'PaymentInfoView', params: { paymentInfoId: team.paymentInfo.id } }">{{
                team.paymentInfo.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('tossApp.team.division')"></span>
          </dt>
          <dd>
            <div v-if="team.division">
              <router-link :to="{ name: 'DivisionView', params: { divisionId: team.division.id } }">{{ team.division.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="team.id" :to="{ name: 'TeamEdit', params: { teamId: team.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./team-details.component.ts"></script>
