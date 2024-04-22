import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Currency = () => import('@/entities/currency/currency.vue');
const CurrencyUpdate = () => import('@/entities/currency/currency-update.vue');
const CurrencyDetails = () => import('@/entities/currency/currency-details.vue');

const FileInfo = () => import('@/entities/file-info/file-info.vue');
const FileInfoUpdate = () => import('@/entities/file-info/file-info-update.vue');
const FileInfoDetails = () => import('@/entities/file-info/file-info-details.vue');

const Country = () => import('@/entities/country/country.vue');
const CountryUpdate = () => import('@/entities/country/country-update.vue');
const CountryDetails = () => import('@/entities/country/country-details.vue');

const Region = () => import('@/entities/region/region.vue');
const RegionUpdate = () => import('@/entities/region/region-update.vue');
const RegionDetails = () => import('@/entities/region/region-details.vue');

const Role = () => import('@/entities/role/role.vue');
const RoleUpdate = () => import('@/entities/role/role-update.vue');
const RoleDetails = () => import('@/entities/role/role-details.vue');

const OperatorRole = () => import('@/entities/operator-role/operator-role.vue');
const OperatorRoleUpdate = () => import('@/entities/operator-role/operator-role-update.vue');
const OperatorRoleDetails = () => import('@/entities/operator-role/operator-role-details.vue');

const Operator = () => import('@/entities/operator/operator.vue');
const OperatorUpdate = () => import('@/entities/operator/operator-update.vue');
const OperatorDetails = () => import('@/entities/operator/operator-details.vue');

const Language = () => import('@/entities/language/language.vue');
const LanguageUpdate = () => import('@/entities/language/language-update.vue');
const LanguageDetails = () => import('@/entities/language/language-details.vue');

const EntryFee = () => import('@/entities/entry-fee/entry-fee.vue');
const EntryFeeUpdate = () => import('@/entities/entry-fee/entry-fee-update.vue');
const EntryFeeDetails = () => import('@/entities/entry-fee/entry-fee-details.vue');

const Competition = () => import('@/entities/competition/competition.vue');
const CompetitionUpdate = () => import('@/entities/competition/competition-update.vue');
const CompetitionDetails = () => import('@/entities/competition/competition-details.vue');

const Tournament = () => import('@/entities/tournament/tournament.vue');
const TournamentUpdate = () => import('@/entities/tournament/tournament-update.vue');
const TournamentDetails = () => import('@/entities/tournament/tournament-details.vue');

const Division = () => import('@/entities/division/division.vue');
const DivisionUpdate = () => import('@/entities/division/division-update.vue');
const DivisionDetails = () => import('@/entities/division/division-details.vue');

const Game = () => import('@/entities/game/game.vue');
const GameUpdate = () => import('@/entities/game/game-update.vue');
const GameDetails = () => import('@/entities/game/game-details.vue');

const MatchFormat = () => import('@/entities/match-format/match-format.vue');
const MatchFormatUpdate = () => import('@/entities/match-format/match-format-update.vue');
const MatchFormatDetails = () => import('@/entities/match-format/match-format-details.vue');

const MatchFormatGame = () => import('@/entities/match-format-game/match-format-game.vue');
const MatchFormatGameUpdate = () => import('@/entities/match-format-game/match-format-game-update.vue');
const MatchFormatGameDetails = () => import('@/entities/match-format-game/match-format-game-details.vue');

const MatchFormatOption = () => import('@/entities/match-format-option/match-format-option.vue');
const MatchFormatOptionUpdate = () => import('@/entities/match-format-option/match-format-option-update.vue');
const MatchFormatOptionDetails = () => import('@/entities/match-format-option/match-format-option-details.vue');

const MatchFormatSet = () => import('@/entities/match-format-set/match-format-set.vue');
const MatchFormatSetUpdate = () => import('@/entities/match-format-set/match-format-set-update.vue');
const MatchFormatSetDetails = () => import('@/entities/match-format-set/match-format-set-details.vue');

const MatchFormatLeg = () => import('@/entities/match-format-leg/match-format-leg.vue');
const MatchFormatLegUpdate = () => import('@/entities/match-format-leg/match-format-leg-update.vue');
const MatchFormatLegDetails = () => import('@/entities/match-format-leg/match-format-leg-details.vue');

const EventPoint = () => import('@/entities/event-point/event-point.vue');
const EventPointUpdate = () => import('@/entities/event-point/event-point-update.vue');
const EventPointDetails = () => import('@/entities/event-point/event-point-details.vue');

const AffiliatedInfo = () => import('@/entities/affiliated-info/affiliated-info.vue');
const AffiliatedInfoUpdate = () => import('@/entities/affiliated-info/affiliated-info-update.vue');
const AffiliatedInfoDetails = () => import('@/entities/affiliated-info/affiliated-info-details.vue');

