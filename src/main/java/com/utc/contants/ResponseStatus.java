package com.utc.contants;

public enum ResponseStatus {
    SUCCESS(200, "success"),
    ERROR(400, "error");

    public final Integer code;
    public final String msg;

    private ResponseStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
