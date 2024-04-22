import { type ITeam } from '@/shared/model/team.model';

import { type PlayerCallModeType } from '@/shared/model/enumerations/player-call-mode-type.model';
export interface IMatchTeam {
  id?: string;
  isWinner?: boolean | null;
  avgPpd?: number | null;
  avgMpr?: number | null;
  winSet?: number | null;
  winLeg?: number | null;
  playerCallModeType?: keyof typeof PlayerCallModeType;
  team?: ITeam | null;
}

export class MatchTeam implements IMatchTeam {
  constructor(
    public id?: string,
    public isWinner?: boolean | null,
    public avgPpd?: number | null,
    public avgMpr?: number | null,
    public winSet?: number | null,
    public winLeg?: number | null,
    public playerCallModeType?: keyof typeof PlayerCallModeType,
    public team?: ITeam | null,
  ) {
    this.isWinner = this.isWinner ?? false;
  }
}
