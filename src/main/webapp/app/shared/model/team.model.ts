import { type IEntry } from '@/shared/model/entry.model';
import { type IDivision } from '@/shared/model/division.model';
import { type IAffiliatedInfo } from '@/shared/model/affiliated-info.model';
import { type IPaymentInfo } from '@/shared/model/payment-info.model';

import { type EntryApprovalStatusType } from '@/shared/model/enumerations/entry-approval-status-type.model';
export interface ITeam {
  id?: string;
  teamNo?: string | null;
  approvalStatus?: keyof typeof EntryApprovalStatusType;
  entryDate?: Date | null;
  memo?: string | null;
  groupNo?: number | null;
  groupSeq?: number | null;
  seedNo?: number | null;
  captain?: IEntry | null;
  tteam?: IDivision | null;
  affiliatedInfo?: IAffiliatedInfo | null;
  paymentInfo?: IPaymentInfo | null;
  division?: IDivision | null;
}

export class Team implements ITeam {
  constructor(
    public id?: string,
    public teamNo?: string | null,
    public approvalStatus?: keyof typeof EntryApprovalStatusType,
    public entryDate?: Date | null,
    public memo?: string | null,
    public groupNo?: number | null,
    public groupSeq?: number | null,
    public seedNo?: number | null,
    public captain?: IEntry | null,
    public tteam?: IDivision | null,
    public affiliatedInfo?: IAffiliatedInfo | null,
    public paymentInfo?: IPaymentInfo | null,
    public division?: IDivision | null,
  ) {}
}
