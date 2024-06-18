package com.mouse.challenge.dto;

import com.mouse.challenge.domain.ChallengeGroup;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengePostCreateRequest {
    private Long userId;
    private Long challengeGroupId;
    private String content;
    private List<MultipartFile> images;
}