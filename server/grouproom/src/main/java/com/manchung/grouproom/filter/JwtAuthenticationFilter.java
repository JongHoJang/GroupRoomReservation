package com.manchung.grouproom.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manchung.grouproom.Security.JwtProvider;
import com.manchung.grouproom.error.CustomException;
import com.manchung.grouproom.error.ErrorCode;
import com.manchung.grouproom.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        try {
            String token = jwtProvider.resolveToken(request);

            if (token != null) {
                if (!jwtProvider.validateToken(token)) {
                    throw new CustomException(ErrorCode.ACCESS_TOKEN_EXPIRED);
                }
                setAuthentication(token, request);
            }

            chain.doFilter(request, response);

        } catch (CustomException e) {
            setErrorResponse(response, e.getErrorCode());
        }
    }

    private void setAuthentication(String token, HttpServletRequest request) {
        Long userId = jwtProvider.getUserIdFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId.toString());
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        ErrorResponse errorResponse = new ErrorResponse(
                errorCode.getCode(),
                errorCode.getStatus().value(),
                errorCode.getMessage()
        );

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}