<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" auth="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.matchFormatOption.home.createOrEditLabel"
          data-cy="MatchFormatOptionCreateUpdateHeading"
          v-text="t$('tossApp.matchFormatOption.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="matchFormatOption.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="matchFormatOption.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.matchFormatOption.game01InOptionType')"
              for="match-format-option-game01InOptionType"
            ></label>
            <select
              class="form-control"
              name="game01InOptionType"
              :class="{ valid: !v$.game01InOptionType.$invalid, invalid: v$.game01InOptionType.$invalid }"
              v-model="v$.game01InOptionType.$model"
              id="match-format-option-game01InOptionType"
              data-cy="game01InOptionType"
            >
              <option
                v-for="matchFormatInOptionType in matchFormatInOptionTypeValues"
                :key="matchFormatInOptionType"
                v-bind:value="matchFormatInOptionType"
                v-bind:label="t$('tossApp.MatchFormatInOptionType.' + matchFormatInOptionType)"
              >
                {{ matchFormatInOptionType }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.matchFormatOption.game01OutOptionType')"
              for="match-format-option-game01OutOptionType"
            ></label>
            <select
              class="form-control"
              name="game01OutOptionType"
              :class="{ valid: !v$.game01OutOptionType.$invalid, invalid: v$.game01OutOptionType.$invalid }"
              v-model="v$.game01OutOptionType.$model"
              id="match-format-option-game01OutOptionType"
              data-cy="game01OutOptionType"
            >
              <option
                v-for="matchFormatOutOptionType in matchFormatOutOptionTypeValues"
                :key="matchFormatOutOptionType"
                v-bind:value="matchFormatOutOptionType"
                v-bind:label="t$('tossApp.MatchFormatOutOptionType.' + matchFormatOutOptionType)"
              >
                {{ matchFormatOutOptionType }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.matchFormatOption.game01BullOptionType')"
              for="match-format-option-game01BullOptionType"
            ></label>
            <select
              class="form-control"
              name="game01BullOptionType"
              :class="{ valid: !v$.game01BullOptionType.$invalid, invalid: v$.game01BullOptionType.$invalid }"
              v-model="v$.game01BullOptionType.$model"
              id="match-format-option-game01BullOptionType"
              data-cy="game01BullOptionType"
            >
              <option
                v-for="matchFormatBullOptionType in matchFormatBullOptionTypeValues"
                :key="matchFormatBullOptionType"
                v-bind:value="matchFormatBullOptionType"
                v-bind:label="t$('tossApp.MatchFormatBullOptionType.' + matchFormatBullOptionType)"
              >
                {{ matchFormatBullOptionType }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.matchFormatOption.game01Arrange')"
              for="match-format-option-game01Arrange"
            ></label>
            <input
              type="checkbox"
              class="form-check"
              name="game01Arrange"
              id="match-format-option-game01Arrange"
              data-cy="game01Arrange"
              :class="{ valid: !v$.game01Arrange.$invalid, invalid: v$.game01Arrange.$invalid }"
              v-model="v$.game01Arrange.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.matchFormatOption.cricketOverKill')"
              for="match-format-option-cricketOverKill"
            ></label>
            <input
              type="checkbox"
              class="form-check"
              name="cricketOverKill"
              id="match-format-option-cricketOverKill"
              data-cy="cricketOverKill"
              :class="{ valid: !v$.cricketOverKill.$invalid, invalid: v$.cricketOverKill.$invalid }"
              v-model="v$.cricketOverKill.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.matchFormatOption.cricketScore')"
              for="match-format-option-cricketScore"
            ></label>
            <input
              type="number"
              class="form-control"
              name="cricketScore"
              id="match-format-option-cricketScore"
              data-cy="cricketScore"
              :class="{ valid: !v$.cricketScore.$invalid, invalid: v$.cricketScore.$invalid }"
              v-model.number="v$.cricketScore.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.matchFormatOption.teamGame01FreezeView')"
              for="match-format-option-teamGame01FreezeView"
            ></label>
            <select
              class="form-control"
              name="teamGame01FreezeView"
              :class="{ valid: !v$.teamGame01FreezeView.$invalid, invalid: v$.teamGame01FreezeView.$invalid }"
              v-model="v$.teamGame01FreezeView.$model"
              id="match-format-option-teamGame01FreezeView"
              data-cy="teamGame01FreezeView"
            >
              <option
                v-for="matchFormatFreezeOptionType in matchFormatFreezeOptionTypeValues"
                :key="matchFormatFreezeOptionType"
                v-bind:value="matchFormatFreezeOptionType"
                v-bind:label="t$('tossApp.MatchFormatFreezeOptionType.' + matchFormatFreezeOptionType)"
              >
                {{ matchFormatFreezeOptionType }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.matchFormatOption.teamGame01Point')"
              for="match-format-option-teamGame01Point"
            ></label>
            <input
              type="checkbox"
              class="form-check"
              name="teamGame01Point"
              id="match-format-option-teamGame01Point"
              data-cy="teamGame01Point"
              :class="{ valid: !v$.teamGame01Point.$invalid, invalid: v$.teamGame01Point.$invalid }"
              v-model="v$.teamGame01Point.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.matchFormatOption.teamCricketMark')"
              for="match-format-option-teamCricketMark"
            ></label>
            <input
              type="checkbox"
              class="form-check"
              name="teamCricketMark"
              id="match-format-option-teamCricketMark"
              data-cy="teamCricketMark"
              :class="{ valid: !v$.teamCricketMark.$invalid, invalid: v$.teamCricketMark.$invalid }"
              v-model="v$.teamCricketMark.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.matchFormatOption.teamCricketFinish')"
              for="match-format-option-teamCricketFinish"
            ></label>
            <select
              class="form-control"
              name="teamCricketFinish"
              :class="{ valid: !v$.teamCricketFinish.$invalid, invalid: v$.teamCricketFinish.$invalid }"
              v-model="v$.teamCricketFinish.$model"
              id="match-format-option-teamCricketFinish"
              data-cy="teamCricketFinish"
            >
              <option
                v-for="matchFormatTeamFinishOptionType in matchFormatTeamFinishOptionTypeValues"
                :key="matchFormatTeamFinishOptionType"
                v-bind:value="matchFormatTeamFinishOptionType"
                v-bind:label="t$('tossApp.MatchFormatTeamFinishOptionType.' + matchFormatTeamFinishOptionType)"
              >
                {{ matchFormatTeamFinishOptionType }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.matchFormatOption.teamCricketPoint')"
              for="match-format-option-teamCricketPoint"
            ></label>
            <input
              type="checkbox"
              class="form-check"
              name="teamCricketPoint"
              id="match-format-option-teamCricketPoint"
              data-cy="teamCricketPoint"
              :class="{ valid: !v$.teamCricketPoint.$invalid, invalid: v$.teamCricketPoint.$invalid }"
              v-model="v$.teamCricketPoint.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.matchFormatOption.matchFormat')"
              for="match-format-option-matchFormat"
            ></label>
            <select
              class="form-control"
              id="match-format-option-matchFormat"
              data-cy="matchFormat"
              name="matchFormat"
              v-model="matchFormatOption.matchFormat"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  matchFormatOption.matchFormat && matchFormatOption.id === matchFormatOption.matchFormat.id
                    ? matchFormatOption.matchFormat
                    : matchFormatOption
                "
                v-for="matchFormatOption in matchFormats"
                :key="matchFormatOption.id"
              >
                {{ matchFormatOption.id }}
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
<script lang="ts" src="./match-format-option-update.component.ts"></script>
