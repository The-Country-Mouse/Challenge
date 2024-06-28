package com.mouse.challenge;

import com.mouse.challenge.domain.ChallengeGroup;
import com.mouse.challenge.service.ChallengeGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/challenge-groups")
@RequiredArgsConstructor
public class ChallengeGroupController {

    private final ChallengeGroupService challengeGroupService;

    // 챌린지그룹 저장
    @PostMapping
    public ResponseEntity<ChallengeGroup> saveChallengeGroup(@RequestBody ChallengeGroup challengeGroup, @RequestPart MultipartFile uploadImage) throws IOException {
        challengeGroupService.saveChallengeGroup(challengeGroup, uploadImage);
        return ResponseEntity.ok(challengeGroup);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeGroup> getChallengeGroupById(@PathVariable String id) {
        ChallengeGroup challengeGroup = challengeGroupService.findById(id);
        return ResponseEntity.ok(challengeGroup);
    }

    // 챌린지그룹 다중 조회
    @GetMapping
    public ResponseEntity<List<ChallengeGroup>> getAllChallengeGroups() {
        List<ChallengeGroup> challengeGroups = challengeGroupService.findAll();
        return ResponseEntity.ok(challengeGroups);
    }

    // 카테고리 ID로 챌린지그룹 다중 조회
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ChallengeGroup>> getChallengeGroupsByCategoryId(@PathVariable String categoryId) {
        List<ChallengeGroup> challengeGroups = challengeGroupService.findByCategoryId(categoryId);
        return ResponseEntity.ok(challengeGroups);
    }

    // 활성화된 챌린지그룹 조회
    @GetMapping("/active")
    public ResponseEntity<List<ChallengeGroup>> getActiveChallengeGroups() {
        List<ChallengeGroup> activeChallengeGroups = challengeGroupService.findActiveChallenges();
        return ResponseEntity.ok(activeChallengeGroups);
    }

    // 기간별 챌린지그룹 조회
    @GetMapping("/date-range")
    public ResponseEntity<List<ChallengeGroup>> getChallengeGroupsByDateRange(
            @RequestParam Date startDate,
            @RequestParam Date endDate) {
        List<ChallengeGroup> challengeGroups = challengeGroupService.findChallengesByDateRange(startDate, endDate);
        return ResponseEntity.ok(challengeGroups);
    }

    // 최대 참여자 수 이하의 챌린지그룹 조회
    @GetMapping("/max-participants/{maxParticipants}")
    public ResponseEntity<List<ChallengeGroup>> getChallengeGroupsByMaxParticipants(@PathVariable int maxParticipants) {
        List<ChallengeGroup> challengeGroups = challengeGroupService.findChallengeByMaxParticipants(maxParticipants);
        return ResponseEntity.ok(challengeGroups);
    }

    // 챌린지그룹 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChallengeGroupById(@PathVariable String id) {
        challengeGroupService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
