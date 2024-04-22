import { type IMatchTeam } from '@/shared/model/match-team.model';

export interface IMatchCall {
  id?: string;
  callDateTime?: Date | null;
  callMessage?: string | null;
  matchTeam?: IMatchTeam | null;
}

export class MatchCall implements IMatchCall {
  constructor(
    public id?: string,
    public callDateTime?: Date | null,
    public callMessage?: string | null,
    public matchTeam?: IMatchTeam | null,
  ) {}
}
