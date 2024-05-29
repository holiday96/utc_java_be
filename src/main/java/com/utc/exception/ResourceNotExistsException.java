package com.utc.exception;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 29.5.2024
 * Description :
 */
public class ResourceNotExistsException extends RuntimeException {

    public ResourceNotExistsException(String message) {
        super(message);
    }
}
