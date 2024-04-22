<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="entryFee">
        <h2 class="jh-entity-heading" data-cy="entryFeeDetailsHeading">
          <span v-text="t$('tossApp.entryFee.detail.title')"></span> {{ entryFee.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.entryFee.entryFeeType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.EntryFeeType.' + entryFee.entryFeeType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.entryFee.entryFeeSubType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.EntryFeeSubType.' + entryFee.entryFeeSubType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.entryFee.paymentMethodType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.PaymentMethodType.' + entryFee.paymentMethodType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.entryFee.scheduleNumber')"></span>
          </dt>
          <dd>
            <span>{{ entryFee.scheduleNumber }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.entryFee.fee')"></span>
          </dt>
          <dd>
            <span>{{ entryFee.fee }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.entryFee.currency')"></span>
          </dt>
          <dd>
            <div v-if="entryFee.currency">
              <router-link :to="{ name: 'CurrencyView', params: { currencyId: entryFee.currency.id } }">{{
                entryFee.currency.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="entryFee.id" :to="{ name: 'EntryFeeEdit', params: { entryFeeId: entryFee.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./entry-fee-details.component.ts"></script>
