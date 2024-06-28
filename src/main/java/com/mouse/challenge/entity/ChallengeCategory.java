package com.mouse.challenge.entity;

import com.mouse.challenge.entity.ChallengeGroup;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "challenge_category")
@Getter
@Setter
public class ChallengeCategory {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private ChallengeGroup challengeGroup;
}
