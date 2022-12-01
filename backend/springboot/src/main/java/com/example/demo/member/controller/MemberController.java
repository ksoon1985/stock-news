package com.example.demo.member.controller;

import com.example.demo.member.dto.JwtResDTO;
import com.example.demo.member.dto.LoginReqDTO;
import com.example.demo.member.model.Member;
import com.example.demo.member.service.MemberService;
import com.example.demo.security.jwt.JwtTokenUtil;
import com.example.demo.security.jwt.SecurityUser;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
@Api("사용자에 대한 처리를 담당하는 컨트롤러")
public class MemberController {

    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Operation(summary = "회원가입 요청",description = "Member email, nickname, password, gender, birthday 을 파라미터로 회원가입 요청을 합니다.")
    @PostMapping("/signUp")
    public ResponseEntity signUpUser(@Valid @RequestBody Member member, BindingResult result, HttpServletRequest request){

        System.out.println("MemberController 회원가입 요청");

        String memEmail = member.getEmail();
        String memNickName = member.getNickName();
        System.out.println(member);

        // validation (email, nickName, password) error
        if(result.hasErrors() || !emailChk(member.getEmail()) || !nickNameChk(member.getNickName())){
            return ResponseEntity.badRequest().body("400 bad request");
        }


        log.info("Client IP : {} , PORT : {}",request.getRemoteAddr(),request.getRemotePort());
        try{
            member = memberService.signUpMember(member);
            return ResponseEntity.ok().body("success");
        }catch (Exception exception){
            log.info("exception : {}",exception);
            return ResponseEntity.internalServerError().body("create fail");
        }
    }

    @Operation(summary = "이메일 중복 체크", description = "회원가입할 때 이메일 중복체크 확인 요청, true 사용 가능  false : 사용 불가능")
    @GetMapping("/chkEmail/{email}")
    public Boolean emailChk(@PathVariable("email") String email) {

        return memberService.emailChk(email);
    }

    @Operation(summary = "닉네임 중복 체크", description = "회원가입할 때 닉네임 중복체크 확인 요청, true 사용 가능  false : 사용 불가능")
    @GetMapping("/chkNickName/{nickName}")
    public Boolean nickNameChk(@PathVariable("nickName") String nickName) {
        return memberService.nickNameChk(nickName);
    }

    @Operation(summary = "로그인 요청",description = "사용자는 email(username) password 로 로그인 요청을 시도합니다.")
    @PostMapping("/login")
    public ResponseEntity login(HttpServletResponse response, @RequestBody LoginReqDTO loginReqDTO) throws Exception{

        String token = null;

        // 인증 매니저로 부터 인증 객체 얻어옴
        Authentication authentication = authenticate(loginReqDTO.getUsername(),loginReqDTO.getPassword());

        // 로그인이 잘 된 경우(username, password 가 일치하다면)
        if(authentication != null){
            // 스프링 시큐리티 컨텍스트에 인증 객체 등록
            // SecurityContextHolder.getContext().setAuthentication(authentication);

            // JWT 토큰 생성
            SecurityUser securityUser = (SecurityUser)authentication.getPrincipal();
            token = jwtTokenUtil.generateToken(securityUser);

            // JWT 쿠키 생성
            Cookie cookie = new Cookie("token","Bearer "+token);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge((int)JwtTokenUtil.JWT_TOKEN_VALIDITY);
            response.addCookie(cookie);

            String nickName = securityUser.getMember().getNickName();

            JwtResDTO jwtResDTO = JwtResDTO.builder()
                    .token(token)
                    .email(((SecurityUser) authentication.getPrincipal()).getUsername())
                    .role(authentication.getAuthorities().toString())
                    .nickName(nickName)
                    .build();

            return ResponseEntity.ok().body(jwtResDTO);
        }else{
            log.info("login fail");
        }

        return ResponseEntity.badRequest().body("id 와 pw 가 일치하지 않습니다.");
    }

    @Operation(summary = "로그아웃 요청",description = "로그아웃을 요청합니다.")
    @GetMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request,HttpServletResponse response){

        // security context 의 authentication 초기화
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(null);

        try {
            // 등록된 JWT 쿠키 조회
            Cookie tokenCookie = Arrays.stream(request.getCookies())
                    .filter(c -> c.getName().equals("token"))
                    .findFirst()
                    .orElse(null);

            if(tokenCookie != null){
                // JWT 쿠키 삭제 (쿠키를 생성할 때 domain, path 를 설정했다면 삭제할떄도 셋팅을 해줘야 한다.)
                log.info("delete token name = {}",tokenCookie.getName());
                tokenCookie.setValue(null);
                tokenCookie.setMaxAge(0);
                tokenCookie.setPath("/");
                response.addCookie(tokenCookie);
                log.info("토큰이 삭제됐습니다.");
            }

        }catch (NullPointerException e){

        }

        return ResponseEntity.ok().body("logout success");
    }


    /**
     *  인증 매니저가 인증 제공자 (AuthenticationProvider) 유저 디테일 서비스 (JwtUserDetailService) 를
     *  내부적으로 호출하여 인증 객체를 반환
     */
    private Authentication authenticate(String username, String password) throws Exception{
        try{
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (DisabledException e){
            throw new Exception("USER_DISABLED", e);
        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
