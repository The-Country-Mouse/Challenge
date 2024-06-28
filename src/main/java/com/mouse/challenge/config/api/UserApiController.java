package com.mouse.challenge.config.api;

import com.mouse.challenge.config.auth.PrincipalDetails;
import com.mouse.challenge.entity.User;
import com.mouse.challenge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserApiController {

    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/signup")
    public String join(@RequestBody HashMap<String, Object> params) {
        LocalDateTime current = LocalDateTime.now();

        User user = new User();
        user.setUserId(params.get("userId").toString());
        user.setUserName(params.get("userName").toString());
        user.setPassword(bCryptPasswordEncoder.encode(params.get("password").toString()));
//        user.setPassword(params.get("password").toString());
        user.setEmail(params.get("email").toString());
        user.setRegist_date(current);
//        user.setTemp_vl(params.get("").toString());

        userService.join(user);

        return "회원가입 완료";
    }

//    @PostMapping("/login")
//    public String login(
////            @RequestBody HashMap<String, Object> params
////            Authentication authentication,
////            @AuthenticationPrincipal PrincipalDetails userDetails
//    ) {
//        System.out.println("controller" );
////        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal(); // 로그인한 유저 세션 가져오기
////        System.out.println("authentication: " + principalDetails.getUser());
////        System.out.println("userDetails = " + userDetails.getUser());
//
//
//
//
//        return "로그인";
//    }

    // 회원 정보 수정
    @PutMapping("/user/{userId}")
    public void userInfoMod(@PathVariable String id,
                            @RequestBody HashMap<String, Object> params) {
        User user = new User();
        user.setUserId(params.get("userId").toString());
        user.setUserName(params.get("userName").toString());
        user.setPassword(bCryptPasswordEncoder.encode(params.get("password").toString()));
//        user.setPassword(params.get("password").toString());
        user.setEmail(params.get("email").toString());
        user.setRegist_date(LocalDateTime.parse(params.get("regist_date").toString()));
//        user.setTemp_vl(params.get("").toString());

        userService.updateUserInfo(id);
    }

    // 회원탈퇴
    @DeleteMapping("/user/{userId}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

}
