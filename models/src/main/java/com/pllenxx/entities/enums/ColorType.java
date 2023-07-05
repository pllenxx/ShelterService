package com.pllenxx.entities.enums;

public enum ColorType {
    WHITE("white"),
    GREY("grey"),
    BLACK("black"),
    BROWN("brown"),
    RED("red");

    private final String color;

    ColorType(String color) {
        this.color = color;
    }

    public boolean equalsName(String otherName) {
        return color.equals(otherName);
    }

    public String toString() {
        return this.color;
    }
}

