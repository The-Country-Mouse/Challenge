package com.mouse.challenge.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengePostUpdateRequest {
    private Long postId;
    private String userId;
    private Long challengeGroupId;
    private String content;
    private List<MultipartFile> images;
}
