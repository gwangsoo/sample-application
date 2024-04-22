<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="affiliatedInfo">
        <h2 class="jh-entity-heading" data-cy="affiliatedInfoDetailsHeading">
          <span v-text="t$('tossApp.affiliatedInfo.detail.title')"></span> {{ affiliatedInfo.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.affiliatedInfo.affiliatedType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.AffiliatedType.' + affiliatedInfo.affiliatedType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.affiliatedInfo.seq')"></span>
          </dt>
          <dd>
            <span>{{ affiliatedInfo.seq }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.affiliatedInfo.name')"></span>
          </dt>
          <dd>
            <span>{{ affiliatedInfo.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.affiliatedInfo.address')"></span>
          </dt>
          <dd>
            <span>{{ affiliatedInfo.address }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.affiliatedInfo.telNo')"></span>
          </dt>
          <dd>
            <span>{{ affiliatedInfo.telNo }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.affiliatedInfo.homepageUrl')"></span>
          </dt>
          <dd>
            <span>{{ affiliatedInfo.homepageUrl }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.affiliatedInfo.fileInfo')"></span>
          </dt>
          <dd>
            <div v-if="affiliatedInfo.fileInfo">
              <router-link :to="{ name: 'FileInfoView', params: { fileInfoId: affiliatedInfo.fileInfo.id } }">{{
                affiliatedInfo.fileInfo.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="affiliatedInfo.id"
          :to="{ name: 'AffiliatedInfoEdit', params: { affiliatedInfoId: affiliatedInfo.id } }"
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

<script lang="ts" src="./affiliated-info-details.component.ts"></script>
