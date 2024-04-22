<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="role">
        <h2 class="jh-entity-heading" data-cy="roleDetailsHeading"><span v-text="t$('tossApp.role.detail.title')"></span> {{ role.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.role.name')"></span>
          </dt>
          <dd>
            <span>{{ role.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.role.authScopeType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.AuthScopeType.' + role.authScopeType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.role.authLevelType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.AuthLevelType.' + role.authLevelType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.role.displayOrder')"></span>
          </dt>
          <dd>
            <span>{{ role.displayOrder }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.role.operatorRole')"></span>
          </dt>
          <dd>
            <div v-if="role.operatorRole">
              <router-link :to="{ name: 'OperatorRoleView', params: { operatorRoleId: role.operatorRole.id } }">{{
                role.operatorRole.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="role.id" :to="{ name: 'RoleEdit', params: { roleId: role.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./role-details.component.ts"></script>
