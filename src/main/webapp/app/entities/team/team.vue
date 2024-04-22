<template>
  <div>
    <h2 id="page-heading" data-cy="TeamHeading">
      <span v-text="t$('tossApp.team.home.title')" id="team-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.team.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'TeamCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-team">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.team.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && teams && teams.length === 0">
      <span v-text="t$('tossApp.team.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="teams && teams.length > 0">
      <table class="table table-striped" aria-describedby="teams">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.team.teamNo')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.team.approvalStatus')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.team.entryDate')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.team.memo')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.team.groupNo')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.team.groupSeq')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.team.seedNo')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.team.captain')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.team.tteam')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.team.affiliatedInfo')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.team.paymentInfo')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.team.division')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="team in teams" :key="team.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TeamView', params: { teamId: team.id } }">{{ team.id }}</router-link>
            </td>
            <td>{{ team.teamNo }}</td>
            <td v-text="t$('tossApp.EntryApprovalStatusType.' + team.approvalStatus)"></td>
            <td>{{ formatDateShort(team.entryDate) || '' }}</td>
            <td>{{ team.memo }}</td>
            <td>{{ team.groupNo }}</td>
            <td>{{ team.groupSeq }}</td>
            <td>{{ team.seedNo }}</td>
            <td>
              <div v-if="team.captain">
                <router-link :to="{ name: 'EntryView', params: { entryId: team.captain.id } }">{{ team.captain.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="team.tteam">
                <router-link :to="{ name: 'DivisionView', params: { divisionId: team.tteam.id } }">{{ team.tteam.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="team.affiliatedInfo">
                <router-link :to="{ name: 'AffiliatedInfoView', params: { affiliatedInfoId: team.affiliatedInfo.id } }">{{
                  team.affiliatedInfo.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="team.paymentInfo">
                <router-link :to="{ name: 'PaymentInfoView', params: { paymentInfoId: team.paymentInfo.id } }">{{
                  team.paymentInfo.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="team.division">
                <router-link :to="{ name: 'DivisionView', params: { divisionId: team.division.id } }">{{ team.division.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'TeamView', params: { teamId: team.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'TeamEdit', params: { teamId: team.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(team)"
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
        <span id="tossApp.team.delete.question" data-cy="teamDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-team-heading" v-text="t$('tossApp.team.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-team"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeTeam()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./team.component.ts"></script>
