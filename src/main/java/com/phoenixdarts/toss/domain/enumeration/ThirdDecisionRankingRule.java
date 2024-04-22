package com.phoenixdarts.toss.domain.enumeration;

/**
 * 라운드로빈 3위 결승 진출 랭킹 기준
 */
public enum ThirdDecisionRankingRule {
    RATING("Rating"),
    PPD,
    MPR,
    WIN_LOSE("Win / Lose"),
    MANUAL_DECISION("Manual Decision");

    private String value;

    ThirdDecisionRankingRule() {}

    ThirdDecisionRankingRule(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
