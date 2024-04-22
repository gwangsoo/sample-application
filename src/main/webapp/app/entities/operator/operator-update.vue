<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.operator.home.createOrEditLabel"
          data-cy="OperatorCreateUpdateHeading"
          v-text="t$('tossApp.operator.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="operator.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="operator.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.operator.userId')" for="operator-userId"></label>
            <input
              type="text"
              class="form-control"
              name="userId"
              id="operator-userId"
              data-cy="userId"
              :class="{ valid: !v$.userId.$invalid, invalid: v$.userId.$invalid }"
              v-model="v$.userId.$model"
              required
            />
            <div v-if="v$.userId.$anyDirty && v$.userId.$invalid">
              <small class="form-text text-danger" v-for="error of v$.userId.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.operator.userName')" for="operator-userName"></label>
            <input
              type="text"
              class="form-control"
              name="userName"
              id="operator-userName"
              data-cy="userName"
              :class="{ valid: !v$.userName.$invalid, invalid: v$.userName.$invalid }"
              v-model="v$.userName.$model"
              required
            />
            <div v-if="v$.userName.$anyDirty && v$.userName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.userName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.operator.phone')" for="operator-phone"></label>
            <input
              type="text"
              class="form-control"
              name="phone"
              id="operator-phone"
              data-cy="phone"
              :class="{ valid: !v$.phone.$invalid, invalid: v$.phone.$invalid }"
              v-model="v$.phone.$model"
            />
            <div v-if="v$.phone.$anyDirty && v$.phone.$invalid">
              <small class="form-text text-danger" v-for="error of v$.phone.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.operator.email')" for="operator-email"></label>
            <input
              type="text"
              class="form-control"
              name="email"
              id="operator-email"
              data-cy="email"
              :class="{ valid: !v$.email.$invalid, invalid: v$.email.$invalid }"
              v-model="v$.email.$model"
            />
            <div v-if="v$.email.$anyDirty && v$.email.$invalid">
              <small class="form-text text-danger" v-for="error of v$.email.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.operator.address')" for="operator-address"></label>
            <input
              type="text"
              class="form-control"
              name="address"
              id="operator-address"
              data-cy="address"
              :class="{ valid: !v$.address.$invalid, invalid: v$.address.$invalid }"
              v-model="v$.address.$model"
            />
            <div v-if="v$.address.$anyDirty && v$.address.$invalid">
              <small class="form-text text-danger" v-for="error of v$.address.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.operator.approvalStatus')" for="operator-approvalStatus"></label>
            <input
              type="checkbox"
              class="form-check"
              name="approvalStatus"
              id="operator-approvalStatus"
              data-cy="approvalStatus"
              :class="{ valid: !v$.approvalStatus.$invalid, invalid: v$.approvalStatus.$invalid }"
              v-model="v$.approvalStatus.$model"
              required
            />
            <div v-if="v$.approvalStatus.$anyDirty && v$.approvalStatus.$invalid">
              <small class="form-text text-danger" v-for="error of v$.approvalStatus.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.operator.operatorRole')" for="operator-operatorRole"></label>
            <select
              class="form-control"
              id="operator-operatorRole"
              data-cy="operatorRole"
              name="operatorRole"
              v-model="operator.operatorRole"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  operator.operatorRole && operatorRoleOption.id === operator.operatorRole.id ? operator.operatorRole : operatorRoleOption
                "
                v-for="operatorRoleOption in operatorRoles"
                :key="operatorRoleOption.id"
              >
                {{ operatorRoleOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.operator.region')" for="operator-region"></label>
            <select class="form-control" id="operator-region" data-cy="region" name="region" v-model="operator.region">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="operator.region && regionOption.id === operator.region.id ? operator.region : regionOption"
                v-for="regionOption in regions"
                :key="regionOption.id"
              >
                {{ regionOption.id }}
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
<script lang="ts" src="./operator-update.component.ts"></script>
