import { type PaymentStatusType } from '@/shared/model/enumerations/payment-status-type.model';
import { type PaymentMethodType } from '@/shared/model/enumerations/payment-method-type.model';
export interface IPaymentInfo {
  id?: string;
  orderNumber?: string | null;
  paymentCompletedDate?: Date | null;
  status?: keyof typeof PaymentStatusType;
  paymentMethodType?: keyof typeof PaymentMethodType | null;
  pgTID?: number | null;
  pgStatus?: number | null;
  pgDetail?: string | null;
  payer?: string | null;
  payerPhone?: string | null;
}

export class PaymentInfo implements IPaymentInfo {
  constructor(
    public id?: string,
    public orderNumber?: string | null,
    public paymentCompletedDate?: Date | null,
    public status?: keyof typeof PaymentStatusType,
    public paymentMethodType?: keyof typeof PaymentMethodType | null,
    public pgTID?: number | null,
    public pgStatus?: number | null,
    public pgDetail?: string | null,
    public payer?: string | null,
    public payerPhone?: string | null,
  ) {}
}
