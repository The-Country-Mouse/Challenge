package com.mouse.challenge.repository;

import com.mouse.challenge.domain.ChallengePost;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengePostRepository extends JpaRepository<ChallengePost, Long> {

    @Query("select distinct p from ChallengePost p"
        + " join fetch p.challengeGroup g"
        + " join fetch p.images i"
        + " join fetch p.user u"
        + " where p.id = :postId")
    ChallengePost findByIdFetchJoin(Long postId);

    @Query("select p from ChallengePost p"
        + " join fetch p.challengeGroup g"
        + " join fetch p.images i"
        + " join fetch p.user u")
    List<ChallengePost> findAllFetchJoin();
}
