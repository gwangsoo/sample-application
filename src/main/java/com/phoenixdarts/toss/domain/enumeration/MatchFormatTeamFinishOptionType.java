package com.phoenixdarts.toss.domain.enumeration;

/**
 * 매치포맷 Team Finish Option
 */
public enum MatchFormatTeamFinishOptionType {
    ONE_PLAYER_ONLY("1Player Only"),
    ALL_PLAYER("1P &amp; 2P");

    private final String value;

    MatchFormatTeamFinishOptionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
