package com.hsms.domain.enums;

/**
 * Status of Movement
 */
public enum Status {
    NO_MOVEMENT(0),
    MOVEMENT_IN_FLOOR(1),
    MOVEMENT_IN_SUB_CORRIDOR(2),
    MOVEMENT_IN_FLOOR_AND_SUB_CORRIDOR(3);

    private final int value;

    Status(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
