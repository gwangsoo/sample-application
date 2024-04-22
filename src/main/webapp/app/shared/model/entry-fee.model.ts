import { type ICurrency } from '@/shared/model/currency.model';

import { type EntryFeeType } from '@/shared/model/enumerations/entry-fee-type.model';
import { type EntryFeeSubType } from '@/shared/model/enumerations/entry-fee-sub-type.model';
import { type PaymentMethodType } from '@/shared/model/enumerations/payment-method-type.model';
export interface IEntryFee {
  id?: string;
  entryFeeType?: keyof typeof EntryFeeType;
  entryFeeSubType?: keyof typeof EntryFeeSubType | null;
  paymentMethodType?: keyof typeof PaymentMethodType | null;
  scheduleNumber?: number;
  fee?: number | null;
  currency?: ICurrency | null;
}

export class EntryFee implements IEntryFee {
  constructor(
    public id?: string,
    public entryFeeType?: keyof typeof EntryFeeType,
    public entryFeeSubType?: keyof typeof EntryFeeSubType | null,
    public paymentMethodType?: keyof typeof PaymentMethodType | null,
    public scheduleNumber?: number,
    public fee?: number | null,
    public currency?: ICurrency | null,
  ) {}
}
