import { type IMatchFormat } from '@/shared/model/match-format.model';

import { type MatchFormatInOptionType } from '@/shared/model/enumerations/match-format-in-option-type.model';
import { type MatchFormatOutOptionType } from '@/shared/model/enumerations/match-format-out-option-type.model';
import { type MatchFormatBullOptionType } from '@/shared/model/enumerations/match-format-bull-option-type.model';
import { type MatchFormatFreezeOptionType } from '@/shared/model/enumerations/match-format-freeze-option-type.model';
import { type MatchFormatTeamFinishOptionType } from '@/shared/model/enumerations/match-format-team-finish-option-type.model';
export interface IMatchFormatOption {
  id?: string;
  game01InOptionType?: keyof typeof MatchFormatInOptionType | null;
  game01OutOptionType?: keyof typeof MatchFormatOutOptionType | null;
  game01BullOptionType?: keyof typeof MatchFormatBullOptionType | null;
  game01Arrange?: boolean | null;
  cricketOverKill?: boolean | null;
  cricketScore?: number | null;
  teamGame01FreezeView?: keyof typeof MatchFormatFreezeOptionType | null;
  teamGame01Point?: boolean | null;
  teamCricketMark?: boolean | null;
  teamCricketFinish?: keyof typeof MatchFormatTeamFinishOptionType | null;
  teamCricketPoint?: boolean | null;
  matchFormat?: IMatchFormat | null;
}

export class MatchFormatOption implements IMatchFormatOption {
  constructor(
    public id?: string,
    public game01InOptionType?: keyof typeof MatchFormatInOptionType | null,
    public game01OutOptionType?: keyof typeof MatchFormatOutOptionType | null,
    public game01BullOptionType?: keyof typeof MatchFormatBullOptionType | null,
    public game01Arrange?: boolean | null,
    public cricketOverKill?: boolean | null,
    public cricketScore?: number | null,
    public teamGame01FreezeView?: keyof typeof MatchFormatFreezeOptionType | null,
    public teamGame01Point?: boolean | null,
    public teamCricketMark?: boolean | null,
    public teamCricketFinish?: keyof typeof MatchFormatTeamFinishOptionType | null,
    public teamCricketPoint?: boolean | null,
    public matchFormat?: IMatchFormat | null,
  ) {
    this.game01Arrange = this.game01Arrange ?? false;
    this.cricketOverKill = this.cricketOverKill ?? false;
    this.teamGame01Point = this.teamGame01Point ?? false;
    this.teamCricketMark = this.teamCricketMark ?? false;
    this.teamCricketPoint = this.teamCricketPoint ?? false;
  }
}
