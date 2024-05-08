<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" auth="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.region.home.createOrEditLabel"
          data-cy="RegionCreateUpdateHeading"
          v-text="t$('tossApp.region.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="region.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="region.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.region.name')" for="region-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="region-name"
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
            <label class="form-control-label" v-text="t$('tossApp.region.parentAreaId')" for="region-parentAreaId"></label>
            <input
              type="text"
              class="form-control"
              name="parentAreaId"
              id="region-parentAreaId"
              data-cy="parentAreaId"
              :class="{ valid: !v$.parentAreaId.$invalid, invalid: v$.parentAreaId.$invalid }"
              v-model="v$.parentAreaId.$model"
              required
            />
            <div v-if="v$.parentAreaId.$anyDirty && v$.parentAreaId.$invalid">
              <small class="form-text text-danger" v-for="error of v$.parentAreaId.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.region.country')" for="region-country"></label>
            <select class="form-control" id="region-country" data-cy="country" name="country" v-model="region.country">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="region.country && countryOption.id === region.country.id ? region.country : countryOption"
                v-for="countryOption in countries"
                :key="countryOption.id"
              >
                {{ countryOption.id }}
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
<script lang="ts" src="./region-update.component.ts"></script>
