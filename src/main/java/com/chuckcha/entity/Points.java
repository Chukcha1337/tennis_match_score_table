package com.chuckcha.entity;

public enum Points {
    ZERO("0"), FIFTEEN("15"), THIRTY("30"), FORTY("40"), ADVANTAGE("AD");

    private final String representation;

    Points(String representation) {
        this.representation = representation;
    }

    public String getRepresentation() {
        return representation;
    }
}
