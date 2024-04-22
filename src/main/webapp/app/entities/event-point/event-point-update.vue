<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.eventPoint.home.createOrEditLabel"
          data-cy="EventPointCreateUpdateHeading"
          v-text="t$('tossApp.eventPoint.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="eventPoint.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="eventPoint.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.eventPoint.seq')" for="event-point-seq"></label>
            <input
              type="number"
              class="form-control"
              name="seq"
              id="event-point-seq"
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
            <label class="form-control-label" v-text="t$('tossApp.eventPoint.rating')" for="event-point-rating"></label>
            <input
              type="number"
              class="form-control"
              name="rating"
              id="event-point-rating"
              data-cy="rating"
              :class="{ valid: !v$.rating.$invalid, invalid: v$.rating.$invalid }"
              v-model.number="v$.rating.$model"
              required
            />
            <div v-if="v$.rating.$anyDirty && v$.rating.$invalid">
              <small class="form-text text-danger" v-for="error of v$.rating.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.eventPoint.ratingMin')" for="event-point-ratingMin"></label>
            <input
              type="number"
              class="form-control"
              name="ratingMin"
              id="event-point-ratingMin"
              data-cy="ratingMin"
              :class="{ valid: !v$.ratingMin.$invalid, invalid: v$.ratingMin.$invalid }"
              v-model.number="v$.ratingMin.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.eventPoint.ratingMax')" for="event-point-ratingMax"></label>
            <input
              type="number"
              class="form-control"
              name="ratingMax"
              id="event-point-ratingMax"
              data-cy="ratingMax"
              :class="{ valid: !v$.ratingMax.$invalid, invalid: v$.ratingMax.$invalid }"
              v-model.number="v$.ratingMax.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.eventPoint.division')" for="event-point-division"></label>
            <select class="form-control" id="event-point-division" data-cy="division" name="division" v-model="eventPoint.division">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="eventPoint.division && divisionOption.id === eventPoint.division.id ? eventPoint.division : divisionOption"
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
<script lang="ts" src="./event-point-update.component.ts"></script>
