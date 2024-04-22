<template>
  <div>
    <h2 id="page-heading" data-cy="EntryFeeHeading">
      <span v-text="t$('tossApp.entryFee.home.title')" id="entry-fee-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.entryFee.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'EntryFeeCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-entry-fee"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.entryFee.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && entryFees && entryFees.length === 0">
      <span v-text="t$('tossApp.entryFee.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="entryFees && entryFees.length > 0">
      <table class="table table-striped" aria-describedby="entryFees">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.entryFee.entryFeeType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.entryFee.entryFeeSubType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.entryFee.paymentMethodType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.entryFee.scheduleNumber')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.entryFee.fee')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.entryFee.currency')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="entryFee in entryFees" :key="entryFee.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EntryFeeView', params: { entryFeeId: entryFee.id } }">{{ entryFee.id }}</router-link>
            </td>
            <td v-text="t$('tossApp.EntryFeeType.' + entryFee.entryFeeType)"></td>
            <td v-text="t$('tossApp.EntryFeeSubType.' + entryFee.entryFeeSubType)"></td>
            <td v-text="t$('tossApp.PaymentMethodType.' + entryFee.paymentMethodType)"></td>
            <td>{{ entryFee.scheduleNumber }}</td>
            <td>{{ entryFee.fee }}</td>
            <td>
              <div v-if="entryFee.currency">
                <router-link :to="{ name: 'CurrencyView', params: { currencyId: entryFee.currency.id } }">{{
                  entryFee.currency.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'EntryFeeView', params: { entryFeeId: entryFee.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'EntryFeeEdit', params: { entryFeeId: entryFee.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(entryFee)"
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
        <span id="tossApp.entryFee.delete.question" data-cy="entryFeeDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-entryFee-heading" v-text="t$('tossApp.entryFee.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-entryFee"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeEntryFee()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./entry-fee.component.ts"></script>
