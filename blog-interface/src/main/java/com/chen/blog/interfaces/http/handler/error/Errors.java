package com.chen.blog.interfaces.http.handler.error;

import com.chen.blog.interfaces.http.handler.error.exception.errorcode.Successful;
import com.chen.blog.interfaces.http.handler.error.exception.errorcode.UnknownError;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * @author <a href="mailto:wb-cl788111@alibaba-inc.com">cl</a>
 * @version 1.0
 * @since 2023/3/19 21:44
 */
public class Errors {


    /**
     * 转错误码
     *
     * @param error 错误
     * @return 错误码
     */
    public static String toErrorCode(Object error) {
        return Optional.ofNullable(error)
                .map(Object::getClass)
                .map(Class::getSimpleName)
                .map((value) -> StringUtils.removeEnd(value, "RuntimeException"))
                .map((value) -> StringUtils.removeEnd(value, "Exception"))
                .orElse(defaultErrorCode());
    }

    public static String defaultErrorCode() {
        return UnknownError.class.getSimpleName();
    }
    public static String successfulErrorCode() {
        return Successful.class.getSimpleName();
    }


}
