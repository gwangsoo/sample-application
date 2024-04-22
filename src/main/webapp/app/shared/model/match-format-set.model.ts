import { type IMatchFormat } from '@/shared/model/match-format.model';

export interface IMatchFormatSet {
  id?: string;
  point?: number;
  matchFormat?: IMatchFormat | null;
}

export class MatchFormatSet implements IMatchFormatSet {
  constructor(
    public id?: string,
    public point?: number,
    public matchFormat?: IMatchFormat | null,
  ) {}
}
