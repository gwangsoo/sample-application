import { type IGame } from '@/shared/model/game.model';
import { type IMatchFormat } from '@/shared/model/match-format.model';

import { type GameCategoryType } from '@/shared/model/enumerations/game-category-type.model';
import { type GameType } from '@/shared/model/enumerations/game-type.model';
import { type MachineCreditType } from '@/shared/model/enumerations/machine-credit-type.model';
export interface IMatchFormatGame {
  id?: string;
  gameCategoryType?: keyof typeof GameCategoryType;
  gameType?: keyof typeof GameType;
  roundNum?: number;
  machineCreditType?: keyof typeof MachineCreditType;
  includingChoiceGame?: boolean | null;
  game?: IGame | null;
  matchFormat?: IMatchFormat | null;
}

export class MatchFormatGame implements IMatchFormatGame {
  constructor(
    public id?: string,
    public gameCategoryType?: keyof typeof GameCategoryType,
    public gameType?: keyof typeof GameType,
    public roundNum?: number,
    public machineCreditType?: keyof typeof MachineCreditType,
    public includingChoiceGame?: boolean | null,
    public game?: IGame | null,
    public matchFormat?: IMatchFormat | null,
  ) {
    this.includingChoiceGame = this.includingChoiceGame ?? false;
  }
}
