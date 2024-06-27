package com.mouse.challenge.repository;

import com.mouse.challenge.entity.ChallengePost;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengePostRepository extends JpaRepository<ChallengePost, Long> {

    @Query("select distinct p from ChallengePost p"
        + " join fetch p.challengeGroup g"
        + " join fetch p.images i"
        + " join fetch p.user u"
        + " where p.id = :postId")
    ChallengePost findByIdFetchJoin(@Param("postId") Long postId);

    @Query("select p from ChallengePost p"
        + " join fetch p.challengeGroup g"
        + " join fetch p.images i"
        + " join fetch p.user u")
    List<ChallengePost> findAllFetchJoin();
}