const PaymentInfo = () => import('@/entities/payment-info/payment-info.vue');
const PaymentInfoUpdate = () => import('@/entities/payment-info/payment-info-update.vue');
const PaymentInfoDetails = () => import('@/entities/payment-info/payment-info-details.vue');

const Team = () => import('@/entities/team/team.vue');
const TeamUpdate = () => import('@/entities/team/team-update.vue');
const TeamDetails = () => import('@/entities/team/team-details.vue');

const Entry = () => import('@/entities/entry/entry.vue');
const EntryUpdate = () => import('@/entities/entry/entry-update.vue');
const EntryDetails = () => import('@/entities/entry/entry-details.vue');

const Match = () => import('@/entities/match/match.vue');
const MatchUpdate = () => import('@/entities/match/match-update.vue');
const MatchDetails = () => import('@/entities/match/match-details.vue');

const MatchTeam = () => import('@/entities/match-team/match-team.vue');
const MatchTeamUpdate = () => import('@/entities/match-team/match-team-update.vue');
const MatchTeamDetails = () => import('@/entities/match-team/match-team-details.vue');

const MatchScore = () => import('@/entities/match-score/match-score.vue');
const MatchScoreUpdate = () => import('@/entities/match-score/match-score-update.vue');
const MatchScoreDetails = () => import('@/entities/match-score/match-score-details.vue');

const MatchAttendance = () => import('@/entities/match-attendance/match-attendance.vue');
const MatchAttendanceUpdate = () => import('@/entities/match-attendance/match-attendance-update.vue');
const MatchAttendanceDetails = () => import('@/entities/match-attendance/match-attendance-details.vue');

const MatchCall = () => import('@/entities/match-call/match-call.vue');
const MatchCallUpdate = () => import('@/entities/match-call/match-call-update.vue');
const MatchCallDetails = () => import('@/entities/match-call/match-call-details.vue');

const MachineArea = () => import('@/entities/machine-area/machine-area.vue');
const MachineAreaUpdate = () => import('@/entities/machine-area/machine-area-update.vue');
const MachineAreaDetails = () => import('@/entities/machine-area/machine-area-details.vue');

const Machine = () => import('@/entities/machine/machine.vue');
const MachineUpdate = () => import('@/entities/machine/machine-update.vue');
const MachineDetails = () => import('@/entities/machine/machine-details.vue');

const Reward = () => import('@/entities/reward/reward.vue');
const RewardUpdate = () => import('@/entities/reward/reward-update.vue');
const RewardDetails = () => import('@/entities/reward/reward-details.vue');

const RewardDetail = () => import('@/entities/reward-detail/reward-detail.vue');
const RewardDetailUpdate = () => import('@/entities/reward-detail/reward-detail-update.vue');
const RewardDetailDetails = () => import('@/entities/reward-detail/reward-detail-details.vue');

