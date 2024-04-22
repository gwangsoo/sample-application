import { type IEntryFee } from '@/shared/model/entry-fee.model';
import { type ICompetition } from '@/shared/model/competition.model';

import { type EntryApprovalType } from '@/shared/model/enumerations/entry-approval-type.model';
import { type TournamentType } from '@/shared/model/enumerations/tournament-type.model';
import { type SeedingRuleType } from '@/shared/model/enumerations/seeding-rule-type.model';
import { type TournamentPlayMode } from '@/shared/model/enumerations/tournament-play-mode.model';
import { type DivisionRuleType } from '@/shared/model/enumerations/division-rule-type.model';
import { type DivisionAssignMethod } from '@/shared/model/enumerations/division-assign-method.model';
import { type EntryGenderType } from '@/shared/model/enumerations/entry-gender-type.model';
import { type HandicapType } from '@/shared/model/enumerations/handicap-type.model';
export interface ITournament {
  id?: string;
  seq?: number;
  name?: string;
  day?: number;
  eventTournament?: boolean | null;
  entryClose?: boolean | null;
  entryApprovalType?: keyof typeof EntryApprovalType;
  tournamentType?: keyof typeof TournamentType;
  seedingRule?: keyof typeof SeedingRuleType;
  tournamentPlayMode?: keyof typeof TournamentPlayMode;
  thirdPlaceDecision?: boolean | null;
  divisionRule?: keyof typeof DivisionRuleType;
  entryLimit?: boolean;
  numOfEntry?: number;
  divisionAssignMethod?: keyof typeof DivisionAssignMethod;
  entryGenderType?: keyof typeof EntryGenderType;
  handicap?: keyof typeof HandicapType;
  entryFee?: IEntryFee | null;
  competition?: ICompetition | null;
}

export class Tournament implements ITournament {
  constructor(
    public id?: string,
    public seq?: number,
    public name?: string,
    public day?: number,
    public eventTournament?: boolean | null,
    public entryClose?: boolean | null,
    public entryApprovalType?: keyof typeof EntryApprovalType,
    public tournamentType?: keyof typeof TournamentType,
    public seedingRule?: keyof typeof SeedingRuleType,
    public tournamentPlayMode?: keyof typeof TournamentPlayMode,
    public thirdPlaceDecision?: boolean | null,
    public divisionRule?: keyof typeof DivisionRuleType,
    public entryLimit?: boolean,
    public numOfEntry?: number,
    public divisionAssignMethod?: keyof typeof DivisionAssignMethod,
    public entryGenderType?: keyof typeof EntryGenderType,
    public handicap?: keyof typeof HandicapType,
    public entryFee?: IEntryFee | null,
    public competition?: ICompetition | null,
  ) {
    this.eventTournament = this.eventTournament ?? false;
    this.entryClose = this.entryClose ?? false;
    this.thirdPlaceDecision = this.thirdPlaceDecision ?? false;
    this.entryLimit = this.entryLimit ?? false;
  }
}
