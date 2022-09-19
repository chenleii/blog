package com.chen.blog.interfaces.http.handle;


import com.chen.blog.core.sharedkernel.exception.DomainRuntimeException;
import com.chen.blog.core.sharedkernel.exception.NotExistException;
import com.chen.blog.interfaces.http.exception.NotLoginException;
import com.chen.blog.interfaces.http.result.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

/**
 * 异常处理
 *
 * @author cl
 * @since 2018/11/3 0:17.
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandle {

    /**
     * 未登录异常
     */
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotLoginException.class)
    @ResponseBody
    public ErrorResult exception(HttpServletRequest request, HttpServletResponse response,
                                 NotLoginException exception) {
        log.warn(this.getClass().getSimpleName() + ":" + exception.getClass().getSimpleName(), exception);
        return ErrorResult.create(ErrorResult.exceptionToErrorCode(exception), exception.getLocalizedMessage());
    }


    /**
     * 不存在异常
     */
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotExistException.class)
    @ResponseBody
    public ErrorResult exception(HttpServletRequest request, HttpServletResponse response,
                                 NotExistException exception) {
        log.warn(this.getClass().getSimpleName() + ":" + exception.getClass().getSimpleName(), exception);
        return ErrorResult.create(ErrorResult.exceptionToErrorCode(exception), exception.getLocalizedMessage());
    }

    /**
     * 领域运行时异常
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DomainRuntimeException.class)
    @ResponseBody
    public ErrorResult exception(HttpServletRequest request, HttpServletResponse response,
                                 DomainRuntimeException exception) {
        log.warn(this.getClass().getSimpleName() + ":" + exception.getClass().getSimpleName(), exception);
        return ErrorResult.create(ErrorResult.exceptionToErrorCode(exception), exception.getLocalizedMessage());
    }

    /**
     * 非法参数异常
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ErrorResult exception(HttpServletRequest request, HttpServletResponse response,
                                 IllegalArgumentException exception) {
        log.warn(this.getClass().getSimpleName() + ":" + exception.getClass().getSimpleName(), exception);
        return ErrorResult.create(ErrorResult.exceptionToErrorCode(exception), exception.getLocalizedMessage());
    }

    /**
     * 非法状态异常
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    @ResponseBody
    public ErrorResult exception(HttpServletRequest request, HttpServletResponse response,
                                 IllegalStateException exception) {
        log.warn(this.getClass().getSimpleName() + ":" + exception.getClass().getSimpleName(), exception);
        return ErrorResult.create(ErrorResult.exceptionToErrorCode(exception), exception.getLocalizedMessage());
    }

    /**
     * 非法格式异常
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalFormatException.class)
    @ResponseBody
    public ErrorResult exception(HttpServletRequest request, HttpServletResponse response,
                                 IllegalFormatException exception) {
        log.warn(this.getClass().getSimpleName() + ":" + exception.getClass().getSimpleName(), exception);
        return ErrorResult.create(ErrorResult.exceptionToErrorCode(exception), exception.getLocalizedMessage());
    }


    /**
     * 参数验证失败
     * controller加注解{@link javax.validation.Valid} {@link org.springframework.validation.annotation.Validated}的情况
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    @ResponseBody
    public ErrorResult exception(HttpServletRequest request, HttpServletResponse response,
                                 BindException exception) {
        log.warn(this.getClass().getSimpleName() + ":" + exception.getClass().getSimpleName(), exception);
        BindingResult bindingResult = exception.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        FieldError fieldError = fieldErrors.get(0);
        return ErrorResult.create(ErrorResult.exceptionToErrorCode(exception), fieldError.getField() + ":" + fieldError.getDefaultMessage());
    }

    /**
     * 参数验证失败
     * service加注解{@link org.springframework.validation.annotation.Validated}的情况
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ErrorResult exception(HttpServletRequest request, HttpServletResponse response,
                                 ConstraintViolationException exception) {
        log.warn(this.getClass().getSimpleName() + ":" + exception.getClass().getSimpleName(), exception);
        Set<ConstraintViolation<?>> constraintViolationSet = exception.getConstraintViolations();
        String msg = null;
        if (CollectionUtils.isNotEmpty(constraintViolationSet)) {
            msg = constraintViolationSet.stream()
                    .findFirst()
                    .map((item) -> {
                        String message = item.getMessage();
                        return Optional.of(item)
                                .map(ConstraintViolation::getPropertyPath)
                                .map((value) -> value + ":" + message)
                                .orElse(message);
                    })
                    .orElse(null);
        }
        return ErrorResult.create(ErrorResult.exceptionToErrorCode(exception), msg);
    }

    /**
     * 缺少请求参数
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ErrorResult exception(HttpServletRequest request, HttpServletResponse response,
                                 MissingServletRequestParameterException exception) {
        log.warn(this.getClass().getSimpleName() + ":" + exception.getClass().getSimpleName(), exception);
        return ErrorResult.create(ErrorResult.exceptionToErrorCode(exception), exception.getLocalizedMessage());
    }

    /**
     * 消息转换异常
     * 如:json格式错误
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseBody
    public ErrorResult exception(HttpServletRequest request, HttpServletResponse response,
                                 HttpMessageConversionException exception) {
        log.warn(this.getClass().getSimpleName() + ":" + exception.getClass().getSimpleName(), exception);
        return ErrorResult.create(ErrorResult.exceptionToErrorCode(exception), exception.getLocalizedMessage());
    }

    /**
     * 404错误.
     */
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public ErrorResult exception(HttpServletRequest request, HttpServletResponse response,
                                 NoHandlerFoundException exception) {
        log.warn(this.getClass().getSimpleName() + ":" + exception.getClass().getSimpleName(), exception);
        return ErrorResult.create(ErrorResult.exceptionToErrorCode(exception), exception.getLocalizedMessage());
    }


    /**
     * 未知错误异常
     */
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UnknownErrorException.class)
    @ResponseBody
    public ErrorResult exception(HttpServletRequest request, HttpServletResponse response,
                                 UnknownErrorException exception) throws Throwable {
        Throwable error = ObjectUtils.defaultIfNull(exception.getCause(), exception);
        log.error(this.getClass().getSimpleName() + ":" + error.getClass().getSimpleName(), exception);
        return ErrorResult.create(ErrorResult.exceptionToErrorCode(error), error.getLocalizedMessage());
    }

    /**
     * 兜底
     */
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorResult exception(HttpServletRequest request, HttpServletResponse response,
                                 Exception exception) {
        log.error(this.getClass().getSimpleName() + ":" + exception.getClass().getSimpleName(), exception);
        return ErrorResult.create(ErrorResult.exceptionToErrorCode(exception), exception.getLocalizedMessage());
    }
}

