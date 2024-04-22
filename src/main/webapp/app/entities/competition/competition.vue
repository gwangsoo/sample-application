<template>
  <div>
    <h2 id="page-heading" data-cy="CompetitionHeading">
      <span v-text="t$('tossApp.competition.home.title')" id="competition-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.competition.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'CompetitionCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-competition"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.competition.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && competitions && competitions.length === 0">
      <span v-text="t$('tossApp.competition.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="competitions && competitions.length > 0">
      <table class="table table-striped" aria-describedby="competitions">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.competition.name')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.competition.startDateTime')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.competition.endDateTime')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.competition.entryStartDateTime')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.competition.entryEndDateTime')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.competition.status')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.competition.approval')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.competition.entryApplyType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.competition.entryRatingType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.competition.competitionImage')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.competition.reward')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.competition.country')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.competition.operator')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.competition.entryFee')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="competition in competitions" :key="competition.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CompetitionView', params: { competitionId: competition.id } }">{{ competition.id }}</router-link>
            </td>
            <td>{{ competition.name }}</td>
            <td>{{ formatDateShort(competition.startDateTime) || '' }}</td>
            <td>{{ formatDateShort(competition.endDateTime) || '' }}</td>
            <td>{{ formatDateShort(competition.entryStartDateTime) || '' }}</td>
            <td>{{ formatDateShort(competition.entryEndDateTime) || '' }}</td>
            <td v-text="t$('tossApp.CompetitionStatus.' + competition.status)"></td>
            <td>{{ competition.approval }}</td>
            <td v-text="t$('tossApp.EntryApplyType.' + competition.entryApplyType)"></td>
            <td v-text="t$('tossApp.EntryRatingType.' + competition.entryRatingType)"></td>
            <td>
              <div v-if="competition.competitionImage">
                <router-link :to="{ name: 'FileInfoView', params: { fileInfoId: competition.competitionImage.id } }">{{
                  competition.competitionImage.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="competition.reward">
                <router-link :to="{ name: 'RewardView', params: { rewardId: competition.reward.id } }">{{
                  competition.reward.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="competition.country">
                <router-link :to="{ name: 'CountryView', params: { countryId: competition.country.id } }">{{
                  competition.country.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="competition.operator">
                <router-link :to="{ name: 'OperatorView', params: { operatorId: competition.operator.id } }">{{
                  competition.operator.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="competition.entryFee">
                <router-link :to="{ name: 'EntryFeeView', params: { entryFeeId: competition.entryFee.id } }">{{
                  competition.entryFee.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'CompetitionView', params: { competitionId: competition.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'CompetitionEdit', params: { competitionId: competition.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(competition)"
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
        <span id="tossApp.competition.delete.question" data-cy="competitionDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-competition-heading" v-text="t$('tossApp.competition.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-competition"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeCompetition()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./competition.component.ts"></script>
