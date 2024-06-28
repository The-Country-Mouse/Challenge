package com.mouse.challenge.controller;

import com.mouse.challenge.config.auth.PrincipalDetails;
import com.mouse.challenge.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login")
    public String login(
            Authentication authentication,
            @AuthenticationPrincipal PrincipalDetails userDetails
    ) { // DI (의존성 주입)
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal(); // 로그인한 유저 세션 가져오기
        System.out.println("authentication: " + principalDetails.getUser());
        System.out.println("userDetails = " + userDetails.getUser());
        return "login";
    }

    @GetMapping("/join")
    public String join(User user) {
        String password = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(password); // 비밀번호 암호화
        return "join";
    }

    @GetMapping("/joinProc")
    public @ResponseBody String joinProc() {
        return "회원가입 완료됨";
    }
}
