package com.mouse.challenge.repository;

import com.mouse.challenge.domain.ChallengeGroup;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChallengeGroupRepository {

    private final EntityManager em;

    // 챌린지그룹 저장
    public void save(ChallengeGroup challengeGroup){
        em.persist(challengeGroup);
    }

    // 챌린지그룹 단건 조회
    public ChallengeGroup findById(String challengeId){
        return em.find(ChallengeGroup.class, challengeId);
    }

    // 챌린지그룹 다중 조회
    public List<ChallengeGroup> findAll(){
        return em.createQuery("SELECT cg FROM ChallengeGroup cg", ChallengeGroup.class)
                .getResultList();
    }

    // 카테고리ID로 챌린지그룹 조회
    public List<ChallengeGroup> findByCategoryId(String categoryId) {
        return em.createQuery("SELECT cg FROM ChallengeGroup cg WHERE cg.categoryId = :categoryId", ChallengeGroup.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    // 참여 활성화 챌린지그룹 조회
    public List<ChallengeGroup> findActiveChallenges(){
        Date today = new Date();
        return em.createQuery("SELECT cg FROM ChallengeGroup cg WHERE :today BETWEEN cg.challengeStartDate AND cg.challengeEndDate", ChallengeGroup.class)
                .setParameter("today", today)
                .getResultList();
    }

    // 기간별 챌린지그룹 조회
    public List<ChallengeGroup> findChallengesByDateRange(Date startDate, Date endDate){
        return em.createQuery("SELECT cg FROM ChallengeGroup cg WHERE cg.challengeStartDate >= :startDate AND cg.challengeEndDate <= :endDate", ChallengeGroup.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    // 최대 참여자 수 이하의 챌린저그룹 조회
    public List<ChallengeGroup> findChallengeByMaxParticipants(int maxParticipants){
        return em.createQuery("SELECT cg FROM ChallengeGroup cg WHERE cg.maxParticipants <= :maxParticipants", ChallengeGroup.class)
                .setParameter("maxParticipants", maxParticipants)
                .getResultList();
    }

    // 챌린지그룹 삭제
    public void deleteById(String challengeId){
        ChallengeGroup challengeGroup = em.find(ChallengeGroup.class, challengeId);
        if(challengeGroup != null){
            em.remove(challengeGroup);
        }else{
            throw new IllegalArgumentException("해당 ID에 해당하는 챌린지 그룹이 없습니다. (challengeId: " + challengeId + ")");
        }
    }
}
