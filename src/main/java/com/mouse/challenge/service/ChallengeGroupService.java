package com.mouse.challenge.service;

import com.mouse.challenge.domain.ChallengeGroup;
import com.mouse.challenge.repository.ChallengeGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeGroupService {

    private static final Logger logger = Logger.getLogger(ChallengeGroupService.class.getName());

    private final ChallengeGroupRepository challengeGroupRepository;

    // 이미지 업로드 경로
    private static final String UPLOAD_DIRECTORY = "/src/main/resources/static/images";

    // 챌린지그룹 저장
    @Transactional
    public void saveChallengeGroup(ChallengeGroup challengeGroup, MultipartFile uploadImage) throws IOException {

        challengeGroup.setTitle(challengeGroup.getTitle());
        challengeGroup.setContent(challengeGroup.getContent());
        challengeGroup.setMaxParticipants(challengeGroup.getMaxParticipants());
        challengeGroup.setChallengeCategories(challengeGroup.getChallengeCategories());
        challengeGroup.setGroupCreateDate(new Date());
        challengeGroup.setChallengeStartDate(challengeGroup.getChallengeStartDate());
        challengeGroup.setChallengeEndDate(challengeGroup.getChallengeEndDate());

        if(uploadImage != null && !uploadImage.isEmpty()){
            // 파일 이름 생성
            String originalFileName = uploadImage.getOriginalFilename();
            String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

            // 파일 저장 경로
            Path path = Paths.get(UPLOAD_DIRECTORY + "/" + fileName);

            // 파일 저장
            Files.write(path, uploadImage.getBytes());
            logger.log(Level.INFO, "파일 저장됨: {0}", path.toString());

            // 챌린지그룹 엔티티에 이미지 경로 저장
            challengeGroup.setChallengeImage("/images/" + fileName);
        }else{
            challengeGroup.setChallengeImage("이미지 없음");
        }
        challengeGroupRepository.save(challengeGroup);
        logger.log(Level.INFO, "챌린지그룹 저장됨: {0}", challengeGroup);

    }

    // 챌린지그룹 단건 조회
    public ChallengeGroup findById(String challengeId){
        return challengeGroupRepository.findById(challengeId);
    }

    // 챌린지 다중 조회
    public List<ChallengeGroup> findAll(){
        return challengeGroupRepository.findAll();
    }

    // 카테고리ID로 챌린지그룹 단건 조회
    public List<ChallengeGroup> findByCategoryId(String categoryId) {
        return challengeGroupRepository.findByCategoryId(categoryId);
    }

    // 참여 활성화 챌린지그룹 조회
    public List<ChallengeGroup> findActiveChallenges(){
        return challengeGroupRepository.findActiveChallenges();
    }

    // 기간별 챌린지그룹 조회
    public List<ChallengeGroup> findChallengesByDateRange(Date startDate, Date endDate){
        return challengeGroupRepository.findChallengesByDateRange(startDate, endDate);
    }

    // 최대 참여자 수 이하의 챌린지그룹 조회
    public List<ChallengeGroup> findChallengeByMaxParticipants(int maxParticipants){
        return challengeGroupRepository.findChallengeByMaxParticipants(maxParticipants);
    }

    // 챌린지그룹 삭제
    public void deleteById(String challengeId){
        challengeGroupRepository.deleteById(challengeId);
    }
}
