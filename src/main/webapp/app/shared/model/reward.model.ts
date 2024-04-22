import { type RewardMethodType } from '@/shared/model/enumerations/reward-method-type.model';
import { type RewardMethodSubType } from '@/shared/model/enumerations/reward-method-sub-type.model';
import { type MachineKindType } from '@/shared/model/enumerations/machine-kind-type.model';
export interface IReward {
  id?: string;
  rewardMethodType?: keyof typeof RewardMethodType;
  rewardMethodSubType?: keyof typeof RewardMethodSubType;
  rewardCategoryNum?: number;
  machineKindType?: keyof typeof MachineKindType | null;
}

export class Reward implements IReward {
  constructor(
    public id?: string,
    public rewardMethodType?: keyof typeof RewardMethodType,
    public rewardMethodSubType?: keyof typeof RewardMethodSubType,
    public rewardCategoryNum?: number,
    public machineKindType?: keyof typeof MachineKindType | null,
  ) {}
}
