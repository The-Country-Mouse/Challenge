package com.mouse.challenge.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mouse.challenge.config.auth.PrincipalDetails;
import com.mouse.challenge.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;


// 스프링 시큐리티에 UsernamePasswordAuthenticationFilter 가 있음.
// /login 요청에 id, pw 값을 넘겨주면 (post)
// UsernamePasswordAuthenticationFilter 가 동작함.

// 현재 로그인폼을 사용하지 않는다고 설정했기 때문에 UsernamePasswordAuthenticationFilter 가 동작 안함.
// 즉 현재 클래스가 동작하지 않음. 그래서 SecurityConfig 에서 필터 추가해줌.
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/api/v1/login");
    }

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if(!request.getMethod().equals("POST")) {
            System.out.println("Post 아님." );
        }

        try {
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);

            // 토큰 생성.
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword());

            // 해당 토큰이 정상인지 유효성 검증.
            // PrincipalDetailsService 의 loadUserByUsername() 힘수가 실행된 후 정상이면 authentication 이 리턴됨.
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

//            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

            // authentication 객체가 sessions 영역에 저장됨.
            return authentication;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행.
    // jwt 토큰을 만들어서 request 요청한 사용자에게 jwt 토큰을 response 해주면 됨.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        // rsa 방식 x, hash 암호 방식
        String jwtToken = JWT.create()
                .withSubject(principalDetails.getUser().getUserName()) //jwt 의 이름
                .withExpiresAt(new Date(System.currentTimeMillis() +(60000 * 10))) // jwt 만료시간
                .withClaim("id", principalDetails.getUser().getUserId()) // jwt 의 payload 부분
                .withClaim("username", principalDetails.getUser().getUserName())
                .sign(Algorithm.HMAC512(JwtUtil.SECRET)); //어떤 해싱 알고리즘으로 해시를 하는지, 어떤 시크릿키를 사용하는지.

        response.addHeader(JwtUtil.HEADER_STRING,JwtUtil.TOKEN_PREFIX + jwtToken);
    }

//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        HttpServletResponse res = (HttpServletResponse) servletResponse;
//
//        if(req.getMethod().equals("POST")) {
//            String headerAuth = req.getHeader("Authorization");
//
//            if(headerAuth.equals("test")) {
//                filterChain.doFilter(req, res);
//            }
//            else {
//                System.out.println("인증 안됨.");
//            }
//        }
//
//
//    }
}
