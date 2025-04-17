package com.example.FireWatch03.domain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "report_fire")
public class ReportFire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_report_fire")
    private Long id;

    private String state;
    private String city;
    private Double latitude;
    private Double longitude;

    @Lob
    private byte[] picture;

    private Date datetime;

    @Column(name = "is_area_closed", columnDefinition = "CHAR(1) DEFAULT 'N'")
    private char isAreaClosed = 'N';

    @Column(name = "is_deleted", columnDefinition = "CHAR(1) DEFAULT 'N'")
    private char isDeleted = 'N';

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;
}