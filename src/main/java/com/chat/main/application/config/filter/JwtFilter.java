package com.chat.main.application.config.filter;

import com.chat.main.application.config.security.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final String BEARER_TYPE = "Bearer";

    public JwtFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = this.resolveAccessToken(request);
        if (accessToken != null) {
            if(jwtProvider.validCheck(accessToken)) {
                Authentication authenticationInfo = jwtProvider.getAuthenticationInfo(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authenticationInfo);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String resolveAccessToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if(authorization == null || !authorization.startsWith(BEARER_TYPE)) return null;

        return authorization.substring(BEARER_TYPE.length() + 1);
    }
}
