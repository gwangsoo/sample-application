<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.tournament.home.createOrEditLabel"
          data-cy="TournamentCreateUpdateHeading"
          v-text="t$('tossApp.tournament.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="tournament.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="tournament.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.tournament.seq')" for="tournament-seq"></label>
            <input
              type="number"
              class="form-control"
              name="seq"
              id="tournament-seq"
              data-cy="seq"
              :class="{ valid: !v$.seq.$invalid, invalid: v$.seq.$invalid }"
              v-model.number="v$.seq.$model"
              required
            />
            <div v-if="v$.seq.$anyDirty && v$.seq.$invalid">
              <small class="form-text text-danger" v-for="error of v$.seq.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.tournament.name')" for="tournament-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="tournament-name"
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
            <label class="form-control-label" v-text="t$('tossApp.tournament.day')" for="tournament-day"></label>
            <input
              type="number"
              class="form-control"
              name="day"
              id="tournament-day"
              data-cy="day"
              :class="{ valid: !v$.day.$invalid, invalid: v$.day.$invalid }"
              v-model.number="v$.day.$model"
              required
            />
            <div v-if="v$.day.$anyDirty && v$.day.$invalid">
              <small class="form-text text-danger" v-for="error of v$.day.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.tournament.eventTournament')" for="tournament-eventTournament"></label>
            <input
              type="checkbox"
              class="form-check"
              name="eventTournament"
              id="tournament-eventTournament"
              data-cy="eventTournament"
              :class="{ valid: !v$.eventTournament.$invalid, invalid: v$.eventTournament.$invalid }"
              v-model="v$.eventTournament.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.tournament.entryClose')" for="tournament-entryClose"></label>
            <input
              type="checkbox"
              class="form-check"
              name="entryClose"
              id="tournament-entryClose"
              data-cy="entryClose"
              :class="{ valid: !v$.entryClose.$invalid, invalid: v$.entryClose.$invalid }"
              v-model="v$.entryClose.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.tournament.entryApprovalType')"
              for="tournament-entryApprovalType"
            ></label>
            <select
              class="form-control"
              name="entryApprovalType"
              :class="{ valid: !v$.entryApprovalType.$invalid, invalid: v$.entryApprovalType.$invalid }"
              v-model="v$.entryApprovalType.$model"
              id="tournament-entryApprovalType"
              data-cy="entryApprovalType"
              required
            >
              <option
                v-for="entryApprovalType in entryApprovalTypeValues"
                :key="entryApprovalType"
                v-bind:value="entryApprovalType"
                v-bind:label="t$('tossApp.EntryApprovalType.' + entryApprovalType)"
              >
                {{ entryApprovalType }}
              </option>
            </select>
            <div v-if="v$.entryApprovalType.$anyDirty && v$.entryApprovalType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.entryApprovalType.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.tournament.tournamentType')" for="tournament-tournamentType"></label>
            <select
              class="form-control"
              name="tournamentType"
              :class="{ valid: !v$.tournamentType.$invalid, invalid: v$.tournamentType.$invalid }"
              v-model="v$.tournamentType.$model"
              id="tournament-tournamentType"
              data-cy="tournamentType"
              required
            >
              <option
                v-for="tournamentType in tournamentTypeValues"
                :key="tournamentType"
                v-bind:value="tournamentType"
                v-bind:label="t$('tossApp.TournamentType.' + tournamentType)"
              >
                {{ tournamentType }}
              </option>
            </select>
            <div v-if="v$.tournamentType.$anyDirty && v$.tournamentType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.tournamentType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.tournament.seedingRule')" for="tournament-seedingRule"></label>
            <select
              class="form-control"
              name="seedingRule"
              :class="{ valid: !v$.seedingRule.$invalid, invalid: v$.seedingRule.$invalid }"
              v-model="v$.seedingRule.$model"
              id="tournament-seedingRule"
              data-cy="seedingRule"
              required
            >
              <option
                v-for="seedingRuleType in seedingRuleTypeValues"
                :key="seedingRuleType"
                v-bind:value="seedingRuleType"
                v-bind:label="t$('tossApp.SeedingRuleType.' + seedingRuleType)"
              >
                {{ seedingRuleType }}
              </option>
            </select>
            <div v-if="v$.seedingRule.$anyDirty && v$.seedingRule.$invalid">
              <small class="form-text text-danger" v-for="error of v$.seedingRule.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.tournament.tournamentPlayMode')"
              for="tournament-tournamentPlayMode"
            ></label>
            <select
              class="form-control"
              name="tournamentPlayMode"
              :class="{ valid: !v$.tournamentPlayMode.$invalid, invalid: v$.tournamentPlayMode.$invalid }"
              v-model="v$.tournamentPlayMode.$model"
              id="tournament-tournamentPlayMode"
              data-cy="tournamentPlayMode"
              required
            >
              <option
                v-for="tournamentPlayMode in tournamentPlayModeValues"
                :key="tournamentPlayMode"
                v-bind:value="tournamentPlayMode"
                v-bind:label="t$('tossApp.TournamentPlayMode.' + tournamentPlayMode)"
              >
                {{ tournamentPlayMode }}
              </option>
            </select>
            <div v-if="v$.tournamentPlayMode.$anyDirty && v$.tournamentPlayMode.$invalid">
              <small class="form-text text-danger" v-for="error of v$.tournamentPlayMode.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.tournament.thirdPlaceDecision')"
              for="tournament-thirdPlaceDecision"
            ></label>
            <input
              type="checkbox"
              class="form-check"
              name="thirdPlaceDecision"
              id="tournament-thirdPlaceDecision"
              data-cy="thirdPlaceDecision"
              :class="{ valid: !v$.thirdPlaceDecision.$invalid, invalid: v$.thirdPlaceDecision.$invalid }"
              v-model="v$.thirdPlaceDecision.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.tournament.divisionRule')" for="tournament-divisionRule"></label>
            <select
              class="form-control"
              name="divisionRule"
              :class="{ valid: !v$.divisionRule.$invalid, invalid: v$.divisionRule.$invalid }"
              v-model="v$.divisionRule.$model"
              id="tournament-divisionRule"
              data-cy="divisionRule"
              required
            >
              <option
                v-for="divisionRuleType in divisionRuleTypeValues"
                :key="divisionRuleType"
                v-bind:value="divisionRuleType"
                v-bind:label="t$('tossApp.DivisionRuleType.' + divisionRuleType)"
              >
                {{ divisionRuleType }}
              </option>
            </select>
            <div v-if="v$.divisionRule.$anyDirty && v$.divisionRule.$invalid">
              <small class="form-text text-danger" v-for="error of v$.divisionRule.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.tournament.entryLimit')" for="tournament-entryLimit"></label>
            <input
              type="checkbox"
              class="form-check"
              name="entryLimit"
              id="tournament-entryLimit"
              data-cy="entryLimit"
              :class="{ valid: !v$.entryLimit.$invalid, invalid: v$.entryLimit.$invalid }"
              v-model="v$.entryLimit.$model"
              required
            />
            <div v-if="v$.entryLimit.$anyDirty && v$.entryLimit.$invalid">
              <small class="form-text text-danger" v-for="error of v$.entryLimit.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.tournament.numOfEntry')" for="tournament-numOfEntry"></label>
            <input
              type="number"
              class="form-control"
              name="numOfEntry"
              id="tournament-numOfEntry"
              data-cy="numOfEntry"
              :class="{ valid: !v$.numOfEntry.$invalid, invalid: v$.numOfEntry.$invalid }"
              v-model.number="v$.numOfEntry.$model"
              required
            />
            <div v-if="v$.numOfEntry.$anyDirty && v$.numOfEntry.$invalid">
              <small class="form-text text-danger" v-for="error of v$.numOfEntry.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.tournament.divisionAssignMethod')"
              for="tournament-divisionAssignMethod"
            ></label>
            <select
              class="form-control"
              name="divisionAssignMethod"
              :class="{ valid: !v$.divisionAssignMethod.$invalid, invalid: v$.divisionAssignMethod.$invalid }"
              v-model="v$.divisionAssignMethod.$model"
              id="tournament-divisionAssignMethod"
              data-cy="divisionAssignMethod"
              required
            >
              <option
                v-for="divisionAssignMethod in divisionAssignMethodValues"
                :key="divisionAssignMethod"
                v-bind:value="divisionAssignMethod"
                v-bind:label="t$('tossApp.DivisionAssignMethod.' + divisionAssignMethod)"
              >
                {{ divisionAssignMethod }}
              </option>
            </select>
            <div v-if="v$.divisionAssignMethod.$anyDirty && v$.divisionAssignMethod.$invalid">
              <small class="form-text text-danger" v-for="error of v$.divisionAssignMethod.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.tournament.entryGenderType')" for="tournament-entryGenderType"></label>
            <select
              class="form-control"
              name="entryGenderType"
              :class="{ valid: !v$.entryGenderType.$invalid, invalid: v$.entryGenderType.$invalid }"
              v-model="v$.entryGenderType.$model"
              id="tournament-entryGenderType"
              data-cy="entryGenderType"
              required
            >
              <option
                v-for="entryGenderType in entryGenderTypeValues"
                :key="entryGenderType"
                v-bind:value="entryGenderType"
                v-bind:label="t$('tossApp.EntryGenderType.' + entryGenderType)"
              >
                {{ entryGenderType }}
              </option>
            </select>
            <div v-if="v$.entryGenderType.$anyDirty && v$.entryGenderType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.entryGenderType.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.tournament.handicap')" for="tournament-handicap"></label>
            <select
              class="form-control"
              name="handicap"
              :class="{ valid: !v$.handicap.$invalid, invalid: v$.handicap.$invalid }"
              v-model="v$.handicap.$model"
              id="tournament-handicap"
              data-cy="handicap"
              required
            >
              <option
                v-for="handicapType in handicapTypeValues"
                :key="handicapType"
                v-bind:value="handicapType"
                v-bind:label="t$('tossApp.HandicapType.' + handicapType)"
              >
                {{ handicapType }}
              </option>
            </select>
            <div v-if="v$.handicap.$anyDirty && v$.handicap.$invalid">
              <small class="form-text text-danger" v-for="error of v$.handicap.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.tournament.entryFee')" for="tournament-entryFee"></label>
            <select class="form-control" id="tournament-entryFee" data-cy="entryFee" name="entryFee" v-model="tournament.entryFee">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="tournament.entryFee && entryFeeOption.id === tournament.entryFee.id ? tournament.entryFee : entryFeeOption"
                v-for="entryFeeOption in entryFees"
                :key="entryFeeOption.id"
              >
                {{ entryFeeOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.tournament.competition')" for="tournament-competition"></label>
            <select
              class="form-control"
              id="tournament-competition"
              data-cy="competition"
              name="competition"
              v-model="tournament.competition"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  tournament.competition && competitionOption.id === tournament.competition.id ? tournament.competition : competitionOption
                "
                v-for="competitionOption in competitions"
                :key="competitionOption.id"
              >
                {{ competitionOption.id }}
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
<script lang="ts" src="./tournament-update.component.ts"></script>
