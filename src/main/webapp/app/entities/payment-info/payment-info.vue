<template>
  <div>
    <h2 id="page-heading" data-cy="PaymentInfoHeading">
      <span v-text="t$('tossApp.paymentInfo.home.title')" id="payment-info-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.paymentInfo.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'PaymentInfoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-payment-info"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.paymentInfo.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && paymentInfos && paymentInfos.length === 0">
      <span v-text="t$('tossApp.paymentInfo.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="paymentInfos && paymentInfos.length > 0">
      <table class="table table-striped" aria-describedby="paymentInfos">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.paymentInfo.orderNumber')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.paymentInfo.paymentCompletedDate')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.paymentInfo.status')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.paymentInfo.paymentMethodType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.paymentInfo.pgTID')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.paymentInfo.pgStatus')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.paymentInfo.pgDetail')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.paymentInfo.payer')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.paymentInfo.payerPhone')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="paymentInfo in paymentInfos" :key="paymentInfo.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PaymentInfoView', params: { paymentInfoId: paymentInfo.id } }">{{ paymentInfo.id }}</router-link>
            </td>
            <td>{{ paymentInfo.orderNumber }}</td>
            <td>{{ formatDateShort(paymentInfo.paymentCompletedDate) || '' }}</td>
            <td v-text="t$('tossApp.PaymentStatusType.' + paymentInfo.status)"></td>
            <td v-text="t$('tossApp.PaymentMethodType.' + paymentInfo.paymentMethodType)"></td>
            <td>{{ paymentInfo.pgTID }}</td>
            <td>{{ paymentInfo.pgStatus }}</td>
            <td>{{ paymentInfo.pgDetail }}</td>
            <td>{{ paymentInfo.payer }}</td>
            <td>{{ paymentInfo.payerPhone }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'PaymentInfoView', params: { paymentInfoId: paymentInfo.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'PaymentInfoEdit', params: { paymentInfoId: paymentInfo.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(paymentInfo)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="tossApp.paymentInfo.delete.question" data-cy="paymentInfoDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-paymentInfo-heading" v-text="t$('tossApp.paymentInfo.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-paymentInfo"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removePaymentInfo()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./payment-info.component.ts"></script>
