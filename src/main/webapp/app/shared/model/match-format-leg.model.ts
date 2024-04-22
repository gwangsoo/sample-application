import { type IMatchFormatOption } from '@/shared/model/match-format-option.model';
import { type IMatchFormatSet } from '@/shared/model/match-format-set.model';

import { type FirstThrowType } from '@/shared/model/enumerations/first-throw-type.model';
import { type LegPlayMode } from '@/shared/model/enumerations/leg-play-mode.model';
export interface IMatchFormatLeg {
  id?: string;
  seq?: number | null;
  firstThrowType?: keyof typeof FirstThrowType;
  playMode?: keyof typeof LegPlayMode | null;
  option?: IMatchFormatOption | null;
  matchFormatSet?: IMatchFormatSet | null;
}

export class MatchFormatLeg implements IMatchFormatLeg {
  constructor(
    public id?: string,
    public seq?: number | null,
    public firstThrowType?: keyof typeof FirstThrowType,
    public playMode?: keyof typeof LegPlayMode | null,
    public option?: IMatchFormatOption | null,
    public matchFormatSet?: IMatchFormatSet | null,
  ) {}
}
