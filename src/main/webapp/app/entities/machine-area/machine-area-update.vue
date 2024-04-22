<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.machineArea.home.createOrEditLabel"
          data-cy="MachineAreaCreateUpdateHeading"
          v-text="t$('tossApp.machineArea.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="machineArea.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="machineArea.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.machineArea.mame')" for="machine-area-mame"></label>
            <input
              type="text"
              class="form-control"
              name="mame"
              id="machine-area-mame"
              data-cy="mame"
              :class="{ valid: !v$.mame.$invalid, invalid: v$.mame.$invalid }"
              v-model="v$.mame.$model"
              required
            />
            <div v-if="v$.mame.$anyDirty && v$.mame.$invalid">
              <small class="form-text text-danger" v-for="error of v$.mame.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.machineArea.seq')" for="machine-area-seq"></label>
            <input
              type="number"
              class="form-control"
              name="seq"
              id="machine-area-seq"
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
            <label class="form-control-label" v-text="t$('tossApp.machineArea.numOfMachine')" for="machine-area-numOfMachine"></label>
            <input
              type="number"
              class="form-control"
              name="numOfMachine"
              id="machine-area-numOfMachine"
              data-cy="numOfMachine"
              :class="{ valid: !v$.numOfMachine.$invalid, invalid: v$.numOfMachine.$invalid }"
              v-model.number="v$.numOfMachine.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.machineArea.competition')" for="machine-area-competition"></label>
            <select
              class="form-control"
              id="machine-area-competition"
              data-cy="competition"
              name="competition"
              v-model="machineArea.competition"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  machineArea.competition && competitionOption.id === machineArea.competition.id
                    ? machineArea.competition
                    : competitionOption
                "
                v-for="competitionOption in competitions"
                :key="competitionOption.id"
              >
                {{ competitionOption.id }}
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
<script lang="ts" src="./machine-area-update.component.ts"></script>
