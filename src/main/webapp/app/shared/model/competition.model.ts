import { type IFileInfo } from '@/shared/model/file-info.model';
import { type IReward } from '@/shared/model/reward.model';
import { type ICountry } from '@/shared/model/country.model';
import { type IOperator } from '@/shared/model/operator.model';
import { type IEntryFee } from '@/shared/model/entry-fee.model';

import { type CompetitionStatus } from '@/shared/model/enumerations/competition-status.model';
import { type EntryApplyType } from '@/shared/model/enumerations/entry-apply-type.model';
import { type EntryRatingType } from '@/shared/model/enumerations/entry-rating-type.model';
export interface ICompetition {
  id?: string;
  name?: string;
  startDateTime?: Date;
  endDateTime?: Date;
  entryStartDateTime?: Date;
  entryEndDateTime?: Date;
  status?: keyof typeof CompetitionStatus;
  approval?: boolean;
  entryApplyType?: keyof typeof EntryApplyType;
  entryRatingType?: keyof typeof EntryRatingType;
  competitionImage?: IFileInfo | null;
  reward?: IReward | null;
  country?: ICountry | null;
  operator?: IOperator | null;
  entryFee?: IEntryFee | null;
}

export class Competition implements ICompetition {
  constructor(
    public id?: string,
    public name?: string,
    public startDateTime?: Date,
    public endDateTime?: Date,
    public entryStartDateTime?: Date,
    public entryEndDateTime?: Date,
    public status?: keyof typeof CompetitionStatus,
    public approval?: boolean,
    public entryApplyType?: keyof typeof EntryApplyType,
    public entryRatingType?: keyof typeof EntryRatingType,
    public competitionImage?: IFileInfo | null,
    public reward?: IReward | null,
    public country?: ICountry | null,
    public operator?: IOperator | null,
    public entryFee?: IEntryFee | null,
  ) {
    this.approval = this.approval ?? false;
  }
}
