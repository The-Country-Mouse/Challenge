package com.mouse.challenge.config.api;

import com.mouse.challenge.entity.ChallengePost;
import com.mouse.challenge.dto.ChallengePostCreateRequest;
import com.mouse.challenge.dto.ChallengePostResponse;
import com.mouse.challenge.dto.ChallengePostUpdateRequest;
import com.mouse.challenge.service.ChallengePostService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenge-posts")
public class ChallengePostApi {

    private final ChallengePostService challengePostService;

    @PostMapping("/save")
    public String createChallengePost(
        @RequestParam("userId") String userId,
        @RequestParam("groupId") Long groupId,
        @RequestParam("content") String content,
        @RequestParam("images") List<MultipartFile> images) throws IOException {

        ChallengePostCreateRequest request = new ChallengePostCreateRequest();
        request.setContent(content);
        request.setUserId(userId);
        request.setChallengeGroupId(groupId);
        request.setImages(images);

        challengePostService.uploadPost(request);

        return "게시글 저장";
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ChallengePostResponse> getChallengePost(@PathVariable("postId") Long postId) {
        ChallengePost post = challengePostService.getPost(postId);

        return ResponseEntity.ok(new ChallengePostResponse(post));
    }

    @GetMapping("/all-posts")
    public ResponseEntity<List<ChallengePostResponse>> getAllChallengePost() {
        List<ChallengePost> posts = challengePostService.getAllPost();

        List<ChallengePostResponse> result = posts.stream()
            .map(ChallengePostResponse::new)
            .toList();

        return ResponseEntity.ok(result);
    }

    @PostMapping("/update/{postId}")
    public ResponseEntity<ChallengePostResponse> updateChallengePost(
        @PathVariable("postId") Long postId,
        @RequestParam("content") String content,
        @RequestParam("images") List<MultipartFile> images) throws IOException {

        ChallengePostUpdateRequest request = new ChallengePostUpdateRequest();
        request.setPostId(postId);
        request.setContent(content);
        request.setImages(images);

        challengePostService.updatePost(request);

        ChallengePost post = challengePostService.getPost(postId);

        return ResponseEntity.ok(new ChallengePostResponse(post));
    }


    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Long> deleteChallengePost(@PathVariable("postId") Long postId) {
        Long deletedPostId = challengePostService.deletePost(postId);

        return ResponseEntity.ok(deletedPostId);
    }

}
