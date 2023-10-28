package org.example.model;

public enum StatusEnum {
    OK("ok"),
    ERROR("error");

    private final String text;

    StatusEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
