package com.chen.blog.infrastructure.tracer;

import com.chen.blog.core.sharedkernel.tracer.TracerConstant;
import com.chen.blog.core.sharedkernel.tracer.Tracers;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 应用于在http接口方式调用下，处理链路跟踪器开始和关闭的接口过滤器。
 *
 * @author cl
 * @version 1.0
 * @since 2022/9/15 22:51
 */
public class OpenTracerHttpFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 获取调用方的链路跟踪ID
            String tid = request.getHeader(TracerConstant.HTTP_TRACE_ID_HEADER_NAME);
            Tracers.startTrace(request, tid);

            // response设置返回链路跟踪ID
            response.setHeader(TracerConstant.HTTP_TRACE_ID_HEADER_NAME, Tracers.getTraceId());
            filterChain.doFilter(request, response);
        } finally {
            Tracers.endTrace(request);
        }
    }

}
