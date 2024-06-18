package com.mouse.challenge.dto;

import com.mouse.challenge.domain.Image;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengePostUpdateRequest {
    private Long postId;
    private Long userId;
    private Long challengeGroupId;
    private String content;
    private List<MultipartFile> images;
}
