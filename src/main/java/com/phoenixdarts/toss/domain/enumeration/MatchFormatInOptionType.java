package com.phoenixdarts.toss.domain.enumeration;

/**
 * 매치포맷 In Option
 */
public enum MatchFormatInOptionType {
    OPEN("Open"),
    DOUBLE("Double"),
    TRIPLE("Triple"),
    MASTER("Master"),
    MASTER_BULL_0("Master Bull -&gt; 0"),
    MASTER_BULL_BUST("Master Bull -&gt; Bust"),
    MASTER_BULL_LOSE("Master Bull -&gt; Lose"),
    DOUBLE_BULL_0("Double Bull -&gt; 0"),
    DOUBLE_BULL_BUST("Double Bull -&gt; Bust"),
    DOUBLE_BULL_LOSE("Double Bull -&gt; Lose");

    private final String value;

    MatchFormatInOptionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
