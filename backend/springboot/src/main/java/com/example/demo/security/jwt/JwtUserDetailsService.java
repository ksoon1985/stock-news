package com.example.demo.security.jwt;

import com.example.demo.member.model.Member;
import com.example.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 들어온 username 으로 Member 를 찾아 결과적으로 UserDetails(Security) 를 반환
 * loadUserByUsername 오버라이드 하는데 여기서 넘겨받은 UserDetails 와 Authentication 의 패스워드를 비교하고 검증
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(username);

        if (member != null) {
            return new SecurityUser(member);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
