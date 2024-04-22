import { type IReward } from '@/shared/model/reward.model';

export interface IRewardDetail {
  id?: string;
  name?: string | null;
  tournamentId?: string | null;
  divisionId?: string | null;
  reward?: IReward | null;
}

export class RewardDetail implements IRewardDetail {
  constructor(
    public id?: string,
    public name?: string | null,
    public tournamentId?: string | null,
    public divisionId?: string | null,
    public reward?: IReward | null,
  ) {}
}
