package com.utc.contants;

public enum EStatus {
    ACTIVE(1),
    INACTIVE(0),
    DELETED(-1);

    public final Integer code;

    private EStatus(Integer code) {
        this.code = code;
    }
}
