import { type IMatchTeam } from '@/shared/model/match-team.model';
import { type IMachine } from '@/shared/model/machine.model';
import { type IDivision } from '@/shared/model/division.model';

import { type MatchType } from '@/shared/model/enumerations/match-type.model';
import { type MatchStatus } from '@/shared/model/enumerations/match-status.model';
export interface IMatch {
  id?: string;
  matchNo?: string;
  matchType?: keyof typeof MatchType;
  groupNo?: number | null;
  groupMatchSeq?: number | null;
  roundNum?: number | null;
  matchStatus?: keyof typeof MatchStatus;
  nextMatchNo?: string | null;
  home?: IMatchTeam | null;
  away?: IMatchTeam | null;
  tmatch?: IMachine | null;
  division?: IDivision | null;
}

export class Match implements IMatch {
  constructor(
    public id?: string,
    public matchNo?: string,
    public matchType?: keyof typeof MatchType,
    public groupNo?: number | null,
    public groupMatchSeq?: number | null,
    public roundNum?: number | null,
    public matchStatus?: keyof typeof MatchStatus,
    public nextMatchNo?: string | null,
    public home?: IMatchTeam | null,
    public away?: IMatchTeam | null,
    public tmatch?: IMachine | null,
    public division?: IDivision | null,
  ) {}
}
