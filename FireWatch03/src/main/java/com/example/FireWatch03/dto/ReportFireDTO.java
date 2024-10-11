package com.example.FireWatch03.dto;

import com.example.FireWatch03.model.ReportFire;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;
import java.util.Date;

@Getter
@Setter
public class ReportFireDTO {

    private Long id;
    private String state;
    private String city;
    private Double latitude;
    private Double longitude;
    private Blob picture;
    private Date datetime;
    private char isAreaClosed;

    // Altere para Long
    private Long appUserId;

    // Construtor padrão (vazio)
    public ReportFireDTO() {}

    // Construtor que recebe um ReportFire
    public ReportFireDTO(ReportFire reportFire) {
        this.id = reportFire.getId();
        this.state = reportFire.getState();
        this.city = reportFire.getCity();
        this.latitude = reportFire.getLatitude();
        this.longitude = reportFire.getLongitude();
        this.picture = reportFire.getPicture();
        this.datetime = reportFire.getDatetime();
        this.isAreaClosed = reportFire.getIsAreaClosed();
    }
}