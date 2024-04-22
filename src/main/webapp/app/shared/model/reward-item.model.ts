import { type IFileInfo } from '@/shared/model/file-info.model';
import { type IRewardDetail } from '@/shared/model/reward-detail.model';

export interface IRewardItem {
  id?: string;
  name?: string;
  itemImage?: IFileInfo | null;
  rewardDetail?: IRewardDetail | null;
}

export class RewardItem implements IRewardItem {
  constructor(
    public id?: string,
    public name?: string,
    public itemImage?: IFileInfo | null,
    public rewardDetail?: IRewardDetail | null,
  ) {}
}
