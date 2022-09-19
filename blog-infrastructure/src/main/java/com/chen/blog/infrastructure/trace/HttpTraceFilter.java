package com.chen.blog.infrastructure.trace;

import com.chen.blog.core.sharedkernel.trace.Traces;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/15 22:51
 */
@Deprecated
public class HttpTraceFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Traces.startTrace();

            filterChain.doFilter(request, response);
        } finally {
            Traces.endTrace();
        }
    }
}
