package com.example.FireWatch03.domain.dto;

import com.example.FireWatch03.domain.models.ReportFire;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportFireDTO {

    private Long id;
    private String state;
    private String city;
    private Double latitude;
    private Double longitude;
    private byte[] picture;
    private Date datetime;
    private boolean isAreaClosed;
    private String appUserId;

    public ReportFireDTO(ReportFire reportFire) {
        this.id = reportFire.getId();
        this.state = reportFire.getState();
        this.city = reportFire.getCity();
        this.latitude = reportFire.getLatitude();
        this.longitude = reportFire.getLongitude();
        this.picture = reportFire.getPicture();
        this.datetime = reportFire.getDatetime();
        this.isAreaClosed = reportFire.getIsAreaClosed() == 'Y';
        this.appUserId = reportFire.getAppUser() != null ? reportFire.getAppUser().getId() : null;
    }
}