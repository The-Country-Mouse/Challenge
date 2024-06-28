package com.mouse.challenge.service;

import com.mouse.challenge.entity.ChallengeCategory;
import com.mouse.challenge.entity.ChallengeGroup;
import com.mouse.challenge.repository.ChallengeGroupRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestPropertySource(properties = "file.upload-dir=/Users/jihyeonjeong/Developer/Challenge/src/main/java/resources/static/images")
@Transactional
public class ChallengeGroupServiceTest {


    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ChallengeGroupRepository challengeGroupRepository;

    @Mock
    private MockMvc mockMvc;

    @Autowired
    private ChallengeGroupService challengeGroupService;

    private Logger logger = Logger.getLogger(ChallengeGroupService.class.getName());



    @Test
    public void testSaveChallengeGroup() throws IOException {

        // 현재 날짜
        Calendar calendar = Calendar.getInstance();

        // 시작 날짜 (어제)
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date startDate = calendar.getTime();

        // 이번 주 일요일 구하기
        calendar = Calendar.getInstance(); // 다시 현재 날짜로 설정
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Date endDate = calendar.getTime();

        // Given
        ChallengeGroup challengeGroup = new ChallengeGroup();
        challengeGroup.setTitle("테스트 챌린지");
        challengeGroup.setContent("테스트 내용");
//        challengeGroup.setChallengeCategories(); 게시글 한개당 카테고리 1개인데 리스트 일 필요가...?
        challengeGroup.setGroupCreateDate(new Date());
        challengeGroup.setChallengeStartDate(startDate);
        challengeGroup.setChallengeEndDate(endDate);
        challengeGroup.setMaxParticipants(10);


        MockMultipartFile uploadImage = new MockMultipartFile("image", "test.jpg", "image/jpeg", new byte[0]);

        // When
        challengeGroupService.saveChallengeGroup(challengeGroup, uploadImage);

        // Then
        List<ChallengeGroup> savedChallengeGroups = challengeGroupRepository.findAll();
        assertThat(savedChallengeGroups).hasSize(1);
        ChallengeGroup savedChallengeGroup = savedChallengeGroups.get(0);
        assertThat(savedChallengeGroup.getTitle()).isEqualTo("테스트 챌린지");
        assertThat(savedChallengeGroup.getChallengeImage()).isNotNull();
    }

    @Test
    public void testFindById() {
        // Given
        ChallengeGroup challengeGroup = new ChallengeGroup();
        challengeGroup.setTitle("테스트 챌린지");
        challengeGroupRepository.save(challengeGroup);

        // When
        ChallengeGroup foundChallengeGroup = challengeGroupService.findById(challengeGroup.getChallengeId().toString());

        // Then
        assertThat(foundChallengeGroup).isNotNull();
        assertThat(foundChallengeGroup.getTitle()).isEqualTo("테스트 챌린지");
    }

    @Test
    public void testFindAll() {
        // Given
        ChallengeGroup challengeGroup1 = new ChallengeGroup();
        challengeGroup1.setTitle("챌린지 그룹 1");
        ChallengeGroup challengeGroup2 = new ChallengeGroup();
        challengeGroup2.setTitle("챌린지 그룹 2");
        challengeGroupRepository.save(challengeGroup1);
        challengeGroupRepository.save(challengeGroup2);

        // When
        List<ChallengeGroup> allChallengeGroups = challengeGroupService.findAll();

        // Then
        assertThat(allChallengeGroups).hasSize(2);
        assertThat(allChallengeGroups).extracting("title")
                .containsExactlyInAnyOrder("챌린지 그룹 1", "챌린지 그룹 2");
    }

