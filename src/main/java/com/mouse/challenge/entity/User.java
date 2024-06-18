package com.mouse.challenge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User {

    @Id
    String userId;
    String userName;
    String password;
    String email;
    LocalDateTime regist_date;
    String temp_vl;

    @OneToMany(mappedBy = "user")
    private List<Participant> participants = new ArrayList<>();
}
