package com.phoenixdarts.toss.backend.domain.enumeration;

/**
 * Credit(별도 게임비용)
 */
public enum MachineCreditType {
    MACHINE_DEFAULT("Machine Default Credit"),
    FREE("Free"),
    NUM_1("1"),
    NUM_2("2"),
    NUM_3("3"),
    NUM_4("4"),
    NUM_5("5"),
    NUM_6("6"),
    NUM_7("7"),
    NUM_8("8"),
    NUM_9("9"),
    NUM_10("10"),
    NUM_11("11"),
    NUM_12("12"),
    NUM_13("13"),
    NUM_14("14"),
    NUM_15("15"),
    NUM_16("16"),
    NUM_17("17"),
    NUM_18("18"),
    NUM_19("19"),
    NUM_20("20");

    private final String value;

    MachineCreditType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
