<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="fileInfo">
        <h2 class="jh-entity-heading" data-cy="fileInfoDetailsHeading">
          <span v-text="t$('tossApp.fileInfo.detail.title')"></span> {{ fileInfo.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.fileInfo.originalName')"></span>
          </dt>
          <dd>
            <span>{{ fileInfo.originalName }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.fileInfo.mimeType')"></span>
          </dt>
          <dd>
            <span>{{ fileInfo.mimeType }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.fileInfo.fileSize')"></span>
          </dt>
          <dd>
            <span>{{ fileInfo.fileSize }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.fileInfo.savedPath')"></span>
          </dt>
          <dd>
            <span>{{ fileInfo.savedPath }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="fileInfo.id" :to="{ name: 'FileInfoEdit', params: { fileInfoId: fileInfo.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./file-info-details.component.ts"></script>
