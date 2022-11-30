package com.example.demo.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

// OncePerRequestFilter : 요청당 한 번의 filter 를 수행 (doFilterInternal)
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException  {

        String requestCookieToken = null;

        // 쿠키 목록을 조회하며 토큰 조회
        try {
            requestCookieToken = Arrays.stream(request.getCookies())
                    .filter(c -> c.getName().equals("token"))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }catch (NullPointerException e){
            log.info("cookie array is nothing");
        }
        // final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        // 쿠키에 JWT 토큰이 있고, "Bearer "로 시작하는지
        if (requestCookieToken != null && requestCookieToken.startsWith("Bearer ")) {
            jwtToken = requestCookieToken.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        // JWT 토큰이 있다는 건 일단 인증은 받았다는 것
        // JWT 토큰에서 username 있고, 스프링 시큐리티 컨텍스트에 authentication 등록이 아직 안된경우
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // username 으로 db 조회
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

            // 토큰이 유효한(username, 만료일 ) 경우.. 수동으로 인증을 설정하도록 Spring Security 를 구성
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 컨텍스트에서 인증을 설정한 후, 현재 사용자가 인증되었음을 지정. Spring Security Configurations 성공적으로 통과.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                log.info("authentication security context holder 에 등록");
            }
        }
        filterChain.doFilter(request,response);
    }

}
