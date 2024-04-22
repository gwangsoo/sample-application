<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.entry.home.createOrEditLabel"
          data-cy="EntryCreateUpdateHeading"
          v-text="t$('tossApp.entry.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="entry.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="entry.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.entry.entryNo')" for="entry-entryNo"></label>
            <input
              type="text"
              class="form-control"
              name="entryNo"
              id="entry-entryNo"
              data-cy="entryNo"
              :class="{ valid: !v$.entryNo.$invalid, invalid: v$.entryNo.$invalid }"
              v-model="v$.entryNo.$model"
              required
            />
            <div v-if="v$.entryNo.$anyDirty && v$.entryNo.$invalid">
              <small class="form-text text-danger" v-for="error of v$.entryNo.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.entry.phoenixNo')" for="entry-phoenixNo"></label>
            <input
              type="text"
              class="form-control"
              name="phoenixNo"
              id="entry-phoenixNo"
              data-cy="phoenixNo"
              :class="{ valid: !v$.phoenixNo.$invalid, invalid: v$.phoenixNo.$invalid }"
              v-model="v$.phoenixNo.$model"
            />
            <div v-if="v$.phoenixNo.$anyDirty && v$.phoenixNo.$invalid">
              <small class="form-text text-danger" v-for="error of v$.phoenixNo.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.entry.cardNo')" for="entry-cardNo"></label>
            <input
              type="text"
              class="form-control"
              name="cardNo"
              id="entry-cardNo"
              data-cy="cardNo"
              :class="{ valid: !v$.cardNo.$invalid, invalid: v$.cardNo.$invalid }"
              v-model="v$.cardNo.$model"
              required
            />
            <div v-if="v$.cardNo.$anyDirty && v$.cardNo.$invalid">
              <small class="form-text text-danger" v-for="error of v$.cardNo.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.entry.name')" for="entry-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="entry-name"
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
            <label class="form-control-label" v-text="t$('tossApp.entry.englishName')" for="entry-englishName"></label>
            <input
              type="text"
              class="form-control"
              name="englishName"
              id="entry-englishName"
              data-cy="englishName"
              :class="{ valid: !v$.englishName.$invalid, invalid: v$.englishName.$invalid }"
              v-model="v$.englishName.$model"
              required
            />
            <div v-if="v$.englishName.$anyDirty && v$.englishName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.englishName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.entry.rating')" for="entry-rating"></label>
            <input
              type="number"
              class="form-control"
              name="rating"
              id="entry-rating"
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
            <label class="form-control-label" v-text="t$('tossApp.entry.mobileNo')" for="entry-mobileNo"></label>
            <input
              type="text"
              class="form-control"
              name="mobileNo"
              id="entry-mobileNo"
              data-cy="mobileNo"
              :class="{ valid: !v$.mobileNo.$invalid, invalid: v$.mobileNo.$invalid }"
              v-model="v$.mobileNo.$model"
            />
            <div v-if="v$.mobileNo.$anyDirty && v$.mobileNo.$invalid">
              <small class="form-text text-danger" v-for="error of v$.mobileNo.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.entry.birthDate')" for="entry-birthDate"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="entry-birthDate"
                  v-model="v$.birthDate.$model"
                  name="birthDate"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="entry-birthDate"
                data-cy="birthDate"
                type="text"
                class="form-control"
                name="birthDate"
                :class="{ valid: !v$.birthDate.$invalid, invalid: v$.birthDate.$invalid }"
                v-model="v$.birthDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.entry.email')" for="entry-email"></label>
            <input
              type="text"
              class="form-control"
              name="email"
              id="entry-email"
              data-cy="email"
              :class="{ valid: !v$.email.$invalid, invalid: v$.email.$invalid }"
              v-model="v$.email.$model"
            />
            <div v-if="v$.email.$anyDirty && v$.email.$invalid">
              <small class="form-text text-danger" v-for="error of v$.email.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.entry.genderType')" for="entry-genderType"></label>
            <select
              class="form-control"
              name="genderType"
              :class="{ valid: !v$.genderType.$invalid, invalid: v$.genderType.$invalid }"
              v-model="v$.genderType.$model"
              id="entry-genderType"
              data-cy="genderType"
              required
            >
              <option
                v-for="genderType in genderTypeValues"
                :key="genderType"
                v-bind:value="genderType"
                v-bind:label="t$('tossApp.GenderType.' + genderType)"
              >
                {{ genderType }}
              </option>
            </select>
            <div v-if="v$.genderType.$anyDirty && v$.genderType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.genderType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.entry.attendanceStatusType')" for="entry-attendanceStatusType"></label>
            <select
              class="form-control"
              name="attendanceStatusType"
              :class="{ valid: !v$.attendanceStatusType.$invalid, invalid: v$.attendanceStatusType.$invalid }"
              v-model="v$.attendanceStatusType.$model"
              id="entry-attendanceStatusType"
              data-cy="attendanceStatusType"
            >
              <option
                v-for="attendanceStatusType in attendanceStatusTypeValues"
                :key="attendanceStatusType"
                v-bind:value="attendanceStatusType"
                v-bind:label="t$('tossApp.AttendanceStatusType.' + attendanceStatusType)"
              >
                {{ attendanceStatusType }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.entry.team')" for="entry-team"></label>
            <select class="form-control" id="entry-team" data-cy="team" name="team" v-model="entry.team">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="entry.team && teamOption.id === entry.team.id ? entry.team : teamOption"
                v-for="teamOption in teams"
                :key="teamOption.id"
              >
                {{ teamOption.id }}
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
<script lang="ts" src="./entry-update.component.ts"></script>
