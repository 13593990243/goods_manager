package com.enums;

public enum StatusEnum {
    FINISH("1","已完成"),
    REFUND("1","已完成");
    private String name;
    private String value;

    StatusEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
