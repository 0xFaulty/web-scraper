package com.defaulty.webscraper.control.task;

/**
 * Enum provides compact storage application flags, based on binary number.
 */
public enum TaskFlags {
    V(0b0001), W(0b0010), C(0b0100), E(0b1000);

    private int value;

    TaskFlags(int value) {
        this.value = value;
    }

    boolean containsIn(int value) {
        return (this.value & value) == this.value;
    }

    public int getValue() {
        return value;
    }

}
