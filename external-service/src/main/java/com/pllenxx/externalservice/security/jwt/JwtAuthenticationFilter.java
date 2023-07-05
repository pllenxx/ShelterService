package com.pllenxx.externalservice.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = jwtUtils.resolveToken(request);
            if (StringUtils.hasText(token) && jwtUtils.validateToken(token)) {
                Authentication authenticationToken = jwtUtils.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (ExpiredJwtException | BadCredentialsException ex) {
            request.setAttribute("Exception", ex);
            throw ex;
        }
        filterChain.doFilter(request, response);
    }
}
