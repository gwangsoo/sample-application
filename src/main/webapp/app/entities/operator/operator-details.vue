<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="operator">
        <h2 class="jh-entity-heading" data-cy="operatorDetailsHeading">
          <span v-text="t$('tossApp.operator.detail.title')"></span> {{ operator.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.operator.userId')"></span>
          </dt>
          <dd>
            <span>{{ operator.userId }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.operator.userName')"></span>
          </dt>
          <dd>
            <span>{{ operator.userName }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.operator.phone')"></span>
          </dt>
          <dd>
            <span>{{ operator.phone }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.operator.email')"></span>
          </dt>
          <dd>
            <span>{{ operator.email }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.operator.address')"></span>
          </dt>
          <dd>
            <span>{{ operator.address }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.operator.approvalStatus')"></span>
          </dt>
          <dd>
            <span>{{ operator.approvalStatus }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.operator.role')"></span>
          </dt>
          <dd>
            <div v-if="operator.role">
              <router-link :to="{ name: 'OperatorRoleView', params: { operatorRoleId: operator.role.id } }">{{
                operator.role.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('tossApp.operator.region')"></span>
          </dt>
          <dd>
            <div v-if="operator.region">
              <router-link :to="{ name: 'RegionView', params: { regionId: operator.region.id } }">{{ operator.region.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="operator.id" :to="{ name: 'OperatorEdit', params: { operatorId: operator.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./operator-details.component.ts"></script>