    @Test
    public void testFindByCategoryId() {
        // Given
        ChallengeGroup challengeGroup1 = new ChallengeGroup();
        ChallengeGroup challengeGroup2 = new ChallengeGroup();

        ChallengeCategory category1 = new ChallengeCategory();
        category1.setId(1L);
        category1.setChallengeGroup(challengeGroup1);

        ChallengeCategory category2 = new ChallengeCategory();
        category2.setId(2L);
        category2.setChallengeGroup(challengeGroup2);

        challengeGroupRepository.save(challengeGroup1);
        challengeGroupRepository.save(challengeGroup2);

        // When
        List<ChallengeGroup> foundByCategory = challengeGroupService.findByCategoryId("1");

        // Then
        assertThat(foundByCategory).hasSize(1);
        assertThat(foundByCategory.get(0).getChallengeCategories().get(0).getId()).isEqualTo("1");
    }

    @Test
    public void testFindActiveChallenges() {
        // Given
        Date today = new Date();

        ChallengeGroup group1 = new ChallengeGroup();
        group1.setTitle("Challenge Group 1");
        group1.setChallengeStartDate(today);
        group1.setChallengeEndDate(today);

        ChallengeGroup group2 = new ChallengeGroup();
        group2.setTitle("Challenge Group 2");
        group2.setChallengeStartDate(today);
        group2.setChallengeEndDate(today);

        ChallengeGroup group3 = new ChallengeGroup();
        group3.setTitle("Challenge Group 3");
        group3.setChallengeStartDate(new Date(today.getTime() - 1000000)); // 이전 날짜
        group3.setChallengeEndDate(new Date(today.getTime() + 1000000)); // 미래 날짜

        entityManager.persist(group1);
        entityManager.persist(group2);
        entityManager.persist(group3);
        entityManager.flush();

        // When
        List<ChallengeGroup> activeChallenges = challengeGroupService.findActiveChallenges();

        // Then
        assertNotNull(activeChallenges);
        assertEquals(2, activeChallenges.size(), "There should be 2 active challenge groups");

        // Optional: Print out the names of the active challenge groups
        activeChallenges.forEach(group -> System.out.println(group.getTitle()));

    }

    @Test
    public void testFindChallengesByDateRange() {
        // Given
        Date today = new Date();

        // ChallengeGroup 1: Active today
        ChallengeGroup group1 = new ChallengeGroup();
        group1.setTitle("Challenge Group 1");
        group1.setChallengeStartDate(today);
        group1.setChallengeEndDate(today);

        // ChallengeGroup 2: Active today
        ChallengeGroup group2 = new ChallengeGroup();
        group2.setTitle("Challenge Group 2");
        group2.setChallengeStartDate(today);
        group2.setChallengeEndDate(today);

        // ChallengeGroup 3: Active in a past and future range
        ChallengeGroup group3 = new ChallengeGroup();
        group3.setTitle("Challenge Group 3");
        group3.setChallengeStartDate(new Date(today.getTime() - 1000000)); // 1000000 milliseconds (11 days) ago
        group3.setChallengeEndDate(new Date(today.getTime() + 1000000)); // 1000000 milliseconds (11 days) ahead

        entityManager.persist(group1);
        entityManager.persist(group2);
        entityManager.persist(group3);
        entityManager.flush();

        // When
        List<ChallengeGroup> activeChallenges = challengeGroupService.findActiveChallenges();

        // Then
        assertNotNull(activeChallenges);
        assertEquals(2, activeChallenges.size(), "There should be 2 active challenge groups");

        // Optional: Print out the names of the active challenge groups
        activeChallenges.forEach(group -> System.out.println(group.getTitle()));
    }

    @Test
    public void testDeleteById() {
        // Given
        ChallengeGroup challengeGroup = new ChallengeGroup();
        challengeGroup.setTitle("테스트 챌린지");
        challengeGroupRepository.save(challengeGroup);

        // When
        challengeGroupService.deleteById(challengeGroup.getChallengeId().toString());

        // Then
        assertThat(challengeGroupRepository.findById(challengeGroup.getChallengeId().toString())).isNull();
    }
}
