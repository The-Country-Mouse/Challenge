package com.mouse.challenge.config.api;

import com.mouse.challenge.entity.User;
import com.mouse.challenge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserApiController {

    private final UserService userService;

//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/signup")
    public String join(@RequestBody HashMap<String, Object> params) {
        LocalDateTime current = LocalDateTime.now();

        User user = new User();
        user.setUserId(params.get("userId").toString());
        user.setUserName(params.get("userName").toString());
//        user.setPassword(bCryptPasswordEncoder.encode(params.get("password").toString()));
        user.setPassword(params.get("password").toString());
        user.setEmail(params.get("email").toString());
        user.setRegist_date(current);
//        user.setTemp_vl(params.get("").toString());

        userService.join(user);

        return "회원가입 완료";
    }
}
