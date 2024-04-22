<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.machine.home.createOrEditLabel"
          data-cy="MachineCreateUpdateHeading"
          v-text="t$('tossApp.machine.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="machine.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="machine.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.machine.machineNo')" for="machine-machineNo"></label>
            <input
              type="number"
              class="form-control"
              name="machineNo"
              id="machine-machineNo"
              data-cy="machineNo"
              :class="{ valid: !v$.machineNo.$invalid, invalid: v$.machineNo.$invalid }"
              v-model.number="v$.machineNo.$model"
              required
            />
            <div v-if="v$.machineNo.$anyDirty && v$.machineNo.$invalid">
              <small class="form-text text-danger" v-for="error of v$.machineNo.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.machine.machineStatusType')" for="machine-machineStatusType"></label>
            <select
              class="form-control"
              name="machineStatusType"
              :class="{ valid: !v$.machineStatusType.$invalid, invalid: v$.machineStatusType.$invalid }"
              v-model="v$.machineStatusType.$model"
              id="machine-machineStatusType"
              data-cy="machineStatusType"
              required
            >
              <option
                v-for="machineStatusType in machineStatusTypeValues"
                :key="machineStatusType"
                v-bind:value="machineStatusType"
                v-bind:label="t$('tossApp.MachineStatusType.' + machineStatusType)"
              >
                {{ machineStatusType }}
              </option>
            </select>
            <div v-if="v$.machineStatusType.$anyDirty && v$.machineStatusType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.machineStatusType.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.machine.match')" for="machine-match"></label>
            <select class="form-control" id="machine-match" data-cy="match" name="match" v-model="machine.match">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="machine.match && matchOption.id === machine.match.id ? machine.match : matchOption"
                v-for="matchOption in matches"
                :key="matchOption.id"
              >
                {{ matchOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.machine.machineArea')" for="machine-machineArea"></label>
            <select class="form-control" id="machine-machineArea" data-cy="machineArea" name="machineArea" v-model="machine.machineArea">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  machine.machineArea && machineAreaOption.id === machine.machineArea.id ? machine.machineArea : machineAreaOption
                "
                v-for="machineAreaOption in machineAreas"
                :key="machineAreaOption.id"
              >
                {{ machineAreaOption.id }}
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
<script lang="ts" src="./machine-update.component.ts"></script>
