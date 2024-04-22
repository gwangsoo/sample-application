<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="tossApp.paymentInfo.home.createOrEditLabel"
          data-cy="PaymentInfoCreateUpdateHeading"
          v-text="t$('tossApp.paymentInfo.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="paymentInfo.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="paymentInfo.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.paymentInfo.orderNumber')" for="payment-info-orderNumber"></label>
            <input
              type="text"
              class="form-control"
              name="orderNumber"
              id="payment-info-orderNumber"
              data-cy="orderNumber"
              :class="{ valid: !v$.orderNumber.$invalid, invalid: v$.orderNumber.$invalid }"
              v-model="v$.orderNumber.$model"
            />
            <div v-if="v$.orderNumber.$anyDirty && v$.orderNumber.$invalid">
              <small class="form-text text-danger" v-for="error of v$.orderNumber.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.paymentInfo.paymentCompletedDate')"
              for="payment-info-paymentCompletedDate"
            ></label>
            <div class="d-flex">
              <input
                id="payment-info-paymentCompletedDate"
                data-cy="paymentCompletedDate"
                type="datetime-local"
                class="form-control"
                name="paymentCompletedDate"
                :class="{ valid: !v$.paymentCompletedDate.$invalid, invalid: v$.paymentCompletedDate.$invalid }"
                :value="convertDateTimeFromServer(v$.paymentCompletedDate.$model)"
                @change="updateInstantField('paymentCompletedDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.paymentInfo.status')" for="payment-info-status"></label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !v$.status.$invalid, invalid: v$.status.$invalid }"
              v-model="v$.status.$model"
              id="payment-info-status"
              data-cy="status"
              required
            >
              <option
                v-for="paymentStatusType in paymentStatusTypeValues"
                :key="paymentStatusType"
                v-bind:value="paymentStatusType"
                v-bind:label="t$('tossApp.PaymentStatusType.' + paymentStatusType)"
              >
                {{ paymentStatusType }}
              </option>
            </select>
            <div v-if="v$.status.$anyDirty && v$.status.$invalid">
              <small class="form-text text-danger" v-for="error of v$.status.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('tossApp.paymentInfo.paymentMethodType')"
              for="payment-info-paymentMethodType"
            ></label>
            <select
              class="form-control"
              name="paymentMethodType"
              :class="{ valid: !v$.paymentMethodType.$invalid, invalid: v$.paymentMethodType.$invalid }"
              v-model="v$.paymentMethodType.$model"
              id="payment-info-paymentMethodType"
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
            <label class="form-control-label" v-text="t$('tossApp.paymentInfo.pgTID')" for="payment-info-pgTID"></label>
            <input
              type="number"
              class="form-control"
              name="pgTID"
              id="payment-info-pgTID"
              data-cy="pgTID"
              :class="{ valid: !v$.pgTID.$invalid, invalid: v$.pgTID.$invalid }"
              v-model.number="v$.pgTID.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.paymentInfo.pgStatus')" for="payment-info-pgStatus"></label>
            <input
              type="number"
              class="form-control"
              name="pgStatus"
              id="payment-info-pgStatus"
              data-cy="pgStatus"
              :class="{ valid: !v$.pgStatus.$invalid, invalid: v$.pgStatus.$invalid }"
              v-model.number="v$.pgStatus.$model"
            />
            <div v-if="v$.pgStatus.$anyDirty && v$.pgStatus.$invalid">
              <small class="form-text text-danger" v-for="error of v$.pgStatus.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.paymentInfo.pgDetail')" for="payment-info-pgDetail"></label>
            <input
              type="text"
              class="form-control"
              name="pgDetail"
              id="payment-info-pgDetail"
              data-cy="pgDetail"
              :class="{ valid: !v$.pgDetail.$invalid, invalid: v$.pgDetail.$invalid }"
              v-model="v$.pgDetail.$model"
            />
            <div v-if="v$.pgDetail.$anyDirty && v$.pgDetail.$invalid">
              <small class="form-text text-danger" v-for="error of v$.pgDetail.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.paymentInfo.payer')" for="payment-info-payer"></label>
            <input
              type="text"
              class="form-control"
              name="payer"
              id="payment-info-payer"
              data-cy="payer"
              :class="{ valid: !v$.payer.$invalid, invalid: v$.payer.$invalid }"
              v-model="v$.payer.$model"
            />
            <div v-if="v$.payer.$anyDirty && v$.payer.$invalid">
              <small class="form-text text-danger" v-for="error of v$.payer.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('tossApp.paymentInfo.payerPhone')" for="payment-info-payerPhone"></label>
            <input
              type="text"
              class="form-control"
              name="payerPhone"
              id="payment-info-payerPhone"
              data-cy="payerPhone"
              :class="{ valid: !v$.payerPhone.$invalid, invalid: v$.payerPhone.$invalid }"
              v-model="v$.payerPhone.$model"
            />
            <div v-if="v$.payerPhone.$anyDirty && v$.payerPhone.$invalid">
              <small class="form-text text-danger" v-for="error of v$.payerPhone.$errors" :key="error.$uid">{{ error.$message }}</small>
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
<script lang="ts" src="./payment-info-update.component.ts"></script>
