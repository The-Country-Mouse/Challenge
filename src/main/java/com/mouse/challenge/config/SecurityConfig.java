package com.mouse.challenge.config;

import com.mouse.challenge.config.auth.PrincipalDetailsService;
import com.mouse.challenge.config.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록됨.
@RequiredArgsConstructor
public class SecurityConfig { //스프링 시큐리티 필터

    private final CorsConfig corsConfig;
    private final PrincipalDetailsService principalDetailsService;

    /**
     * 인증/인가에 대한 설정
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // authenticationManager 생성해줌.
        AuthenticationManagerBuilder sharedObject = http.getSharedObject(AuthenticationManagerBuilder.class);
        sharedObject.userDetailsService(this.principalDetailsService);
        AuthenticationManager authenticationManager = sharedObject.build();
        http.authenticationManager(authenticationManager);

        http.csrf(AbstractHttpConfigurer::disable) //csrf 보호기능 비활성화
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilter(corsConfig.corsFilter())
                .addFilterBefore(new JwtAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                    auth -> auth.requestMatchers( "/api/v1/signup","/api/v1/login").permitAll() //인증없이 접근 가능.
//                          .requestMatchers("").hasRole("") //역할을 가진 사용자만 접근가능
                            .anyRequest().authenticated() //이외의 모든 접근에 대해서 인증 진행
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 생성하지 않음, stateless 서버로 사용하겠다.
                );// 세션관리 설정

        return http.build();
    }

    /**
     * 비밀번호 암호화
     */
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
