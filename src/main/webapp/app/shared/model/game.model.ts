import { type GameCategoryType } from '@/shared/model/enumerations/game-category-type.model';
export interface IGame {
  id?: string;
  gameCategoryType?: keyof typeof GameCategoryType;
  name?: string;
  description?: string;
  roundNumDefault?: number | null;
  roundNumMin?: number | null;
  roundNumMax?: number | null;
  roundNumUnlimit?: number | null;
}

export class Game implements IGame {
  constructor(
    public id?: string,
    public gameCategoryType?: keyof typeof GameCategoryType,
    public name?: string,
    public description?: string,
    public roundNumDefault?: number | null,
    public roundNumMin?: number | null,
    public roundNumMax?: number | null,
    public roundNumUnlimit?: number | null,
  ) {}
}
