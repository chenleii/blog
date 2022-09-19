package com.chen.blog.interfaces.http.result;

import com.chen.blog.interfaces.http.handle.UnknownErrorException;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/3 1:06
 */
@Getter
@ToString
public class ErrorResult {

    /**
     * 错误码
     */
    private final String errorCode;
    /**
     * 错误信息
     */
    private final String errorMessage;

    /**
     * 错误
     * <p>
     * 比如堆栈
     */
    private final Object error;

    private ErrorResult(String errorCode, String errorMessage, Object error) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.error = error;
    }

    public static ErrorResult create(String errorCode, String errorMessage) {
        return new ErrorResult(errorCode, errorMessage, null);
    }

    public static ErrorResult create(String errorCode, String errorMessage, Object error) {
        return new ErrorResult(errorCode, errorMessage, null);
    }


    public static ErrorResult create(Exception exception) {
        return new ErrorResult(exceptionToErrorCode(exception), exception.getMessage(), null);
    }

    public static ErrorResult create(Exception exception, String errorMessage) {
        return new ErrorResult(exceptionToErrorCode(exception), errorMessage, null);
    }

    public static ErrorResult create(Exception exception, String errorMessage, Object error) {
        return new ErrorResult(exceptionToErrorCode(exception), errorMessage, error);
    }

    /**
     * 异常转换为错误码
     *
     * @param throwable 异常
     * @return 错误码
     */
    public static String exceptionToErrorCode(Throwable throwable) {
        String code = Objects.nonNull(throwable) ? exceptionToErrorCode(throwable.getClass()) : null;
        return StringUtils.defaultIfBlank(code, exceptionToErrorCode(UnknownErrorException.class));
    }

    private static <T extends Throwable> String exceptionToErrorCode(Class<T> throwableClass) {
        String code = throwableClass.getSimpleName();
        code = StringUtils.removeEnd(code, "RuntimeException");
        code = StringUtils.removeEnd(code, "Exception");
        return code;
    }
}
