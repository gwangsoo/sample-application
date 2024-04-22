<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="entry">
        <h2 class="jh-entity-heading" data-cy="entryDetailsHeading">
          <span v-text="t$('tossApp.entry.detail.title')"></span> {{ entry.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.entry.entryNo')"></span>
          </dt>
          <dd>
            <span>{{ entry.entryNo }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.entry.phoenixNo')"></span>
          </dt>
          <dd>
            <span>{{ entry.phoenixNo }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.entry.cardNo')"></span>
          </dt>
          <dd>
            <span>{{ entry.cardNo }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.entry.name')"></span>
          </dt>
          <dd>
            <span>{{ entry.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.entry.englishName')"></span>
          </dt>
          <dd>
            <span>{{ entry.englishName }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.entry.rating')"></span>
          </dt>
          <dd>
            <span>{{ entry.rating }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.entry.mobileNo')"></span>
          </dt>
          <dd>
            <span>{{ entry.mobileNo }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.entry.birthDate')"></span>
          </dt>
          <dd>
            <span>{{ entry.birthDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.entry.email')"></span>
          </dt>
          <dd>
            <span>{{ entry.email }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.entry.genderType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.GenderType.' + entry.genderType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.entry.attendanceStatusType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.AttendanceStatusType.' + entry.attendanceStatusType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.entry.team')"></span>
          </dt>
          <dd>
            <div v-if="entry.team">
              <router-link :to="{ name: 'TeamView', params: { teamId: entry.team.id } }">{{ entry.team.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="entry.id" :to="{ name: 'EntryEdit', params: { entryId: entry.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./entry-details.component.ts"></script>
