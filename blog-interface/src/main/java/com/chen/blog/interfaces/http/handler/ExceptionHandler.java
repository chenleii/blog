package com.chen.blog.interfaces.http.handler;


import com.chen.blog.configuration.properties.ApiProperties;
import com.chen.blog.core.sharedkernel.exception.DomainRuntimeException;
import com.chen.blog.core.sharedkernel.exception.NotExistException;
import com.chen.blog.core.sharedkernel.logger.Loggers;
import com.chen.blog.core.sharedkernel.trace.Traces;
import com.chen.blog.interfaces.http.exception.NotLoginException;
import com.chen.blog.interfaces.http.handler.error.Errors;
import com.chen.blog.interfaces.http.handler.error.exception.UnknownErrorException;
import com.chen.blog.interfaces.http.handler.result.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.IllegalFormatException;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * 异常处理器
 *
 * @author cl
 * @since 2018/11/3 0:17.
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandler {

    @Inject
    private ApiProperties apiProperties;

    @Inject
    private HttpServletRequest request;


    /**
     * 未登录异常
     */
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @org.springframework.web.bind.annotation.ExceptionHandler(NotLoginException.class)
    @ResponseBody
    public R<?> exception(HttpServletRequest request, HttpServletResponse response,
                          NotLoginException exception) {
        return handle(exception, null);
    }


    /**
     * 不存在异常
     */
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @org.springframework.web.bind.annotation.ExceptionHandler(NotExistException.class)
    @ResponseBody
    public R<?> exception(HttpServletRequest request, HttpServletResponse response,
                          NotExistException exception) {
        return handle(exception, null);
    }

    /**
     * 领域运行时异常
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(DomainRuntimeException.class)
    @ResponseBody
    public R<?> exception(HttpServletRequest request, HttpServletResponse response,
                          DomainRuntimeException exception) {
        return handle(exception, null);
    }

    /**
     * 非法参数异常
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public R<?> exception(HttpServletRequest request, HttpServletResponse response,
                          IllegalArgumentException exception) {
        return handle(exception, null);
    }

    /**
     * 非法状态异常
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalStateException.class)
    @ResponseBody
    public R<?> exception(HttpServletRequest request, HttpServletResponse response,
                          IllegalStateException exception) {
        return handle(exception, null);
    }

    /**
     * 非法格式异常
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalFormatException.class)
    @ResponseBody
    public R<?> exception(HttpServletRequest request, HttpServletResponse response,
                          IllegalFormatException exception) {
        return handle(exception, null);
    }


    /**
     * 参数验证失败
     * controller加注解{@link jakarta.validation.Valid} {@link org.springframework.validation.annotation.Validated}的情况
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    @ResponseBody
    public R<?> exception(HttpServletRequest request, HttpServletResponse response,
                          BindException exception) {
        final String errorMessage = Optional.ofNullable(exception)
                .map(BindException::getBindingResult)
                .map(org.springframework.validation.Errors::getFieldErrors)
                .filter(CollectionUtils::isNotEmpty)
                .map((value) -> value.get(0))
                .map((value) -> value.getField() + ":" + value.getDefaultMessage())
                .orElse(null);
        return handle(exception, errorMessage);
    }

    /**
     * 参数验证失败
     * service加注解{@link org.springframework.validation.annotation.Validated}的情况
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public R<?> exception(HttpServletRequest request, HttpServletResponse response,
                          ConstraintViolationException exception) {
        final String errorMessage = Optional.ofNullable(exception.getConstraintViolations())
                .filter(CollectionUtils::isNotEmpty)
                .map((Function<? super Set<ConstraintViolation<?>>, ? extends ConstraintViolation<?>>) IterableUtils::first)
                .map((value) -> value.getPropertyPath() + ":" + value.getMessage())
                .orElse(null);
        return handle(exception, errorMessage);
    }

    /**
     * 缺少请求参数
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public R<?> exception(HttpServletRequest request, HttpServletResponse response,
                          MissingServletRequestParameterException exception) {
        return handle(exception, null);
    }

    /**
     * 消息转换异常
     * 如:json格式错误
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageConversionException.class)
    @ResponseBody
    public R<?> exception(HttpServletRequest request, HttpServletResponse response,
                          HttpMessageConversionException exception) {
        return handle(exception, null);
    }

    /**
     * 404错误.
     */
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @org.springframework.web.bind.annotation.ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public R<?> exception(HttpServletRequest request, HttpServletResponse response,
                          NoHandlerFoundException exception) {
        return handle(exception, null);
    }


    /**
     * 未知错误异常
     */
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(UnknownErrorException.class)
    @ResponseBody
    public R<?> exception(HttpServletRequest request, HttpServletResponse response,
                          UnknownErrorException exception) throws Throwable {
        // 优先使用上一级异常（导致该异常UnknownErrorException发生的异常）
        Throwable error = ObjectUtils.defaultIfNull(exception.getCause(), exception);
        return handle(error, null);
    }

    /**
     * 兜底
     */
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseBody
    public R<?> exception(HttpServletRequest request, HttpServletResponse response,
                          Exception exception) {
        return handle(exception, null);
    }

    /**
     * 统一处理
     *
     * @param exception    异常
     * @param errorMessage 错误消息（可为空）
     * @return 错误结果
     */
    private R<?> handle(Throwable exception, String errorMessage) {
        log.error(this.getClass().getSimpleName() + ":" + exception.getClass().getSimpleName(), exception);
        // 打印监控日志
        Loggers.BizMonitorLogger.log(this.getClass().getSimpleName(), Traces.getTraceId(), request.getRequestURI(), exception.getClass().getSimpleName());
        return R.builder()
                .traceId(Traces.getTraceId())
                .success(false)
                .errorCode(Errors.toErrorCode(exception))
                .errorMessage(StringUtils.isNotBlank(errorMessage) ? errorMessage : exception.getLocalizedMessage())
                .errorStackTrace(apiProperties.getExceptionHandler().getErrorStackTrace().isEnabled() ? ExceptionUtils.getStackTrace(exception) : null)
                .error(null)
                .data(null)
                .build();
    }


}

