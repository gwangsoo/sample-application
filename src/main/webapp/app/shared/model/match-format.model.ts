import { type IDivision } from '@/shared/model/division.model';

import { type MatchFormatType } from '@/shared/model/enumerations/match-format-type.model';
import { type FirstThrowType } from '@/shared/model/enumerations/first-throw-type.model';
export interface IMatchFormat {
  id?: string;
  name?: string;
  description?: string;
  matchFormatType?: keyof typeof MatchFormatType;
  firstSet?: keyof typeof FirstThrowType | null;
  middleSet?: keyof typeof FirstThrowType | null;
  lastSet?: keyof typeof FirstThrowType | null;
  division?: IDivision | null;
}

export class MatchFormat implements IMatchFormat {
  constructor(
    public id?: string,
    public name?: string,
    public description?: string,
    public matchFormatType?: keyof typeof MatchFormatType,
    public firstSet?: keyof typeof FirstThrowType | null,
    public middleSet?: keyof typeof FirstThrowType | null,
    public lastSet?: keyof typeof FirstThrowType | null,
    public division?: IDivision | null,
  ) {}
}
