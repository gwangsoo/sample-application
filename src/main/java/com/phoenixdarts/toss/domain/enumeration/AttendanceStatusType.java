package com.phoenixdarts.toss.domain.enumeration;

/**
 * AttendanceStatusType
 */
public enum AttendanceStatusType {
    NON_ATTENDANCE("미출석"),
    GIVE_UP("기권"),
    ATTENDANCE("출석");

    private final String value;

    AttendanceStatusType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
