package com.example.demo.security.config;

import com.example.demo.security.jwt.JwtAccessDeniedHandler;
import com.example.demo.security.jwt.JwtAuthenticationEntryPoint;
import com.example.demo.security.jwt.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableWebSecurity // SpringSecurityFilterChain 자동 생성
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final UserDetailsService jwtUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    // Authentication Manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    // 패스워드 암호화 인코딩
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Authentication Provider
    // - UserDetailsService , PasswordEncoder 등록
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(jwtUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        // cors issue
        httpSecurity.cors().disable();
        // restful, ajax 등 허용
        httpSecurity.csrf().disable();
        // x-frame-options header 삭제 (iframe 사용 시 )
        httpSecurity.headers().frameOptions().disable();

        /*
            시큐리티 세션 정책
            always : 항상 세션 생성
            if_required : 필요시 생성
            never : 생성하지 않지만, 기존에 존재하면 사용
            stateless : 생성하지 않고, 기존 존재와 상관없이 미사용 (주로 jwt 사용할 때)
                -> 시큐리티는 기본적으로 세션을 사용하지만, 이 앱은 사용을 안하기 때문에 STATELESS 로 설정
         */
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // url pattern 에 맞게 인증, 권한 필요 여부
        // 배포시 새로고침 할 때 인증오류 때문에 일단 다 열어둠
        httpSecurity.authorizeRequests()
                        .antMatchers(
                                "/**"

//                                "/","/index.html", "/favicon.ico" ,"/webjars/**", "/css/**", "/js/**", "/img/**",
//                                "/member/**",
//                                "/stock/**",
//                                "/news/**",
//                                "/community/**",
//
//                                "/api/member/**",
//                                "/api/stock/**",
//                                "/api/news/**",
//                                "/api/community/**",
//
//                                "/api/swagger/**",
//                                "/v3/api-docs",
//                                "/swagger-ui/**",
//                                "/swagger-resources",
//                                "/swagger-resources/configuration/ui",
//                                "/swagger-resources/configuration/security"
                        ).permitAll()
                        .antMatchers("/api/admin/**").hasRole("ADMIN");
//                        .anyRequest().authenticated();

        // api server 기 때문에 login form 해제
        httpSecurity.formLogin().disable();
        // 사용자 인증방법으로 보안 이슈때문에 사용안하는것을 권장
        httpSecurity.httpBasic().disable();

        // 인증, 인가 예외 처리
        httpSecurity.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler);

        // jwt filter 등록
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        // authenticationProvider 등록
        httpSecurity.authenticationProvider(authenticationProvider());
        return httpSecurity.build();
    }
}
