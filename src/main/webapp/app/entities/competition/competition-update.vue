<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" auth="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.competition.home.createOrEditLabel"
          data-cy="CompetitionCreateUpdateHeading"
          v-text="t$('tossApp.competition.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="competition.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="competition.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.competition.name')" for="competition-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="competition-name"
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
            <label class="form-control-label" v-text="t$('tossApp.competition.startDateTime')" for="competition-startDateTime"></label>
            <div class="d-flex">
              <input
                id="competition-startDateTime"
                data-cy="startDateTime"
                type="datetime-local"
                class="form-control"
                name="startDateTime"
                :class="{ valid: !v$.startDateTime.$invalid, invalid: v$.startDateTime.$invalid }"
                required
                :value="convertDateTimeFromServer(v$.startDateTime.$model)"
                @change="updateInstantField('startDateTime', $event)"
              />
            </div>
            <div v-if="v$.startDateTime.$anyDirty && v$.startDateTime.$invalid">
              <small class="form-text text-danger" v-for="error of v$.startDateTime.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.competition.endDateTime')" for="competition-endDateTime"></label>
            <div class="d-flex">
              <input
                id="competition-endDateTime"
                data-cy="endDateTime"
                type="datetime-local"
                class="form-control"
                name="endDateTime"
                :class="{ valid: !v$.endDateTime.$invalid, invalid: v$.endDateTime.$invalid }"
                required
                :value="convertDateTimeFromServer(v$.endDateTime.$model)"
                @change="updateInstantField('endDateTime', $event)"
              />
            </div>
            <div v-if="v$.endDateTime.$anyDirty && v$.endDateTime.$invalid">
              <small class="form-text text-danger" v-for="error of v$.endDateTime.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.competition.entryStartDateTime')"
              for="competition-entryStartDateTime"
            ></label>
            <div class="d-flex">
              <input
                id="competition-entryStartDateTime"
                data-cy="entryStartDateTime"
                type="datetime-local"
                class="form-control"
                name="entryStartDateTime"
                :class="{ valid: !v$.entryStartDateTime.$invalid, invalid: v$.entryStartDateTime.$invalid }"
                required
                :value="convertDateTimeFromServer(v$.entryStartDateTime.$model)"
                @change="updateInstantField('entryStartDateTime', $event)"
              />
            </div>
            <div v-if="v$.entryStartDateTime.$anyDirty && v$.entryStartDateTime.$invalid">
              <small class="form-text text-danger" v-for="error of v$.entryStartDateTime.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.competition.entryEndDateTime')"
              for="competition-entryEndDateTime"
            ></label>
            <div class="d-flex">
              <input
                id="competition-entryEndDateTime"
                data-cy="entryEndDateTime"
                type="datetime-local"
                class="form-control"
                name="entryEndDateTime"
                :class="{ valid: !v$.entryEndDateTime.$invalid, invalid: v$.entryEndDateTime.$invalid }"
                required
                :value="convertDateTimeFromServer(v$.entryEndDateTime.$model)"
                @change="updateInstantField('entryEndDateTime', $event)"
              />
            </div>
            <div v-if="v$.entryEndDateTime.$anyDirty && v$.entryEndDateTime.$invalid">
              <small class="form-text text-danger" v-for="error of v$.entryEndDateTime.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.competition.status')" for="competition-status"></label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !v$.status.$invalid, invalid: v$.status.$invalid }"
              v-model="v$.status.$model"
              id="competition-status"
              data-cy="status"
              required
            >
              <option
                v-for="competitionStatus in competitionStatusValues"
                :key="competitionStatus"
                v-bind:value="competitionStatus"
                v-bind:label="t$('tossApp.CompetitionStatus.' + competitionStatus)"
              >
                {{ competitionStatus }}
              </option>
            </select>
            <div v-if="v$.status.$anyDirty && v$.status.$invalid">
              <small class="form-text text-danger" v-for="error of v$.status.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.competition.approval')" for="competition-approval"></label>
            <input
              type="checkbox"
              class="form-check"
              name="approval"
              id="competition-approval"
              data-cy="approval"
              :class="{ valid: !v$.approval.$invalid, invalid: v$.approval.$invalid }"
              v-model="v$.approval.$model"
              required
            />
            <div v-if="v$.approval.$anyDirty && v$.approval.$invalid">
              <small class="form-text text-danger" v-for="error of v$.approval.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.competition.entryApplyType')" for="competition-entryApplyType"></label>
            <select
              class="form-control"
              name="entryApplyType"
              :class="{ valid: !v$.entryApplyType.$invalid, invalid: v$.entryApplyType.$invalid }"
              v-model="v$.entryApplyType.$model"
              id="competition-entryApplyType"
              data-cy="entryApplyType"
              required
            >
              <option
                v-for="entryApplyType in entryApplyTypeValues"
                :key="entryApplyType"
                v-bind:value="entryApplyType"
                v-bind:label="t$('tossApp.EntryApplyType.' + entryApplyType)"
              >
                {{ entryApplyType }}
              </option>
            </select>
            <div v-if="v$.entryApplyType.$anyDirty && v$.entryApplyType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.entryApplyType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.competition.entryRatingType')" for="competition-entryRatingType"></label>
            <select
              class="form-control"
              name="entryRatingType"
              :class="{ valid: !v$.entryRatingType.$invalid, invalid: v$.entryRatingType.$invalid }"
              v-model="v$.entryRatingType.$model"
              id="competition-entryRatingType"
              data-cy="entryRatingType"
              required
            >
              <option
                v-for="entryRatingType in entryRatingTypeValues"
                :key="entryRatingType"
                v-bind:value="entryRatingType"
                v-bind:label="t$('tossApp.EntryRatingType.' + entryRatingType)"
              >
                {{ entryRatingType }}
              </option>
            </select>
            <div v-if="v$.entryRatingType.$anyDirty && v$.entryRatingType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.entryRatingType.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.competition.competitionImage')"
              for="competition-competitionImage"
            ></label>
            <select
              class="form-control"
              id="competition-competitionImage"
              data-cy="competitionImage"
              name="competitionImage"
              v-model="competition.competitionImage"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  competition.competitionImage && fileInfoOption.id === competition.competitionImage.id
                    ? competition.competitionImage
                    : fileInfoOption
                "
                v-for="fileInfoOption in fileInfos"
                :key="fileInfoOption.id"
              >
                {{ fileInfoOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.competition.reward')" for="competition-reward"></label>
            <select class="form-control" id="competition-reward" data-cy="reward" name="reward" v-model="competition.reward">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="competition.reward && rewardOption.id === competition.reward.id ? competition.reward : rewardOption"
                v-for="rewardOption in rewards"
                :key="rewardOption.id"
              >
                {{ rewardOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.competition.country')" for="competition-country"></label>
            <select class="form-control" id="competition-country" data-cy="country" name="country" v-model="competition.country">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="competition.country && countryOption.id === competition.country.id ? competition.country : countryOption"
                v-for="countryOption in countries"
                :key="countryOption.id"
              >
                {{ countryOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.competition.operator')" for="competition-operator"></label>
            <select class="form-control" id="competition-operator" data-cy="operator" name="operator" v-model="competition.operator">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="competition.operator && operatorOption.id === competition.operator.id ? competition.operator : operatorOption"
                v-for="operatorOption in operators"
                :key="operatorOption.id"
              >
                {{ operatorOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.competition.entryFee')" for="competition-entryFee"></label>
            <select class="form-control" id="competition-entryFee" data-cy="entryFee" name="entryFee" v-model="competition.entryFee">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="competition.entryFee && entryFeeOption.id === competition.entryFee.id ? competition.entryFee : entryFeeOption"
                v-for="entryFeeOption in entryFees"
                :key="entryFeeOption.id"
              >
                {{ entryFeeOption.id }}
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
<script lang="ts" src="./competition-update.component.ts"></script>