const RewardItem = () => import('@/entities/reward-item/reward-item.vue');
const RewardItemUpdate = () => import('@/entities/reward-item/reward-item-update.vue');
const RewardItemDetails = () => import('@/entities/reward-item/reward-item-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'currency',
      name: 'Currency',
      component: Currency,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'currency/new',
      name: 'CurrencyCreate',
      component: CurrencyUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'currency/:currencyId/edit',
      name: 'CurrencyEdit',
      component: CurrencyUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'currency/:currencyId/view',
      name: 'CurrencyView',
      component: CurrencyDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'file-info',
      name: 'FileInfo',
      component: FileInfo,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'file-info/new',
      name: 'FileInfoCreate',
      component: FileInfoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'file-info/:fileInfoId/edit',
      name: 'FileInfoEdit',
      component: FileInfoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'file-info/:fileInfoId/view',
      name: 'FileInfoView',
      component: FileInfoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country',
      name: 'Country',
      component: Country,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country/new',
      name: 'CountryCreate',
      component: CountryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country/:countryId/edit',
      name: 'CountryEdit',
      component: CountryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country/:countryId/view',
      name: 'CountryView',
      component: CountryDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'region',
      name: 'Region',
      component: Region,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'region/new',
      name: 'RegionCreate',
      component: RegionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'region/:regionId/edit',
      name: 'RegionEdit',
      component: RegionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'region/:regionId/view',
      name: 'RegionView',
      component: RegionDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'role',
      name: 'Role',
      component: Role,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'role/new',
      name: 'RoleCreate',
      component: RoleUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'role/:roleId/edit',
      name: 'RoleEdit',
      component: RoleUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'role/:roleId/view',
      name: 'RoleView',
      component: RoleDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'operator-role',
      name: 'OperatorRole',
      component: OperatorRole,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'operator-role/new',
      name: 'OperatorRoleCreate',
      component: OperatorRoleUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'operator-role/:operatorRoleId/edit',
      name: 'OperatorRoleEdit',
      component: OperatorRoleUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'operator-role/:operatorRoleId/view',
      name: 'OperatorRoleView',
      component: OperatorRoleDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'operator',
      name: 'Operator',
      component: Operator,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'operator/new',
      name: 'OperatorCreate',
      component: OperatorUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'operator/:operatorId/edit',
      name: 'OperatorEdit',
      component: OperatorUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'operator/:operatorId/view',
      name: 'OperatorView',
      component: OperatorDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'language',
      name: 'Language',
      component: Language,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'language/new',
      name: 'LanguageCreate',
      component: LanguageUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'language/:languageId/edit',
      name: 'LanguageEdit',
      component: LanguageUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'language/:languageId/view',
      name: 'LanguageView',
      component: LanguageDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'entry-fee',
      name: 'EntryFee',
      component: EntryFee,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'entry-fee/new',
      name: 'EntryFeeCreate',
      component: EntryFeeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'entry-fee/:entryFeeId/edit',
      name: 'EntryFeeEdit',
      component: EntryFeeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'entry-fee/:entryFeeId/view',
      name: 'EntryFeeView',
      component: EntryFeeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'competition',
      name: 'Competition',
      component: Competition,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'competition/new',
      name: 'CompetitionCreate',
      component: CompetitionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'competition/:competitionId/edit',
      name: 'CompetitionEdit',
      component: CompetitionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'competition/:competitionId/view',
      name: 'CompetitionView',
      component: CompetitionDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tournament',
      name: 'Tournament',
      component: Tournament,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tournament/new',
      name: 'TournamentCreate',
      component: TournamentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tournament/:tournamentId/edit',
      name: 'TournamentEdit',
      component: TournamentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tournament/:tournamentId/view',
      name: 'TournamentView',
      component: TournamentDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'division',
      name: 'Division',
      component: Division,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'division/new',
      name: 'DivisionCreate',
      component: DivisionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'division/:divisionId/edit',
      name: 'DivisionEdit',
      component: DivisionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'division/:divisionId/view',
      name: 'DivisionView',
      component: DivisionDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'game',
      name: 'Game',
      component: Game,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'game/new',
      name: 'GameCreate',
      component: GameUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'game/:gameId/edit',
      name: 'GameEdit',
      component: GameUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'game/:gameId/view',
      name: 'GameView',
      component: GameDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-format',
      name: 'MatchFormat',
      component: MatchFormat,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-format/new',
      name: 'MatchFormatCreate',
      component: MatchFormatUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-format/:matchFormatId/edit',
      name: 'MatchFormatEdit',
      component: MatchFormatUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-format/:matchFormatId/view',
      name: 'MatchFormatView',
      component: MatchFormatDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-format-game',
      name: 'MatchFormatGame',
      component: MatchFormatGame,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-format-game/new',
      name: 'MatchFormatGameCreate',
      component: MatchFormatGameUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-format-game/:matchFormatGameId/edit',
      name: 'MatchFormatGameEdit',
      component: MatchFormatGameUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-format-game/:matchFormatGameId/view',
      name: 'MatchFormatGameView',
      component: MatchFormatGameDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-format-option',
      name: 'MatchFormatOption',
      component: MatchFormatOption,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-format-option/new',
      name: 'MatchFormatOptionCreate',
      component: MatchFormatOptionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-format-option/:matchFormatOptionId/edit',
      name: 'MatchFormatOptionEdit',
      component: MatchFormatOptionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-format-option/:matchFormatOptionId/view',
      name: 'MatchFormatOptionView',
      component: MatchFormatOptionDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-format-set',
      name: 'MatchFormatSet',
      component: MatchFormatSet,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-format-set/new',
      name: 'MatchFormatSetCreate',
      component: MatchFormatSetUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-format-set/:matchFormatSetId/edit',
      name: 'MatchFormatSetEdit',
      component: MatchFormatSetUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-format-set/:matchFormatSetId/view',
      name: 'MatchFormatSetView',
      component: MatchFormatSetDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-format-leg',
      name: 'MatchFormatLeg',
      component: MatchFormatLeg,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-format-leg/new',
      name: 'MatchFormatLegCreate',
      component: MatchFormatLegUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-format-leg/:matchFormatLegId/edit',
      name: 'MatchFormatLegEdit',
      component: MatchFormatLegUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-format-leg/:matchFormatLegId/view',
      name: 'MatchFormatLegView',
      component: MatchFormatLegDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'event-point',
      name: 'EventPoint',
      component: EventPoint,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'event-point/new',
      name: 'EventPointCreate',
      component: EventPointUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'event-point/:eventPointId/edit',
      name: 'EventPointEdit',
      component: EventPointUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'event-point/:eventPointId/view',
      name: 'EventPointView',
      component: EventPointDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'affiliated-info',
      name: 'AffiliatedInfo',
      component: AffiliatedInfo,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'affiliated-info/new',
      name: 'AffiliatedInfoCreate',
      component: AffiliatedInfoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'affiliated-info/:affiliatedInfoId/edit',
      name: 'AffiliatedInfoEdit',
      component: AffiliatedInfoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'affiliated-info/:affiliatedInfoId/view',
      name: 'AffiliatedInfoView',
      component: AffiliatedInfoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment-info',
      name: 'PaymentInfo',
      component: PaymentInfo,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment-info/new',
      name: 'PaymentInfoCreate',
      component: PaymentInfoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment-info/:paymentInfoId/edit',
      name: 'PaymentInfoEdit',
      component: PaymentInfoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment-info/:paymentInfoId/view',
      name: 'PaymentInfoView',
      component: PaymentInfoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'team',
      name: 'Team',
      component: Team,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'team/new',
      name: 'TeamCreate',
      component: TeamUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'team/:teamId/edit',
      name: 'TeamEdit',
      component: TeamUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'team/:teamId/view',
      name: 'TeamView',
      component: TeamDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'entry',
      name: 'Entry',
      component: Entry,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'entry/new',
      name: 'EntryCreate',
      component: EntryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'entry/:entryId/edit',
      name: 'EntryEdit',
      component: EntryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'entry/:entryId/view',
      name: 'EntryView',
      component: EntryDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match',
      name: 'Match',
      component: Match,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match/new',
      name: 'MatchCreate',
      component: MatchUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match/:matchId/edit',
      name: 'MatchEdit',
      component: MatchUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match/:matchId/view',
      name: 'MatchView',
      component: MatchDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-team',
      name: 'MatchTeam',
      component: MatchTeam,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-team/new',
      name: 'MatchTeamCreate',
      component: MatchTeamUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-team/:matchTeamId/edit',
      name: 'MatchTeamEdit',
      component: MatchTeamUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-team/:matchTeamId/view',
      name: 'MatchTeamView',
      component: MatchTeamDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-score',
      name: 'MatchScore',
      component: MatchScore,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-score/new',
      name: 'MatchScoreCreate',
      component: MatchScoreUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-score/:matchScoreId/edit',
      name: 'MatchScoreEdit',
      component: MatchScoreUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-score/:matchScoreId/view',
      name: 'MatchScoreView',
      component: MatchScoreDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-attendance',
      name: 'MatchAttendance',
      component: MatchAttendance,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-attendance/new',
      name: 'MatchAttendanceCreate',
      component: MatchAttendanceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-attendance/:matchAttendanceId/edit',
      name: 'MatchAttendanceEdit',
      component: MatchAttendanceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-attendance/:matchAttendanceId/view',
      name: 'MatchAttendanceView',
      component: MatchAttendanceDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-call',
      name: 'MatchCall',
      component: MatchCall,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-call/new',
      name: 'MatchCallCreate',
      component: MatchCallUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-call/:matchCallId/edit',
      name: 'MatchCallEdit',
      component: MatchCallUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match-call/:matchCallId/view',
      name: 'MatchCallView',
      component: MatchCallDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'machine-area',
      name: 'MachineArea',
      component: MachineArea,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'machine-area/new',
      name: 'MachineAreaCreate',
      component: MachineAreaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'machine-area/:machineAreaId/edit',
      name: 'MachineAreaEdit',
      component: MachineAreaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'machine-area/:machineAreaId/view',
      name: 'MachineAreaView',
      component: MachineAreaDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'machine',
      name: 'Machine',
      component: Machine,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'machine/new',
      name: 'MachineCreate',
      component: MachineUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'machine/:machineId/edit',
      name: 'MachineEdit',
      component: MachineUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'machine/:machineId/view',
      name: 'MachineView',
      component: MachineDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'reward',
      name: 'Reward',
      component: Reward,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'reward/new',
      name: 'RewardCreate',
      component: RewardUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'reward/:rewardId/edit',
      name: 'RewardEdit',
      component: RewardUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'reward/:rewardId/view',
      name: 'RewardView',
      component: RewardDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'reward-detail',
      name: 'RewardDetail',
      component: RewardDetail,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'reward-detail/new',
      name: 'RewardDetailCreate',
      component: RewardDetailUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'reward-detail/:rewardDetailId/edit',
      name: 'RewardDetailEdit',
      component: RewardDetailUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'reward-detail/:rewardDetailId/view',
      name: 'RewardDetailView',
      component: RewardDetailDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'reward-item',
      name: 'RewardItem',
      component: RewardItem,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'reward-item/new',
      name: 'RewardItemCreate',
      component: RewardItemUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'reward-item/:rewardItemId/edit',
      name: 'RewardItemEdit',
      component: RewardItemUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'reward-item/:rewardItemId/view',
      name: 'RewardItemView',
      component: RewardItemDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
