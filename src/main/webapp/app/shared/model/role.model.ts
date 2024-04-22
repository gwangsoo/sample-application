import { type IOperatorRole } from '@/shared/model/operator-role.model';

import { type AuthScopeType } from '@/shared/model/enumerations/auth-scope-type.model';
import { type AuthLevelType } from '@/shared/model/enumerations/auth-level-type.model';
export interface IRole {
  id?: string;
  name?: string;
  authScopeType?: keyof typeof AuthScopeType;
  authLevelType?: keyof typeof AuthLevelType;
  displayOrder?: number;
  operatorRole?: IOperatorRole | null;
}

export class Role implements IRole {
  constructor(
    public id?: string,
    public name?: string,
    public authScopeType?: keyof typeof AuthScopeType,
    public authLevelType?: keyof typeof AuthLevelType,
    public displayOrder?: number,
    public operatorRole?: IOperatorRole | null,
  ) {}
}
