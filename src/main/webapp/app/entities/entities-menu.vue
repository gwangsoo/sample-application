<template>
  <div>
    <b-dropdown-item to="/currency">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.currency')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/file-info">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.fileInfo')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/country">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.country')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/region">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.region')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/auth">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.auth')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/operator-auth">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.operatorRole')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/operator">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.operator')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/language">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.language')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/entry-fee">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.entryFee')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/competition">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.competition')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/tournament">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.tournament')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/division">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.division')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/game">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.game')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/match-format">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.matchFormat')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/match-format-game">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.matchFormatGame')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/match-format-option">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.matchFormatOption')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/match-format-set">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.matchFormatSet')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/match-format-leg">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.matchFormatLeg')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/event-point">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.eventPoint')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/affiliated-info">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.affiliatedInfo')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/payment-info">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.paymentInfo')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/team">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.team')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/entry">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.entry')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/match">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.match')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/match-team">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.matchTeam')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/match-score">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.matchScore')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/match-attendance">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.matchAttendance')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/match-call">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.matchCall')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/machine-area">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.machineArea')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/machine">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.machine')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/reward">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.reward')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/reward-detail">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.rewardDetail')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/reward-item">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.rewardItem')"></span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
