package com.phoenixdarts.toss.backend.domain.enumeration;

/**
 * 머신 종류
 */
public enum MachineKindType {
    VSS_S4("VSS[S4]"),
    VSX,
    VSX_MAX("VSX-Max");

    private String value;

    MachineKindType() {}

    MachineKindType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
