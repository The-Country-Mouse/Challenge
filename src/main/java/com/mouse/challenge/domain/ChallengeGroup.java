package com.mouse.challenge.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "challenge_group")
@Getter
@Setter
public class ChallengeGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_id")
    private Long challengeId;

    @OneToMany(mappedBy = "challengeGroup")
    private List<ChallengeCategory> challengeCategories = new ArrayList<>();

    @Column(name = "max_participants")
    private int maxParticipants;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "challenge_image")
    private String challengeImage;

    @Column(name = "group_create_date")
    private Date groupCreateDate;

    @Column(name = "challenge_start_date")
    private Date challengeStartDate;

    @Column(name = "challenge_end_date")
    private Date challengeEndDate;
}
