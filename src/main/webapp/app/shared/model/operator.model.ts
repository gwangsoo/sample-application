import { type IOperatorRole } from '@/shared/model/operator-role.model';
import { type IRegion } from '@/shared/model/region.model';

export interface IOperator {
  id?: string;
  userId?: string;
  userName?: string;
  phone?: string | null;
  email?: string | null;
  address?: string | null;
  approvalStatus?: boolean;
  operatorRole?: IOperatorRole | null;
  region?: IRegion | null;
}

export class Operator implements IOperator {
  constructor(
    public id?: string,
    public userId?: string,
    public userName?: string,
    public phone?: string | null,
    public email?: string | null,
    public address?: string | null,
    public approvalStatus?: boolean,
    public operatorRole?: IOperatorRole | null,
    public region?: IRegion | null,
  ) {
    this.approvalStatus = this.approvalStatus ?? false;
  }
}
