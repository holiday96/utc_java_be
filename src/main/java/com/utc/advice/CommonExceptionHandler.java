package com.utc.advice;

import com.utc.contants.ApiStatus;
import com.utc.exception.ForbiddenException;
import com.utc.exception.ResourceNotExistsException;
import com.utc.exception.ValidateException;
import com.utc.payload.response.RestApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 29.5.2024
 * Description :
 */
@ControllerAdvice
public class CommonExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler({
            BindException.class,
            HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class,
            HttpRequestMethodNotSupportedException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestApiResponse handleSpringDefaultExceptionWithBadRequest(Exception e) {
        logger.warn(e.getMessage());
        return new RestApiResponse(
                ApiStatus.BAD_REQUEST.code,
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
    }

    @ExceptionHandler(ValidateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestApiResponse handleCustomExceptionWithBadRequest(ValidateException e) {
        logger.warn(e.getMessage());
        if (e.getMessage() != null && e.getErrorCode() != null && e.getErrorMessage() != null) {
            return new RestApiResponse(
                    Integer.parseInt(e.getErrorCode()),
                    e.getErrorMessage()
            );
        }
        return new RestApiResponse(
                ApiStatus.BAD_REQUEST.code,
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public RestApiResponse handleCustomExceptionWithForbidden(ForbiddenException e) {
        logger.warn(e.getMessage());
        return new RestApiResponse(
                ApiStatus.FORBIDDEN.code,
                HttpStatus.FORBIDDEN.getReasonPhrase()
        );
    }

    @ExceptionHandler(ResourceNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public RestApiResponse handleCustomExceptionWithNotFound(ResourceNotExistsException e) {
        logger.warn(e.getMessage());
        return new RestApiResponse(
                ApiStatus.NOT_FOUND.code,
                HttpStatus.NOT_FOUND.getReasonPhrase()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public RestApiResponse handleCustomExceptionWithInternalServerError(Exception e) {
        logger.warn(e.getMessage());
        return new RestApiResponse(
                ApiStatus.INTERNAL_ERROR.code,
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
        );
    }
}
