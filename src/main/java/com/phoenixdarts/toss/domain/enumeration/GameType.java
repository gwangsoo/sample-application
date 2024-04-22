package com.phoenixdarts.toss.domain.enumeration;

/**
 * 게임
 */
public enum GameType {
    GAME_301("301 Game"),
    GAME_501("501 Game"),
    GAME_701("701 Game"),
    GAME_901("901 Game"),
    GAME_1101("1101 Game"),
    GAME_151("1501 Game"),
    CRICKET_STANDARD("Standard Cricket"),
    CRICKET_CUT_THROAT("Cut-Throat Cricket"),
    COUNT_UP("Count-Up"),
    CRICKET_COUNT_UP("Cricket Count-Up"),
    BULL_SHOT("Bull Shot"),
    HALF_IT("Half-It");

    private final String value;

    GameType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
