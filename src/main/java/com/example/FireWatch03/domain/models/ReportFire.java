package com.example.FireWatch03.domain.models;

import com.example.FireWatch03.domain.dto.ReportFireDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @JoinColumn(name = "user_id_user")
    private AppUser appUser;


    public void updateFromDTO(ReportFireDTO dto) {
        this.state = dto.getState();
        this.city = dto.getCity();
        this.latitude = dto.getLatitude();
        this.longitude = dto.getLongitude();
        this.picture = dto.getPicture();
        this.datetime = dto.getDatetime();
        this.isAreaClosed = dto.isAreaClosed() ? 'Y' : 'N';
    }
}