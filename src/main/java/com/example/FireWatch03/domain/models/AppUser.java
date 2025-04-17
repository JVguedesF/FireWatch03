package com.example.FireWatch03.domain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "app_user")
@Getter
@Setter
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    @Column(name = "wants_receive_sms", columnDefinition = "CHAR(1) DEFAULT 'N'")
    private char wantsReceiveSms = 'N';

    private Double longitude;
    private Double latitude;
}