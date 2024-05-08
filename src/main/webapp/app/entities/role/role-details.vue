<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="auth">
        <h2 class="jh-entity-heading" data-cy="roleDetailsHeading"><span v-text="t$('tossApp.auth.detail.title')"></span> {{ auth.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.auth.name')"></span>
          </dt>
          <dd>
            <span>{{ auth.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.auth.authScopeType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.AuthScopeType.' + auth.authScopeType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.auth.authLevelType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.AuthLevelType.' + auth.authLevelType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.auth.displayOrder')"></span>
          </dt>
          <dd>
            <span>{{ auth.displayOrder }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.auth.role')"></span>
          </dt>
          <dd>
            <div v-if="auth.role">
              <router-link :to="{ name: 'OperatorRoleView', params: { operatorRoleId: auth.role.id } }">{{
                auth.role.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="auth.id" :to="{ name: 'RoleEdit', params: { roleId: auth.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./auth-details.component.ts"></script>
