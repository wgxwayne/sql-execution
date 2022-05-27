package indi.wgx.enums;

public enum OrderWayType {
    ASCENDING_ORDER("0", "ASC"),
    DESCENDING_ORDER("1", "DESC");

    private final String code;
    private final String info;

    OrderWayType(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
