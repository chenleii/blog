package com.chen.blog.infrastructure.trace;

import com.chen.blog.core.sharedkernel.trace.Traces;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/15 22:51
 */
public class HttpTraceFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Traces.startTrace(this);
            filterChain.doFilter(request, response);
        } finally {
            Traces.endTrace(this);
        }
    }
}
