package com.mouse.challenge.service;

import com.mouse.challenge.domain.ChallengeGroup;
import com.mouse.challenge.domain.ChallengePost;
import com.mouse.challenge.domain.Image;
import com.mouse.challenge.domain.User;
import com.mouse.challenge.dto.ChallengePostCreateRequest;
import com.mouse.challenge.dto.ChallengePostUpdateRequest;
import com.mouse.challenge.repository.ChallengePostRepository;
import com.mouse.challenge.repository.UserRepository;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ChallengePostService {

    private final ChallengePostRepository challengePostRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    @Autowired
    public ChallengePostService(ChallengePostRepository challengePostRepository, UserRepository userRepository, ImageService imageService) {
        this.challengePostRepository = challengePostRepository;
        this.userRepository = userRepository;
        this.imageService = imageService;
    }

    @Transactional
    public ChallengePost uploadPost(ChallengePostCreateRequest request)
        throws IOException {
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        ChallengeGroup challengeGroup = null;
        if (request.getChallengeGroupId() != null) {
            challengeGroup = new ChallengeGroup();
            challengeGroup.setId(request.getChallengeGroupId());
        }

        ChallengePost challengePost = new ChallengePost();
        challengePost.setUser(user);
        challengePost.setChallengeGroup(challengeGroup);
        challengePost.setContent(request.getContent());
        challengePost.setCreateDate(LocalDateTime.now());

        List<Image> uploadedImages = new ArrayList<>();
        for (MultipartFile file : request.getImages()) {
            String imageUrl = imageService.upload(file);

            Image image = new Image(file.getOriginalFilename(), imageUrl, challengePost);

            uploadedImages.add(image);
        }

        challengePost.setImages(uploadedImages);

        return challengePostRepository.save(challengePost);
    }

    public ChallengePost getPost(Long postId) {
        return challengePostRepository.findByIdFetchJoin(postId);
    }

    public List<ChallengePost> getAllPost() {
        return challengePostRepository.findAllFetchJoin();
    }

    @Transactional
    public void updatePost(ChallengePostUpdateRequest request) throws IOException {
        if (request.getPostId() == null) {
            throw new IllegalArgumentException("Post ID must not be null");
        }

        ChallengePost existingPost = challengePostRepository.findById(request.getPostId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));

        existingPost.setContent(request.getContent());

        existingPost.getImages().clear();

        if (request.getImages() != null && !request.getImages().isEmpty()) {
            for (MultipartFile file : request.getImages()) {
                String imageUrl = imageService.upload(file);
                Image image = new Image(file.getOriginalFilename(), imageUrl, existingPost);
                existingPost.getImages().add(image);
            }
        }
    }

    @Transactional
    public Long deletePost(Long postId) {
        challengePostRepository.deleteById(postId);
        return postId;
    }

}
