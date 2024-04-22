<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.role.home.createOrEditLabel"
          data-cy="RoleCreateUpdateHeading"
          v-text="t$('tossApp.role.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="role.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="role.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.role.name')" for="role-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="role-name"
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
            <label class="form-control-label" v-text="t$('tossApp.role.authScopeType')" for="role-authScopeType"></label>
            <select
              class="form-control"
              name="authScopeType"
              :class="{ valid: !v$.authScopeType.$invalid, invalid: v$.authScopeType.$invalid }"
              v-model="v$.authScopeType.$model"
              id="role-authScopeType"
              data-cy="authScopeType"
              required
            >
              <option
                v-for="authScopeType in authScopeTypeValues"
                :key="authScopeType"
                v-bind:value="authScopeType"
                v-bind:label="t$('tossApp.AuthScopeType.' + authScopeType)"
              >
                {{ authScopeType }}
              </option>
            </select>
            <div v-if="v$.authScopeType.$anyDirty && v$.authScopeType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.authScopeType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.role.authLevelType')" for="role-authLevelType"></label>
            <select
              class="form-control"
              name="authLevelType"
              :class="{ valid: !v$.authLevelType.$invalid, invalid: v$.authLevelType.$invalid }"
              v-model="v$.authLevelType.$model"
              id="role-authLevelType"
              data-cy="authLevelType"
              required
            >
              <option
                v-for="authLevelType in authLevelTypeValues"
                :key="authLevelType"
                v-bind:value="authLevelType"
                v-bind:label="t$('tossApp.AuthLevelType.' + authLevelType)"
              >
                {{ authLevelType }}
              </option>
            </select>
            <div v-if="v$.authLevelType.$anyDirty && v$.authLevelType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.authLevelType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.role.displayOrder')" for="role-displayOrder"></label>
            <input
              type="number"
              class="form-control"
              name="displayOrder"
              id="role-displayOrder"
              data-cy="displayOrder"
              :class="{ valid: !v$.displayOrder.$invalid, invalid: v$.displayOrder.$invalid }"
              v-model.number="v$.displayOrder.$model"
              required
            />
            <div v-if="v$.displayOrder.$anyDirty && v$.displayOrder.$invalid">
              <small class="form-text text-danger" v-for="error of v$.displayOrder.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.role.operatorRole')" for="role-operatorRole"></label>
            <select class="form-control" id="role-operatorRole" data-cy="operatorRole" name="operatorRole" v-model="role.operatorRole">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="role.operatorRole && operatorRoleOption.id === role.operatorRole.id ? role.operatorRole : operatorRoleOption"
                v-for="operatorRoleOption in operatorRoles"
                :key="operatorRoleOption.id"
              >
                {{ operatorRoleOption.id }}
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
<script lang="ts" src="./role-update.component.ts"></script>
