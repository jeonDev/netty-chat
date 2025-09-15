package com.chat.main.application.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class UrlFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();

        String queryParameter = queryString != null ? "?" + queryString : "";
        log.info("Request: [{}] {}{}", method, uri, queryParameter);

        // 필터 체인을 계속 진행가
        filterChain.doFilter(request, response);
    }
}
