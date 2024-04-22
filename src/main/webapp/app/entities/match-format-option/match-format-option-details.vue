<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="matchFormatOption">
        <h2 class="jh-entity-heading" data-cy="matchFormatOptionDetailsHeading">
          <span v-text="t$('tossApp.matchFormatOption.detail.title')"></span> {{ matchFormatOption.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('tossApp.matchFormatOption.game01InOptionType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.MatchFormatInOptionType.' + matchFormatOption.game01InOptionType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormatOption.game01OutOptionType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.MatchFormatOutOptionType.' + matchFormatOption.game01OutOptionType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormatOption.game01BullOptionType')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.MatchFormatBullOptionType.' + matchFormatOption.game01BullOptionType)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormatOption.game01Arrange')"></span>
          </dt>
          <dd>
            <span>{{ matchFormatOption.game01Arrange }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormatOption.cricketOverKill')"></span>
          </dt>
          <dd>
            <span>{{ matchFormatOption.cricketOverKill }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormatOption.cricketScore')"></span>
          </dt>
          <dd>
            <span>{{ matchFormatOption.cricketScore }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormatOption.teamGame01FreezeView')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.MatchFormatFreezeOptionType.' + matchFormatOption.teamGame01FreezeView)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormatOption.teamGame01Point')"></span>
          </dt>
          <dd>
            <span>{{ matchFormatOption.teamGame01Point }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormatOption.teamCricketMark')"></span>
          </dt>
          <dd>
            <span>{{ matchFormatOption.teamCricketMark }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormatOption.teamCricketFinish')"></span>
          </dt>
          <dd>
            <span v-text="t$('tossApp.MatchFormatTeamFinishOptionType.' + matchFormatOption.teamCricketFinish)"></span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormatOption.teamCricketPoint')"></span>
          </dt>
          <dd>
            <span>{{ matchFormatOption.teamCricketPoint }}</span>
          </dd>
          <dt>
            <span v-text="t$('tossApp.matchFormatOption.matchFormat')"></span>
          </dt>
          <dd>
            <div v-if="matchFormatOption.matchFormat">
              <router-link :to="{ name: 'MatchFormatView', params: { matchFormatId: matchFormatOption.matchFormat.id } }">{{
                matchFormatOption.matchFormat.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="matchFormatOption.id"
          :to="{ name: 'MatchFormatOptionEdit', params: { matchFormatOptionId: matchFormatOption.id } }"
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

<script lang="ts" src="./match-format-option-details.component.ts"></script>
