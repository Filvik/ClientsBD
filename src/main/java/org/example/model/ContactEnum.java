package org.example.model;

public enum ContactEnum {
    EMAIL("email"),
    PHONE("phone");

    private final String text;

    ContactEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
