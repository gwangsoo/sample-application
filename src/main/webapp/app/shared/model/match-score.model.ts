import { type IMatch } from '@/shared/model/match.model';

export interface IMatchScore {
  id?: string;
  setNo?: number | null;
  lgeNo?: number | null;
  legGameName?: string | null;
  homeLegScore?: number | null;
  awayLegScore?: number | null;
  match?: IMatch | null;
}

export class MatchScore implements IMatchScore {
  constructor(
    public id?: string,
    public setNo?: number | null,
    public lgeNo?: number | null,
    public legGameName?: string | null,
    public homeLegScore?: number | null,
    public awayLegScore?: number | null,
    public match?: IMatch | null,
  ) {}
}
