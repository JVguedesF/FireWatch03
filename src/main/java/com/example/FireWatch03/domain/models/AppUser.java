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
    @Column(name = "id_user")
    private String id;

    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    @Column(name = "wants_receive_sms")
    private char wantsReceiveSms;

    private Double longitude;
    private Double latitude;

}