<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.rewardItem.home.createOrEditLabel"
          data-cy="RewardItemCreateUpdateHeading"
          v-text="t$('tossApp.rewardItem.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="rewardItem.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="rewardItem.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.rewardItem.name')" for="reward-item-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="reward-item-name"
              data-cy="name"
              :class="{ valid: !v$.name.$invalid, invalid: v$.name.$invalid }"
              v-model="v$.name.$model"
              required
            />
            <div v-if="v$.name.$anyDirty && v$.name.$invalid">
              <small class="form-text text-danger" v-for="error of v$.name.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.rewardItem.itemImage')" for="reward-item-itemImage"></label>
            <select class="form-control" id="reward-item-itemImage" data-cy="itemImage" name="itemImage" v-model="rewardItem.itemImage">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="rewardItem.itemImage && fileInfoOption.id === rewardItem.itemImage.id ? rewardItem.itemImage : fileInfoOption"
                v-for="fileInfoOption in fileInfos"
                :key="fileInfoOption.id"
              >
                {{ fileInfoOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.rewardItem.rewardDetail')" for="reward-item-rewardDetail"></label>
            <select
              class="form-control"
              id="reward-item-rewardDetail"
              data-cy="rewardDetail"
              name="rewardDetail"
              v-model="rewardItem.rewardDetail"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  rewardItem.rewardDetail && rewardDetailOption.id === rewardItem.rewardDetail.id
                    ? rewardItem.rewardDetail
                    : rewardDetailOption
                "
                v-for="rewardDetailOption in rewardDetails"
                :key="rewardDetailOption.id"
              >
                {{ rewardDetailOption.id }}
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
<script lang="ts" src="./reward-item-update.component.ts"></script>
