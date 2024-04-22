<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="machineArea">
        <h2 class="jh-entity-heading" data-cy="machineAreaDetailsHeading">
          <span v-text="t$('tossApp.machineArea.detail.title')"></span> {{ machineArea.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.machineArea.mame')"></span>
          </dt>
          <dd>
            <span>{{ machineArea.mame }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.machineArea.seq')"></span>
          </dt>
          <dd>
            <span>{{ machineArea.seq }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.machineArea.numOfMachine')"></span>
          </dt>
          <dd>
            <span>{{ machineArea.numOfMachine }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.machineArea.competition')"></span>
          </dt>
          <dd>
            <div v-if="machineArea.competition">
              <router-link :to="{ name: 'CompetitionView', params: { competitionId: machineArea.competition.id } }">{{
                machineArea.competition.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="machineArea.id"
          :to="{ name: 'MachineAreaEdit', params: { machineAreaId: machineArea.id } }"
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

<script lang="ts" src="./machine-area-details.component.ts"></script>
