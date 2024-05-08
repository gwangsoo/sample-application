<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" auth="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.fileInfo.home.createOrEditLabel"
          data-cy="FileInfoCreateUpdateHeading"
          v-text="t$('tossApp.fileInfo.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="fileInfo.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="fileInfo.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.fileInfo.originalName')" for="file-info-originalName"></label>
            <input
              type="text"
              class="form-control"
              name="originalName"
              id="file-info-originalName"
              data-cy="originalName"
              :class="{ valid: !v$.originalName.$invalid, invalid: v$.originalName.$invalid }"
              v-model="v$.originalName.$model"
              required
            />
            <div v-if="v$.originalName.$anyDirty && v$.originalName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.originalName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.fileInfo.mimeType')" for="file-info-mimeType"></label>
            <input
              type="text"
              class="form-control"
              name="mimeType"
              id="file-info-mimeType"
              data-cy="mimeType"
              :class="{ valid: !v$.mimeType.$invalid, invalid: v$.mimeType.$invalid }"
              v-model="v$.mimeType.$model"
              required
            />
            <div v-if="v$.mimeType.$anyDirty && v$.mimeType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.mimeType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.fileInfo.fileSize')" for="file-info-fileSize"></label>
            <input
              type="number"
              class="form-control"
              name="fileSize"
              id="file-info-fileSize"
              data-cy="fileSize"
              :class="{ valid: !v$.fileSize.$invalid, invalid: v$.fileSize.$invalid }"
              v-model.number="v$.fileSize.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.fileInfo.savedPath')" for="file-info-savedPath"></label>
            <input
              type="text"
              class="form-control"
              name="savedPath"
              id="file-info-savedPath"
              data-cy="savedPath"
              :class="{ valid: !v$.savedPath.$invalid, invalid: v$.savedPath.$invalid }"
              v-model="v$.savedPath.$model"
              required
            />
            <div v-if="v$.savedPath.$anyDirty && v$.savedPath.$invalid">
              <small class="form-text text-danger" v-for="error of v$.savedPath.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
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
<script lang="ts" src="./file-info-update.component.ts"></script>
