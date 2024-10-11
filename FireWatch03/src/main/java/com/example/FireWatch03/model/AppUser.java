package com.example.FireWatch03.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "app_user")// Nome da tabela no banco de dados
@Getter
@Setter
public class AppUser {

    @Id
    @Column(name = "id_user")
    private String id;

    private String name;
    private String email;
    private String password;
    private String phoneNumber; // Ajustado o nome do atributo
    private Double longitude;
    private Double latitude;

}