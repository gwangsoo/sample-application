<template>
  <div>
    <h2 id="page-heading" data-cy="FileInfoHeading">
      <span v-text="t$('tossApp.fileInfo.home.title')" id="file-info-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('tossApp.fileInfo.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'FileInfoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-file-info"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('tossApp.fileInfo.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && fileInfos && fileInfos.length === 0">
      <span v-text="t$('tossApp.fileInfo.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="fileInfos && fileInfos.length > 0">
      <table class="table table-striped" aria-describedby="fileInfos">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.fileInfo.originalName')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.fileInfo.mimeType')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.fileInfo.fileSize')"></span></th>
            <th scope="row"><span v-text="t$('tossApp.fileInfo.savedPath')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="fileInfo in fileInfos" :key="fileInfo.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'FileInfoView', params: { fileInfoId: fileInfo.id } }">{{ fileInfo.id }}</router-link>
            </td>
            <td>{{ fileInfo.originalName }}</td>
            <td>{{ fileInfo.mimeType }}</td>
            <td>{{ fileInfo.fileSize }}</td>
            <td>{{ fileInfo.savedPath }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'FileInfoView', params: { fileInfoId: fileInfo.id } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                </router-link>
                <router-link
                  :to="{ name: 'FileInfoEdit', params: { fileInfoId: fileInfo.id } }"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(fileInfo)"
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
        <span id="tossApp.fileInfo.delete.question" data-cy="fileInfoDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-fileInfo-heading" v-text="t$('tossApp.fileInfo.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-fileInfo"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeFileInfo()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./file-info.component.ts"></script>
