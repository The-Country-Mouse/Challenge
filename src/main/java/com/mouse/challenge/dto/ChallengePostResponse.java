package com.mouse.challenge.dto;

import com.mouse.challenge.domain.ChallengePost;
import com.mouse.challenge.domain.Image;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChallengePostResponse {
    private Long postId;
    private String content;
    private List<String> imageUrls;
    private String userName;
    public ChallengePostResponse(ChallengePost challengePost) {
        postId = challengePost.getId();
        content = challengePost.getContent();
        imageUrls = challengePost.getImages().stream()
            .map(Image::getUniqueName)
            .collect(Collectors.toList());
        userName = challengePost.getUser().getUsername();
    }
}
