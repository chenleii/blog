package com.chen.blog.infrastructure.tracer;

import com.chen.blog.core.sharedkernel.tracer.TracerConstant;
import com.chen.blog.core.sharedkernel.tracer.Tracers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/15 22:51
 */
@Deprecated
public class HttpTraceMonitorLogFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(TracerConstant.DEFAULT_HTTP_TRACE_MONITOR_LOGGER_NAME);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Throwable throwable = null;
        long startTime = System.currentTimeMillis();
        try {

            filterChain.doFilter(request, response);
        } catch (ServletException | IOException t) {
            throwable = t;
            throw t;
        } finally {

            long endTime = System.currentTimeMillis();
            logger.info("uri={},tid={},time={},rt={},error={}",
                    request.getRequestURI(),
                    Tracers.getTraceId(),
                    startTime,
                    endTime - startTime,
                    Optional.ofNullable(throwable).map(Throwable::getClass).map(Class::getSimpleName).orElse(null));

        }
    }
}
