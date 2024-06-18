package com.mouse.challenge.config.auth;

import com.mouse.challenge.entity.User;
import com.mouse.challenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 로그인 요청이 들어올 때 동작.
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 시큐리티 session(내부 Authentication(내부 UserDetails))
    // 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername");
        User userEntity = userRepository.findOne(userId);


        System.out.println("userEntity.getUserId() = " + userEntity.getUserId());
        System.out.println("userEntity.getPassword() = " + userEntity.getPassword());

        return new PrincipalDetails(userEntity);
    }
}
