package com.utc.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 29.5.2024
 * Description :
 */
@Getter
@Setter
@AllArgsConstructor
public class ValidateException extends RuntimeException {
    private String errorCode;
    private String errorMessage;
}
