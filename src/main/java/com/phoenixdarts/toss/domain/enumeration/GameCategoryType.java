package com.phoenixdarts.toss.backend.domain.enumeration;

/**
 * 게임카테고리
 */
public enum GameCategoryType {
    GAME_01("01 Game"),
    CRICKET("Cricket"),
    PRACTICE("Practice");

    private final String value;

    GameCategoryType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
