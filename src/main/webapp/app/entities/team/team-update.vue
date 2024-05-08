<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" auth="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.team.home.createOrEditLabel"
          data-cy="TeamCreateUpdateHeading"
          v-text="t$('tossApp.team.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="team.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="team.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.team.teamNo')" for="team-teamNo"></label>
            <input
              type="text"
              class="form-control"
              name="teamNo"
              id="team-teamNo"
              data-cy="teamNo"
              :class="{ valid: !v$.teamNo.$invalid, invalid: v$.teamNo.$invalid }"
              v-model="v$.teamNo.$model"
            />
            <div v-if="v$.teamNo.$anyDirty && v$.teamNo.$invalid">
              <small class="form-text text-danger" v-for="error of v$.teamNo.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.team.approvalStatus')" for="team-approvalStatus"></label>
            <select
              class="form-control"
              name="approvalStatus"
              :class="{ valid: !v$.approvalStatus.$invalid, invalid: v$.approvalStatus.$invalid }"
              v-model="v$.approvalStatus.$model"
              id="team-approvalStatus"
              data-cy="approvalStatus"
              required
            >
              <option
                v-for="entryApprovalStatusType in entryApprovalStatusTypeValues"
                :key="entryApprovalStatusType"
                v-bind:value="entryApprovalStatusType"
                v-bind:label="t$('tossApp.EntryApprovalStatusType.' + entryApprovalStatusType)"
              >
                {{ entryApprovalStatusType }}
              </option>
            </select>
            <div v-if="v$.approvalStatus.$anyDirty && v$.approvalStatus.$invalid">
              <small class="form-text text-danger" v-for="error of v$.approvalStatus.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.team.entryDate')" for="team-entryDate"></label>
            <div class="d-flex">
              <input
                id="team-entryDate"
                data-cy="entryDate"
                type="datetime-local"
                class="form-control"
                name="entryDate"
                :class="{ valid: !v$.entryDate.$invalid, invalid: v$.entryDate.$invalid }"
                :value="convertDateTimeFromServer(v$.entryDate.$model)"
                @change="updateZonedDateTimeField('entryDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.team.memo')" for="team-memo"></label>
            <input
              type="text"
              class="form-control"
              name="memo"
              id="team-memo"
              data-cy="memo"
              :class="{ valid: !v$.memo.$invalid, invalid: v$.memo.$invalid }"
              v-model="v$.memo.$model"
            />
            <div v-if="v$.memo.$anyDirty && v$.memo.$invalid">
              <small class="form-text text-danger" v-for="error of v$.memo.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.team.groupNo')" for="team-groupNo"></label>
            <input
              type="number"
              class="form-control"
              name="groupNo"
              id="team-groupNo"
              data-cy="groupNo"
              :class="{ valid: !v$.groupNo.$invalid, invalid: v$.groupNo.$invalid }"
              v-model.number="v$.groupNo.$model"
            />
            <div v-if="v$.groupNo.$anyDirty && v$.groupNo.$invalid">
              <small class="form-text text-danger" v-for="error of v$.groupNo.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.team.groupSeq')" for="team-groupSeq"></label>
            <input
              type="number"
              class="form-control"
              name="groupSeq"
              id="team-groupSeq"
              data-cy="groupSeq"
              :class="{ valid: !v$.groupSeq.$invalid, invalid: v$.groupSeq.$invalid }"
              v-model.number="v$.groupSeq.$model"
            />
            <div v-if="v$.groupSeq.$anyDirty && v$.groupSeq.$invalid">
              <small class="form-text text-danger" v-for="error of v$.groupSeq.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.team.seedNo')" for="team-seedNo"></label>
            <input
              type="number"
              class="form-control"
              name="seedNo"
              id="team-seedNo"
              data-cy="seedNo"
              :class="{ valid: !v$.seedNo.$invalid, invalid: v$.seedNo.$invalid }"
              v-model.number="v$.seedNo.$model"
            />
            <div v-if="v$.seedNo.$anyDirty && v$.seedNo.$invalid">
              <small class="form-text text-danger" v-for="error of v$.seedNo.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.team.captain')" for="team-captain"></label>
            <select class="form-control" id="team-captain" data-cy="captain" name="captain" v-model="team.captain">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="team.captain && entryOption.id === team.captain.id ? team.captain : entryOption"
                v-for="entryOption in entries"
                :key="entryOption.id"
              >
                {{ entryOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.team.tteam')" for="team-tteam"></label>
            <select class="form-control" id="team-tteam" data-cy="tteam" name="tteam" v-model="team.tteam">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="team.tteam && divisionOption.id === team.tteam.id ? team.tteam : divisionOption"
                v-for="divisionOption in divisions"
                :key="divisionOption.id"
              >
                {{ divisionOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.team.affiliatedInfo')" for="team-affiliatedInfo"></label>
            <select
              class="form-control"
              id="team-affiliatedInfo"
              data-cy="affiliatedInfo"
              name="affiliatedInfo"
              v-model="team.affiliatedInfo"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  team.affiliatedInfo && affiliatedInfoOption.id === team.affiliatedInfo.id ? team.affiliatedInfo : affiliatedInfoOption
                "
                v-for="affiliatedInfoOption in affiliatedInfos"
                :key="affiliatedInfoOption.id"
              >
                {{ affiliatedInfoOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.team.paymentInfo')" for="team-paymentInfo"></label>
            <select class="form-control" id="team-paymentInfo" data-cy="paymentInfo" name="paymentInfo" v-model="team.paymentInfo">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="team.paymentInfo && paymentInfoOption.id === team.paymentInfo.id ? team.paymentInfo : paymentInfoOption"
                v-for="paymentInfoOption in paymentInfos"
                :key="paymentInfoOption.id"
              >
                {{ paymentInfoOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.team.division')" for="team-division"></label>
            <select class="form-control" id="team-division" data-cy="division" name="division" v-model="team.division">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="team.division && divisionOption.id === team.division.id ? team.division : divisionOption"
                v-for="divisionOption in divisions"
                :key="divisionOption.id"
              >
                {{ divisionOption.id }}
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
<script lang="ts" src="./team-update.component.ts"></script>
