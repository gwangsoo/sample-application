<template>
  <div>
    <h2 id="page-heading" data-cy="MatchFormatOptionHeading">
      <span v-text="t$('tossApp.matchFormatOption.home.title')" id="match-format-option-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.matchFormatOption.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'MatchFormatOptionCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-match-format-option"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.matchFormatOption.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && matchFormatOptions && matchFormatOptions.length === 0">
      <span v-text="t$('tossApp.matchFormatOption.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="matchFormatOptions && matchFormatOptions.length > 0">
      <table class="table table-striped" aria-describedby="matchFormatOptions">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatOption.game01InOptionType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatOption.game01OutOptionType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatOption.game01BullOptionType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatOption.game01Arrange')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatOption.cricketOverKill')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatOption.cricketScore')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatOption.teamGame01FreezeView')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatOption.teamGame01Point')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatOption.teamCricketMark')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatOption.teamCricketFinish')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatOption.teamCricketPoint')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.matchFormatOption.matchFormat')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="matchFormatOption in matchFormatOptions" :key="matchFormatOption.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MatchFormatOptionView', params: { matchFormatOptionId: matchFormatOption.id } }">{{
                matchFormatOption.id
              }}</router-link>
            </td>
            <td v-text="t$('tossApp.MatchFormatInOptionType.' + matchFormatOption.game01InOptionType)"></td>
            <td v-text="t$('tossApp.MatchFormatOutOptionType.' + matchFormatOption.game01OutOptionType)"></td>
            <td v-text="t$('tossApp.MatchFormatBullOptionType.' + matchFormatOption.game01BullOptionType)"></td>
            <td>{{ matchFormatOption.game01Arrange }}</td>
            <td>{{ matchFormatOption.cricketOverKill }}</td>
            <td>{{ matchFormatOption.cricketScore }}</td>
            <td v-text="t$('tossApp.MatchFormatFreezeOptionType.' + matchFormatOption.teamGame01FreezeView)"></td>
            <td>{{ matchFormatOption.teamGame01Point }}</td>
            <td>{{ matchFormatOption.teamCricketMark }}</td>
            <td v-text="t$('tossApp.MatchFormatTeamFinishOptionType.' + matchFormatOption.teamCricketFinish)"></td>
            <td>{{ matchFormatOption.teamCricketPoint }}</td>
            <td>
              <div v-if="matchFormatOption.matchFormat">
                <router-link :to="{ name: 'MatchFormatView', params: { matchFormatId: matchFormatOption.matchFormat.id } }">{{
                  matchFormatOption.matchFormat.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'MatchFormatOptionView', params: { matchFormatOptionId: matchFormatOption.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'MatchFormatOptionEdit', params: { matchFormatOptionId: matchFormatOption.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(matchFormatOption)"
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
        <span
          id="tossApp.matchFormatOption.delete.question"
          data-cy="matchFormatOptionDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-matchFormatOption-heading" v-text="t$('tossApp.matchFormatOption.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-matchFormatOption"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeMatchFormatOption()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./match-format-option.component.ts"></script>
