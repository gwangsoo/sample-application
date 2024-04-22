package com.phoenixdarts.toss.domain.enumeration;

/**
 * 라운드로빈 순위 결정 방식
 */
public enum RoundRobinRankingDecisionType {
    PERFECT("Perfect"),
    AMATEUR1("아마추어1"),
    AMATEUR2("아마추어2"),
    AMATEUR3("아마추어3"),
    AMATEUR4("아마추어4");

    private final String value;

    RoundRobinRankingDecisionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
