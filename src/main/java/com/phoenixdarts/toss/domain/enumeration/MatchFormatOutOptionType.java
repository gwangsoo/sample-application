package com.phoenixdarts.toss.backend.domain.enumeration;

/**
 * 매치포맷 Out Option
 */
public enum MatchFormatOutOptionType {
    OPEN("Open"),
    DOUBLE("Double"),
    MASTER("Master"),
    MASTER_BULL_0("Master Bull -&gt; 0"),
    MASTER_BULL_BUST("Master Bull -&gt; Bust"),
    MASTER_BULL_LOSE("Master Bull -&gt; Lose"),
    DOUBLE_BULL_0("Double Bull -&gt; 0"),
    DOUBLE_BULL_BUST("Double Bull -&gt; Bust"),
    DOUBLE_BULL_LOSE("Double Bull -&gt; Lose");

    private final String value;

    MatchFormatOutOptionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
