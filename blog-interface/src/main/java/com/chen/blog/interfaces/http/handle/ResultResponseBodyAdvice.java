package com.chen.blog.interfaces.http.handle;

import com.chen.blog.interfaces.http.result.R;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 包装返回结果为：
 * {
 * "code": "200",
 * "msg": "成功",
 * "data": {}
 * }
 *
 * @author cl
 * @version 1.0
 * @since 2020/11/25 14:48
 */
@ControllerAdvice
public class ResultResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    @ResponseBody
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        return (AnnotatedElementUtils.hasAnnotation(methodParameter.getContainingClass(), ResultWrapper.class) ||
                methodParameter.hasMethodAnnotation(ResultWrapper.class));
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        R<?> r = null;
        if (body instanceof R) {
            r= ((R<?>) body);
        }else {
            r = R.success(body);
        }

        return r;
    }

}
