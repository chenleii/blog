package com.chen.blog.infrastructure.trace;

import com.chen.blog.core.sharedkernel.trace.TraceConstant;
import com.chen.blog.core.sharedkernel.trace.Traces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/15 22:51
 */
@Deprecated
public class HttpTraceMonitorLogFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(TraceConstant.DEFAULT_HTTP_TRACE_MONITOR_LOGGER_NAME);

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
                    Traces.getTraceId(),
                    startTime,
                    endTime - startTime,
                    Optional.ofNullable(throwable).map(Throwable::getClass).map(Class::getSimpleName).orElse(null));

        }
    }
}
