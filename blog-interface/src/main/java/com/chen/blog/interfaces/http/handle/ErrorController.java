package com.chen.blog.interfaces.http.handle;

import com.chen.blog.interfaces.http.result.ErrorResult;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author cl
 * @since 2022/09/03 15:07.
 */
@Controller
public class ErrorController extends BasicErrorController {

    @Getter
    private final ErrorAttributes errorAttributes;

    public ErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties,
                           ObjectProvider<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, serverProperties.getError(),
                errorViewResolvers.orderedStream().collect(Collectors.toList()));

        this.errorAttributes = errorAttributes;
    }


    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        return super.errorHtml(request, response);
    }

    /**
     * 覆蓋默认的
     */
    @Override
    @RequestMapping("/original")
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        return super.error(request);
    }

    @RequestMapping
    public ErrorResult error(HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebRequest webRequest = new ServletWebRequest(request);
        HttpStatus status = super.getStatus(request);
        String uri = Objects.toString(webRequest.getAttribute(RequestDispatcher.ERROR_REQUEST_URI, RequestAttributes.SCOPE_REQUEST), "");
        Throwable error = getErrorAttributes().getError(webRequest);
        String message = Objects.toString(webRequest.getAttribute(RequestDispatcher.ERROR_MESSAGE, RequestAttributes.SCOPE_REQUEST), null);

        // 单独处理404
        if (status == HttpStatus.NOT_FOUND) {
            throw new NoHandlerFoundException(request.getMethod(), uri, HttpHeaders.EMPTY);
        }
        if (Objects.nonNull(error)) {
            throw new UnknownErrorException(error.getMessage(), error);
        } else {
            throw new UnknownErrorException(StringUtils.defaultIfBlank(message, status.getReasonPhrase()));
        }
    }

}
