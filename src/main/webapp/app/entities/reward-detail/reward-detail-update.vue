<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.rewardDetail.home.createOrEditLabel"
          data-cy="RewardDetailCreateUpdateHeading"
          v-text="t$('tossApp.rewardDetail.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="rewardDetail.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="rewardDetail.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.rewardDetail.name')" for="reward-detail-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="reward-detail-name"
              data-cy="name"
              :class="{ valid: !v$.name.$invalid, invalid: v$.name.$invalid }"
              v-model="v$.name.$model"
            />
            <div v-if="v$.name.$anyDirty && v$.name.$invalid">
              <small class="form-text text-danger" v-for="error of v$.name.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.rewardDetail.tournamentId')" for="reward-detail-tournamentId"></label>
            <input
              type="text"
              class="form-control"
              name="tournamentId"
              id="reward-detail-tournamentId"
              data-cy="tournamentId"
              :class="{ valid: !v$.tournamentId.$invalid, invalid: v$.tournamentId.$invalid }"
              v-model="v$.tournamentId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.rewardDetail.divisionId')" for="reward-detail-divisionId"></label>
            <input
              type="text"
              class="form-control"
              name="divisionId"
              id="reward-detail-divisionId"
              data-cy="divisionId"
              :class="{ valid: !v$.divisionId.$invalid, invalid: v$.divisionId.$invalid }"
              v-model="v$.divisionId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.rewardDetail.reward')" for="reward-detail-reward"></label>
            <select class="form-control" id="reward-detail-reward" data-cy="reward" name="reward" v-model="rewardDetail.reward">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="rewardDetail.reward && rewardOption.id === rewardDetail.reward.id ? rewardDetail.reward : rewardOption"
                v-for="rewardOption in rewards"
                :key="rewardOption.id"
              >
                {{ rewardOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./reward-detail-update.component.ts"></script>
