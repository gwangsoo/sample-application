import { defineComponent, provide } from 'vue';

import CurrencyService from './currency/currency.service';
import FileInfoService from './file-info/file-info.service';
import CountryService from './country/country.service';
import RegionService from './region/region.service';
import RoleService from './role/role.service';
import OperatorRoleService from './operator-role/operator-role.service';
import OperatorService from './operator/operator.service';
import LanguageService from './language/language.service';
import EntryFeeService from './entry-fee/entry-fee.service';
import CompetitionService from './competition/competition.service';
import TournamentService from './tournament/tournament.service';
import DivisionService from './division/division.service';
import GameService from './game/game.service';
import MatchFormatService from './match-format/match-format.service';
import MatchFormatGameService from './match-format-game/match-format-game.service';
import MatchFormatOptionService from './match-format-option/match-format-option.service';
import MatchFormatSetService from './match-format-set/match-format-set.service';
import MatchFormatLegService from './match-format-leg/match-format-leg.service';
import EventPointService from './event-point/event-point.service';
import AffiliatedInfoService from './affiliated-info/affiliated-info.service';
import PaymentInfoService from './payment-info/payment-info.service';
import TeamService from './team/team.service';
import EntryService from './entry/entry.service';
import MatchService from './match/match.service';
import MatchTeamService from './match-team/match-team.service';
import MatchScoreService from './match-score/match-score.service';
import MatchAttendanceService from './match-attendance/match-attendance.service';
import MatchCallService from './match-call/match-call.service';
import MachineAreaService from './machine-area/machine-area.service';
import MachineService from './machine/machine.service';
import RewardService from './reward/reward.service';
import RewardDetailService from './reward-detail/reward-detail.service';
import RewardItemService from './reward-item/reward-item.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('currencyService', () => new CurrencyService());
    provide('fileInfoService', () => new FileInfoService());
    provide('countryService', () => new CountryService());
    provide('regionService', () => new RegionService());
    provide('roleService', () => new RoleService());
    provide('operatorRoleService', () => new OperatorRoleService());
    provide('operatorService', () => new OperatorService());
    provide('languageService', () => new LanguageService());
    provide('entryFeeService', () => new EntryFeeService());
    provide('competitionService', () => new CompetitionService());
    provide('tournamentService', () => new TournamentService());
    provide('divisionService', () => new DivisionService());
    provide('gameService', () => new GameService());
    provide('matchFormatService', () => new MatchFormatService());
    provide('matchFormatGameService', () => new MatchFormatGameService());
    provide('matchFormatOptionService', () => new MatchFormatOptionService());
    provide('matchFormatSetService', () => new MatchFormatSetService());
    provide('matchFormatLegService', () => new MatchFormatLegService());
    provide('eventPointService', () => new EventPointService());
    provide('affiliatedInfoService', () => new AffiliatedInfoService());
    provide('paymentInfoService', () => new PaymentInfoService());
    provide('teamService', () => new TeamService());
    provide('entryService', () => new EntryService());
    provide('matchService', () => new MatchService());
    provide('matchTeamService', () => new MatchTeamService());
    provide('matchScoreService', () => new MatchScoreService());
    provide('matchAttendanceService', () => new MatchAttendanceService());
    provide('matchCallService', () => new MatchCallService());
    provide('machineAreaService', () => new MachineAreaService());
    provide('machineService', () => new MachineService());
    provide('rewardService', () => new RewardService());
    provide('rewardDetailService', () => new RewardDetailService());
    provide('rewardItemService', () => new RewardItemService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
