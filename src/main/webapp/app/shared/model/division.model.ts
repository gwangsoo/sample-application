import { type ITournament } from '@/shared/model/tournament.model';

import { type RoundRobinRankingDecisionType } from '@/shared/model/enumerations/round-robin-ranking-decision-type.model';
import { type RoundRobinGroupType } from '@/shared/model/enumerations/round-robin-group-type.model';
import { type NextRoundDecisionType } from '@/shared/model/enumerations/next-round-decision-type.model';
import { type ThirdDecisionRankingRule } from '@/shared/model/enumerations/third-decision-ranking-rule.model';
import { type EventRangeType } from '@/shared/model/enumerations/event-range-type.model';
export interface IDivision {
  id?: string;
  seq?: number;
  name?: string;
  ratingRuleTeamMin?: number | null;
  ratingRuleTeamMax?: number | null;
  ratingRuleIndividualLimit?: boolean | null;
  ratingRuleIndividualMin?: number | null;
  ratingRuleIndividualMax?: number | null;
  entryLimit?: boolean;
  roundRobinRankingDecisionType?: keyof typeof RoundRobinRankingDecisionType | null;
  roundRobinGroupType?: keyof typeof RoundRobinGroupType | null;
  nextRoundDecisionType?: keyof typeof NextRoundDecisionType | null;
  roundRoginThirdDecision?: boolean | null;
  thirdDecisionRankingRule?: keyof typeof ThirdDecisionRankingRule | null;
  useAllRoundSameFormat?: boolean | null;
  eventRangeType?: keyof typeof EventRangeType | null;
  eliminationTeamCount?: number | null;
  tournament?: ITournament | null;
}

export class Division implements IDivision {
  constructor(
    public id?: string,
    public seq?: number,
    public name?: string,
    public ratingRuleTeamMin?: number | null,
    public ratingRuleTeamMax?: number | null,
    public ratingRuleIndividualLimit?: boolean | null,
    public ratingRuleIndividualMin?: number | null,
    public ratingRuleIndividualMax?: number | null,
    public entryLimit?: boolean,
    public roundRobinRankingDecisionType?: keyof typeof RoundRobinRankingDecisionType | null,
    public roundRobinGroupType?: keyof typeof RoundRobinGroupType | null,
    public nextRoundDecisionType?: keyof typeof NextRoundDecisionType | null,
    public roundRoginThirdDecision?: boolean | null,
    public thirdDecisionRankingRule?: keyof typeof ThirdDecisionRankingRule | null,
    public useAllRoundSameFormat?: boolean | null,
    public eventRangeType?: keyof typeof EventRangeType | null,
    public eliminationTeamCount?: number | null,
    public tournament?: ITournament | null,
  ) {
    this.ratingRuleIndividualLimit = this.ratingRuleIndividualLimit ?? false;
    this.entryLimit = this.entryLimit ?? false;
    this.roundRoginThirdDecision = this.roundRoginThirdDecision ?? false;
    this.useAllRoundSameFormat = this.useAllRoundSameFormat ?? false;
  }
}
