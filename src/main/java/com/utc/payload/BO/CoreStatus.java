package com.utc.payload.BO;

import lombok.Getter;

@Getter
public enum CoreStatus {
    CANCEL(-1), PENDING(0), CONFIRMED(1);

    private final Integer code;

    CoreStatus(Integer code) {
        this.code = code;
    }

}
