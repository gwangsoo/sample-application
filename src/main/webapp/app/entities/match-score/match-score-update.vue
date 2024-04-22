<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.matchScore.home.createOrEditLabel"
          data-cy="MatchScoreCreateUpdateHeading"
          v-text="t$('tossApp.matchScore.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="matchScore.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="matchScore.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchScore.setNo')" for="match-score-setNo"></label>
            <input
              type="number"
              class="form-control"
              name="setNo"
              id="match-score-setNo"
              data-cy="setNo"
              :class="{ valid: !v$.setNo.$invalid, invalid: v$.setNo.$invalid }"
              v-model.number="v$.setNo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchScore.lgeNo')" for="match-score-lgeNo"></label>
            <input
              type="number"
              class="form-control"
              name="lgeNo"
              id="match-score-lgeNo"
              data-cy="lgeNo"
              :class="{ valid: !v$.lgeNo.$invalid, invalid: v$.lgeNo.$invalid }"
              v-model.number="v$.lgeNo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchScore.legGameName')" for="match-score-legGameName"></label>
            <input
              type="text"
              class="form-control"
              name="legGameName"
              id="match-score-legGameName"
              data-cy="legGameName"
              :class="{ valid: !v$.legGameName.$invalid, invalid: v$.legGameName.$invalid }"
              v-model="v$.legGameName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchScore.homeLegScore')" for="match-score-homeLegScore"></label>
            <input
              type="number"
              class="form-control"
              name="homeLegScore"
              id="match-score-homeLegScore"
              data-cy="homeLegScore"
              :class="{ valid: !v$.homeLegScore.$invalid, invalid: v$.homeLegScore.$invalid }"
              v-model.number="v$.homeLegScore.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchScore.awayLegScore')" for="match-score-awayLegScore"></label>
            <input
              type="number"
              class="form-control"
              name="awayLegScore"
              id="match-score-awayLegScore"
              data-cy="awayLegScore"
              :class="{ valid: !v$.awayLegScore.$invalid, invalid: v$.awayLegScore.$invalid }"
              v-model.number="v$.awayLegScore.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.matchScore.match')" for="match-score-match"></label>
            <select class="form-control" id="match-score-match" data-cy="match" name="match" v-model="matchScore.match">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="matchScore.match && matchOption.id === matchScore.match.id ? matchScore.match : matchOption"
                v-for="matchOption in matches"
                :key="matchOption.id"
              >
                {{ matchOption.id }}
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
<script lang="ts" src="./match-score-update.component.ts"></script>
