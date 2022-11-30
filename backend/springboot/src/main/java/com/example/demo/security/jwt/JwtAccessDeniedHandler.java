package com.example.demo.security.jwt;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  JWT 검증 시 발생하는 Exception 을 처리
 *  인가 실패 시 HttpServletResponse.SC_FORBIDDEN 403 (권한)
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 권한이 없이 접근하려 할 때 403
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
