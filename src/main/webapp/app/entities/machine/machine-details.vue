<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="machine">
        <h2 class="jh-entity-heading" data-cy="machineDetailsHeading">
          <span v-text="t$('tossApp.machine.detail.title')"></span> {{ machine.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.machine.machineNo')"></span>
          </dt>
          <dd>
            <span>{{ machine.machineNo }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.machine.machineStatusType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.MachineStatusType.' + machine.machineStatusType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.machine.match')"></span>
          </dt>
          <dd>
            <div v-if="machine.match">
              <router-link :to="{ name: 'MatchView', params: { matchId: machine.match.id } }">{{ machine.match.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('tossApp.machine.machineArea')"></span>
          </dt>
          <dd>
            <div v-if="machine.machineArea">
              <router-link :to="{ name: 'MachineAreaView', params: { machineAreaId: machine.machineArea.id } }">{{
                machine.machineArea.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="machine.id" :to="{ name: 'MachineEdit', params: { machineId: machine.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./machine-details.component.ts"></script>
