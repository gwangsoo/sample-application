<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.reward.home.createOrEditLabel"
          data-cy="RewardCreateUpdateHeading"
          v-text="t$('tossApp.reward.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="reward.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="reward.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.reward.rewardMethodType')" for="reward-rewardMethodType"></label>
            <select
              class="form-control"
              name="rewardMethodType"
              :class="{ valid: !v$.rewardMethodType.$invalid, invalid: v$.rewardMethodType.$invalid }"
              v-model="v$.rewardMethodType.$model"
              id="reward-rewardMethodType"
              data-cy="rewardMethodType"
              required
            >
              <option
                v-for="rewardMethodType in rewardMethodTypeValues"
                :key="rewardMethodType"
                v-bind:value="rewardMethodType"
                v-bind:label="t$('tossApp.RewardMethodType.' + rewardMethodType)"
              >
                {{ rewardMethodType }}
              </option>
            </select>
            <div v-if="v$.rewardMethodType.$anyDirty && v$.rewardMethodType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.rewardMethodType.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.reward.rewardMethodSubType')" for="reward-rewardMethodSubType"></label>
            <select
              class="form-control"
              name="rewardMethodSubType"
              :class="{ valid: !v$.rewardMethodSubType.$invalid, invalid: v$.rewardMethodSubType.$invalid }"
              v-model="v$.rewardMethodSubType.$model"
              id="reward-rewardMethodSubType"
              data-cy="rewardMethodSubType"
              required
            >
              <option
                v-for="rewardMethodSubType in rewardMethodSubTypeValues"
                :key="rewardMethodSubType"
                v-bind:value="rewardMethodSubType"
                v-bind:label="t$('tossApp.RewardMethodSubType.' + rewardMethodSubType)"
              >
                {{ rewardMethodSubType }}
              </option>
            </select>
            <div v-if="v$.rewardMethodSubType.$anyDirty && v$.rewardMethodSubType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.rewardMethodSubType.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.reward.rewardCategoryNum')" for="reward-rewardCategoryNum"></label>
            <input
              type="number"
              class="form-control"
              name="rewardCategoryNum"
              id="reward-rewardCategoryNum"
              data-cy="rewardCategoryNum"
              :class="{ valid: !v$.rewardCategoryNum.$invalid, invalid: v$.rewardCategoryNum.$invalid }"
              v-model.number="v$.rewardCategoryNum.$model"
              required
            />
            <div v-if="v$.rewardCategoryNum.$anyDirty && v$.rewardCategoryNum.$invalid">
              <small class="form-text text-danger" v-for="error of v$.rewardCategoryNum.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.reward.machineKindType')" for="reward-machineKindType"></label>
            <select
              class="form-control"
              name="machineKindType"
              :class="{ valid: !v$.machineKindType.$invalid, invalid: v$.machineKindType.$invalid }"
              v-model="v$.machineKindType.$model"
              id="reward-machineKindType"
              data-cy="machineKindType"
            >
              <option
                v-for="machineKindType in machineKindTypeValues"
                :key="machineKindType"
                v-bind:value="machineKindType"
                v-bind:label="t$('tossApp.MachineKindType.' + machineKindType)"
              >
                {{ machineKindType }}
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
<script lang="ts" src="./reward-update.component.ts"></script>
