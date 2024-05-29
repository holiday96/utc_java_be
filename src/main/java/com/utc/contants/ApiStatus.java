package com.utc.contants;

public enum ApiStatus {
    SUCCESS(200),
    BAD_REQUEST(400),
    NOT_IN_LOGIN(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    INTERNAL_ERROR(500);

    public final Integer code;

    private ApiStatus(Integer code) {
        this.code = code;
    }
}
