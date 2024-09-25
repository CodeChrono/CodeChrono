package com.codechrono.idea.plugin.entity;

public enum EditType {
    INSERT("INSERT"),
    DELETE("DELETE"),
    TMP("TMP"),
    UNDEFINED("UNDEFINED");
    //public static final int IDLE_4 = 3;

    private final String stringValue;
    @Override
    public String toString() {
        return name().toUpperCase();
    }

    EditType(String value) {
        this.stringValue = value;
    }

    // 获取枚举常量的字符串表示
    public String getAsString() {
        return stringValue;
    }

    // 提供一个静态方法，根据字符串值获取对应的枚举常量（可选）
    public static EditType fromString(String value) {
        for (EditType editType : values()) {
            if (editType.stringValue.equals(value)) {
                return editType;
            }
        }
        throw new IllegalArgumentException("EditType: No enum constant " + EditType.class.getName() + "." + value);
    }
}
