package com.utc.utils;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 29.5.2024
 * Description :
 */
@Component
public class MessageUtils {

    public static <T> String getProperty(MessageSource messageSource, String key, String[] arguments) {
        return messageSource.getMessage(key, arguments, Locale.getDefault());
    }

    public static <T> String getProperty(MessageSource messageSource, String key) {
        return messageSource.getMessage(key, new String[]{}, Locale.getDefault());
    }
}
