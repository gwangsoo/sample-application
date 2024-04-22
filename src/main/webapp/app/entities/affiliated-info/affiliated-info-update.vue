<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.affiliatedInfo.home.createOrEditLabel"
          data-cy="AffiliatedInfoCreateUpdateHeading"
          v-text="t$('tossApp.affiliatedInfo.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="affiliatedInfo.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="affiliatedInfo.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.affiliatedInfo.affiliatedType')"
              for="affiliated-info-affiliatedType"
            ></label>
            <select
              class="form-control"
              name="affiliatedType"
              :class="{ valid: !v$.affiliatedType.$invalid, invalid: v$.affiliatedType.$invalid }"
              v-model="v$.affiliatedType.$model"
              id="affiliated-info-affiliatedType"
              data-cy="affiliatedType"
              required
            >
              <option
                v-for="affiliatedType in affiliatedTypeValues"
                :key="affiliatedType"
                v-bind:value="affiliatedType"
                v-bind:label="t$('tossApp.AffiliatedType.' + affiliatedType)"
              >
                {{ affiliatedType }}
              </option>
            </select>
            <div v-if="v$.affiliatedType.$anyDirty && v$.affiliatedType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.affiliatedType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.affiliatedInfo.seq')" for="affiliated-info-seq"></label>
            <input
              type="text"
              class="form-control"
              name="seq"
              id="affiliated-info-seq"
              data-cy="seq"
              :class="{ valid: !v$.seq.$invalid, invalid: v$.seq.$invalid }"
              v-model="v$.seq.$model"
            />
            <div v-if="v$.seq.$anyDirty && v$.seq.$invalid">
              <small class="form-text text-danger" v-for="error of v$.seq.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.affiliatedInfo.name')" for="affiliated-info-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="affiliated-info-name"
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
            <label class="form-control-label" v-text="t$('tossApp.affiliatedInfo.address')" for="affiliated-info-address"></label>
            <input
              type="text"
              class="form-control"
              name="address"
              id="affiliated-info-address"
              data-cy="address"
              :class="{ valid: !v$.address.$invalid, invalid: v$.address.$invalid }"
              v-model="v$.address.$model"
            />
            <div v-if="v$.address.$anyDirty && v$.address.$invalid">
              <small class="form-text text-danger" v-for="error of v$.address.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.affiliatedInfo.telNo')" for="affiliated-info-telNo"></label>
            <input
              type="text"
              class="form-control"
              name="telNo"
              id="affiliated-info-telNo"
              data-cy="telNo"
              :class="{ valid: !v$.telNo.$invalid, invalid: v$.telNo.$invalid }"
              v-model="v$.telNo.$model"
            />
            <div v-if="v$.telNo.$anyDirty && v$.telNo.$invalid">
              <small class="form-text text-danger" v-for="error of v$.telNo.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.affiliatedInfo.homepageUrl')" for="affiliated-info-homepageUrl"></label>
            <input
              type="text"
              class="form-control"
              name="homepageUrl"
              id="affiliated-info-homepageUrl"
              data-cy="homepageUrl"
              :class="{ valid: !v$.homepageUrl.$invalid, invalid: v$.homepageUrl.$invalid }"
              v-model="v$.homepageUrl.$model"
            />
            <div v-if="v$.homepageUrl.$anyDirty && v$.homepageUrl.$invalid">
              <small class="form-text text-danger" v-for="error of v$.homepageUrl.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.affiliatedInfo.fileInfo')" for="affiliated-info-fileInfo"></label>
            <select class="form-control" id="affiliated-info-fileInfo" data-cy="fileInfo" name="fileInfo" v-model="affiliatedInfo.fileInfo">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  affiliatedInfo.fileInfo && fileInfoOption.id === affiliatedInfo.fileInfo.id ? affiliatedInfo.fileInfo : fileInfoOption
                "
                v-for="fileInfoOption in fileInfos"
                :key="fileInfoOption.id"
              >
                {{ fileInfoOption.id }}
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
<script lang="ts" src="./affiliated-info-update.component.ts"></script>
