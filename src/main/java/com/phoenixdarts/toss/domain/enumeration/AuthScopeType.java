package com.phoenixdarts.toss.domain.enumeration;

/**
 * 권한범위
 */
public enum AuthScopeType {
    GLOBAL("전체국가대회"),
    COUNTRY("해당국가대회"),
    REGION("해당지역대회"),
    SHOP("해당Shop엔트리");

    private final String value;

    AuthScopeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
