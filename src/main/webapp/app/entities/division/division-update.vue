<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.division.home.createOrEditLabel"
          data-cy="DivisionCreateUpdateHeading"
          v-text="t$('tossApp.division.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="division.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="division.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.division.seq')" for="division-seq"></label>
            <input
              type="number"
              class="form-control"
              name="seq"
              id="division-seq"
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
            <label class="form-control-label" v-text="t$('tossApp.division.name')" for="division-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="division-name"
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
            <label class="form-control-label" v-text="t$('tossApp.division.ratingRuleTeamMin')" for="division-ratingRuleTeamMin"></label>
            <input
              type="number"
              class="form-control"
              name="ratingRuleTeamMin"
              id="division-ratingRuleTeamMin"
              data-cy="ratingRuleTeamMin"
              :class="{ valid: !v$.ratingRuleTeamMin.$invalid, invalid: v$.ratingRuleTeamMin.$invalid }"
              v-model.number="v$.ratingRuleTeamMin.$model"
            />
            <div v-if="v$.ratingRuleTeamMin.$anyDirty && v$.ratingRuleTeamMin.$invalid">
              <small class="form-text text-danger" v-for="error of v$.ratingRuleTeamMin.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.division.ratingRuleTeamMax')" for="division-ratingRuleTeamMax"></label>
            <input
              type="number"
              class="form-control"
              name="ratingRuleTeamMax"
              id="division-ratingRuleTeamMax"
              data-cy="ratingRuleTeamMax"
              :class="{ valid: !v$.ratingRuleTeamMax.$invalid, invalid: v$.ratingRuleTeamMax.$invalid }"
              v-model.number="v$.ratingRuleTeamMax.$model"
            />
            <div v-if="v$.ratingRuleTeamMax.$anyDirty && v$.ratingRuleTeamMax.$invalid">
              <small class="form-text text-danger" v-for="error of v$.ratingRuleTeamMax.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.division.ratingRuleIndividualLimit')"
              for="division-ratingRuleIndividualLimit"
            ></label>
            <input
              type="checkbox"
              class="form-check"
              name="ratingRuleIndividualLimit"
              id="division-ratingRuleIndividualLimit"
              data-cy="ratingRuleIndividualLimit"
              :class="{ valid: !v$.ratingRuleIndividualLimit.$invalid, invalid: v$.ratingRuleIndividualLimit.$invalid }"
              v-model="v$.ratingRuleIndividualLimit.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.division.ratingRuleIndividualMin')"
              for="division-ratingRuleIndividualMin"
            ></label>
            <input
              type="number"
              class="form-control"
              name="ratingRuleIndividualMin"
              id="division-ratingRuleIndividualMin"
              data-cy="ratingRuleIndividualMin"
              :class="{ valid: !v$.ratingRuleIndividualMin.$invalid, invalid: v$.ratingRuleIndividualMin.$invalid }"
              v-model.number="v$.ratingRuleIndividualMin.$model"
            />
            <div v-if="v$.ratingRuleIndividualMin.$anyDirty && v$.ratingRuleIndividualMin.$invalid">
              <small class="form-text text-danger" v-for="error of v$.ratingRuleIndividualMin.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.division.ratingRuleIndividualMax')"
              for="division-ratingRuleIndividualMax"
            ></label>
            <input
              type="number"
              class="form-control"
              name="ratingRuleIndividualMax"
              id="division-ratingRuleIndividualMax"
              data-cy="ratingRuleIndividualMax"
              :class="{ valid: !v$.ratingRuleIndividualMax.$invalid, invalid: v$.ratingRuleIndividualMax.$invalid }"
              v-model.number="v$.ratingRuleIndividualMax.$model"
            />
            <div v-if="v$.ratingRuleIndividualMax.$anyDirty && v$.ratingRuleIndividualMax.$invalid">
              <small class="form-text text-danger" v-for="error of v$.ratingRuleIndividualMax.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.division.entryLimit')" for="division-entryLimit"></label>
            <input
              type="checkbox"
              class="form-check"
              name="entryLimit"
              id="division-entryLimit"
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
            <label
              class="form-control-label"
              v-text="t$('tossApp.division.roundRobinRankingDecisionType')"
              for="division-roundRobinRankingDecisionType"
            ></label>
            <select
              class="form-control"
              name="roundRobinRankingDecisionType"
              :class="{ valid: !v$.roundRobinRankingDecisionType.$invalid, invalid: v$.roundRobinRankingDecisionType.$invalid }"
              v-model="v$.roundRobinRankingDecisionType.$model"
              id="division-roundRobinRankingDecisionType"
              data-cy="roundRobinRankingDecisionType"
            >
              <option
                v-for="roundRobinRankingDecisionType in roundRobinRankingDecisionTypeValues"
                :key="roundRobinRankingDecisionType"
                v-bind:value="roundRobinRankingDecisionType"
                v-bind:label="t$('tossApp.RoundRobinRankingDecisionType.' + roundRobinRankingDecisionType)"
              >
                {{ roundRobinRankingDecisionType }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.division.roundRobinGroupType')"
              for="division-roundRobinGroupType"
            ></label>
            <select
              class="form-control"
              name="roundRobinGroupType"
              :class="{ valid: !v$.roundRobinGroupType.$invalid, invalid: v$.roundRobinGroupType.$invalid }"
              v-model="v$.roundRobinGroupType.$model"
              id="division-roundRobinGroupType"
              data-cy="roundRobinGroupType"
            >
              <option
                v-for="roundRobinGroupType in roundRobinGroupTypeValues"
                :key="roundRobinGroupType"
                v-bind:value="roundRobinGroupType"
                v-bind:label="t$('tossApp.RoundRobinGroupType.' + roundRobinGroupType)"
              >
                {{ roundRobinGroupType }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.division.nextRoundDecisionType')"
              for="division-nextRoundDecisionType"
            ></label>
            <select
              class="form-control"
              name="nextRoundDecisionType"
              :class="{ valid: !v$.nextRoundDecisionType.$invalid, invalid: v$.nextRoundDecisionType.$invalid }"
              v-model="v$.nextRoundDecisionType.$model"
              id="division-nextRoundDecisionType"
              data-cy="nextRoundDecisionType"
            >
              <option
                v-for="nextRoundDecisionType in nextRoundDecisionTypeValues"
                :key="nextRoundDecisionType"
                v-bind:value="nextRoundDecisionType"
                v-bind:label="t$('tossApp.NextRoundDecisionType.' + nextRoundDecisionType)"
              >
                {{ nextRoundDecisionType }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.division.roundRoginThirdDecision')"
              for="division-roundRoginThirdDecision"
            ></label>
            <input
              type="checkbox"
              class="form-check"
              name="roundRoginThirdDecision"
              id="division-roundRoginThirdDecision"
              data-cy="roundRoginThirdDecision"
              :class="{ valid: !v$.roundRoginThirdDecision.$invalid, invalid: v$.roundRoginThirdDecision.$invalid }"
              v-model="v$.roundRoginThirdDecision.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.division.thirdDecisionRankingRule')"
              for="division-thirdDecisionRankingRule"
            ></label>
            <select
              class="form-control"
              name="thirdDecisionRankingRule"
              :class="{ valid: !v$.thirdDecisionRankingRule.$invalid, invalid: v$.thirdDecisionRankingRule.$invalid }"
              v-model="v$.thirdDecisionRankingRule.$model"
              id="division-thirdDecisionRankingRule"
              data-cy="thirdDecisionRankingRule"
            >
              <option
                v-for="thirdDecisionRankingRule in thirdDecisionRankingRuleValues"
                :key="thirdDecisionRankingRule"
                v-bind:value="thirdDecisionRankingRule"
                v-bind:label="t$('tossApp.ThirdDecisionRankingRule.' + thirdDecisionRankingRule)"
              >
                {{ thirdDecisionRankingRule }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.division.useAllRoundSameFormat')"
              for="division-useAllRoundSameFormat"
            ></label>
            <input
              type="checkbox"
              class="form-check"
              name="useAllRoundSameFormat"
              id="division-useAllRoundSameFormat"
              data-cy="useAllRoundSameFormat"
              :class="{ valid: !v$.useAllRoundSameFormat.$invalid, invalid: v$.useAllRoundSameFormat.$invalid }"
              v-model="v$.useAllRoundSameFormat.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.division.eventRangeType')" for="division-eventRangeType"></label>
            <select
              class="form-control"
              name="eventRangeType"
              :class="{ valid: !v$.eventRangeType.$invalid, invalid: v$.eventRangeType.$invalid }"
              v-model="v$.eventRangeType.$model"
              id="division-eventRangeType"
              data-cy="eventRangeType"
            >
              <option
                v-for="eventRangeType in eventRangeTypeValues"
                :key="eventRangeType"
                v-bind:value="eventRangeType"
                v-bind:label="t$('tossApp.EventRangeType.' + eventRangeType)"
              >
                {{ eventRangeType }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.division.eliminationTeamCount')"
              for="division-eliminationTeamCount"
            ></label>
            <input
              type="number"
              class="form-control"
              name="eliminationTeamCount"
              id="division-eliminationTeamCount"
              data-cy="eliminationTeamCount"
              :class="{ valid: !v$.eliminationTeamCount.$invalid, invalid: v$.eliminationTeamCount.$invalid }"
              v-model.number="v$.eliminationTeamCount.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.division.tournament')" for="division-tournament"></label>
            <select class="form-control" id="division-tournament" data-cy="tournament" name="tournament" v-model="division.tournament">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  division.tournament && tournamentOption.id === division.tournament.id ? division.tournament : tournamentOption
                "
                v-for="tournamentOption in tournaments"
                :key="tournamentOption.id"
              >
                {{ tournamentOption.id }}
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
<script lang="ts" src="./division-update.component.ts"></script>
