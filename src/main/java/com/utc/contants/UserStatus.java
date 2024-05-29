package com.utc.contants;

public enum UserStatus {
    ACTIVE(1),
    INACTIVE(0),
    BLOCKED(-1);

    public final Integer code;

    private UserStatus(Integer code) {
        this.code = code;
    }
}
