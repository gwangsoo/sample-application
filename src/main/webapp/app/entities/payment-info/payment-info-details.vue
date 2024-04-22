<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="paymentInfo">
        <h2 class="jh-entity-heading" data-cy="paymentInfoDetailsHeading">
          <span v-text="t$('tossApp.paymentInfo.detail.title')"></span> {{ paymentInfo.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.paymentInfo.orderNumber')"></span>
          </dt>
          <dd>
            <span>{{ paymentInfo.orderNumber }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.paymentInfo.paymentCompletedDate')"></span>
          </dt>
          <dd>
            <span v-if="paymentInfo.paymentCompletedDate">{{ formatDateLong(paymentInfo.paymentCompletedDate) }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.paymentInfo.status')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.PaymentStatusType.' + paymentInfo.status)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.paymentInfo.paymentMethodType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.PaymentMethodType.' + paymentInfo.paymentMethodType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.paymentInfo.pgTID')"></span>
          </dt>
          <dd>
            <span>{{ paymentInfo.pgTID }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.paymentInfo.pgStatus')"></span>
          </dt>
          <dd>
            <span>{{ paymentInfo.pgStatus }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.paymentInfo.pgDetail')"></span>
          </dt>
          <dd>
            <span>{{ paymentInfo.pgDetail }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.paymentInfo.payer')"></span>
          </dt>
          <dd>
            <span>{{ paymentInfo.payer }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.paymentInfo.payerPhone')"></span>
          </dt>
          <dd>
            <span>{{ paymentInfo.payerPhone }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="paymentInfo.id"
          :to="{ name: 'PaymentInfoEdit', params: { paymentInfoId: paymentInfo.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./payment-info-details.component.ts"></script>
