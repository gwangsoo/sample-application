<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" auth="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.entryFee.home.createOrEditLabel"
          data-cy="EntryFeeCreateUpdateHeading"
          v-text="t$('tossApp.entryFee.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="entryFee.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="entryFee.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.entryFee.entryFeeType')" for="entry-fee-entryFeeType"></label>
            <select
              class="form-control"
              name="entryFeeType"
              :class="{ valid: !v$.entryFeeType.$invalid, invalid: v$.entryFeeType.$invalid }"
              v-model="v$.entryFeeType.$model"
              id="entry-fee-entryFeeType"
              data-cy="entryFeeType"
              required
            >
              <option
                v-for="entryFeeType in entryFeeTypeValues"
                :key="entryFeeType"
                v-bind:value="entryFeeType"
                v-bind:label="t$('tossApp.EntryFeeType.' + entryFeeType)"
              >
                {{ entryFeeType }}
              </option>
            </select>
            <div v-if="v$.entryFeeType.$anyDirty && v$.entryFeeType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.entryFeeType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.entryFee.entryFeeSubType')" for="entry-fee-entryFeeSubType"></label>
            <select
              class="form-control"
              name="entryFeeSubType"
              :class="{ valid: !v$.entryFeeSubType.$invalid, invalid: v$.entryFeeSubType.$invalid }"
              v-model="v$.entryFeeSubType.$model"
              id="entry-fee-entryFeeSubType"
              data-cy="entryFeeSubType"
            >
              <option
                v-for="entryFeeSubType in entryFeeSubTypeValues"
                :key="entryFeeSubType"
                v-bind:value="entryFeeSubType"
                v-bind:label="t$('tossApp.EntryFeeSubType.' + entryFeeSubType)"
              >
                {{ entryFeeSubType }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.entryFee.paymentMethodType')" for="entry-fee-paymentMethodType"></label>
            <select
              class="form-control"
              name="paymentMethodType"
              :class="{ valid: !v$.paymentMethodType.$invalid, invalid: v$.paymentMethodType.$invalid }"
              v-model="v$.paymentMethodType.$model"
              id="entry-fee-paymentMethodType"
              data-cy="paymentMethodType"
            >
              <option
                v-for="paymentMethodType in paymentMethodTypeValues"
                :key="paymentMethodType"
                v-bind:value="paymentMethodType"
                v-bind:label="t$('tossApp.PaymentMethodType.' + paymentMethodType)"
              >
                {{ paymentMethodType }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.entryFee.scheduleNumber')" for="entry-fee-scheduleNumber"></label>
            <input
              type="number"
              class="form-control"
              name="scheduleNumber"
              id="entry-fee-scheduleNumber"
              data-cy="scheduleNumber"
              :class="{ valid: !v$.scheduleNumber.$invalid, invalid: v$.scheduleNumber.$invalid }"
              v-model.number="v$.scheduleNumber.$model"
              required
            />
            <div v-if="v$.scheduleNumber.$anyDirty && v$.scheduleNumber.$invalid">
              <small class="form-text text-danger" v-for="error of v$.scheduleNumber.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.entryFee.fee')" for="entry-fee-fee"></label>
            <input
              type="number"
              class="form-control"
              name="fee"
              id="entry-fee-fee"
              data-cy="fee"
              :class="{ valid: !v$.fee.$invalid, invalid: v$.fee.$invalid }"
              v-model.number="v$.fee.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.entryFee.currency')" for="entry-fee-currency"></label>
            <select class="form-control" id="entry-fee-currency" data-cy="currency" name="currency" v-model="entryFee.currency">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="entryFee.currency && currencyOption.id === entryFee.currency.id ? entryFee.currency : currencyOption"
                v-for="currencyOption in currencies"
                :key="currencyOption.id"
              >
                {{ currencyOption.id }}
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
<script lang="ts" src="./entry-fee-update.component.ts"></script>
