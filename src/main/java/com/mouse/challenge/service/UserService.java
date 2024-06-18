package com.mouse.challenge.service;

import com.mouse.challenge.entity.User;
import com.mouse.challenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원가입
     */
    @Transactional
    public void join(User user) {
//        if(validateDuplicateUser(user)) {
//            System.out.println("이미 존재하는 회원입니다.");
//
//        }

        userRepository.save(user);
    }

    // 회원 중복 체크
    private boolean validateDuplicateUser(User user) {
        List<User> users = userRepository.findById(user.getUserId());
        return users.isEmpty();
    }


    /**
     * 회원 전체 조회
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }


    /**
     * 회원 단건 조회
     */
    public User findOne(String id) {
        return userRepository.findOne(id);
    }

    /**
     * 회원 정보 수정
     */
    @Transactional
    public boolean updateUserInfo(String id) {
        return true;
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    public boolean deleteUser(String id) {
        return true;
    }
}
