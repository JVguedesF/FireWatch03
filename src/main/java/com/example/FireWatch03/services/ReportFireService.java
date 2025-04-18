package com.example.FireWatch03.services;

import com.example.FireWatch03.domain.dto.ReportFireDTO;
import com.example.FireWatch03.domain.models.AppUser;
import com.example.FireWatch03.domain.models.ReportFire;
import com.example.FireWatch03.repositories.AppUserRepository;
import com.example.FireWatch03.repositories.ReportFireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReportFireService {

    @Autowired
    private ReportFireRepository reportFireRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    public List<ReportFire> getAllReports() {
        return reportFireRepository.findByIsDeleted('N');
    }

    public ReportFire getReportById(Long id) {
        return reportFireRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Relatório não encontrado - ID: " + id));
    }

    public ReportFire createReport(ReportFireDTO reportFireDTO) {
        ReportFire report = new ReportFire();
        report.setState(reportFireDTO.getState());
        report.setCity(reportFireDTO.getCity());
        report.setLatitude(reportFireDTO.getLatitude());
        report.setLongitude(reportFireDTO.getLongitude());
        report.setPicture(reportFireDTO.getPicture());
        report.setDatetime(reportFireDTO.getDatetime());
        report.setIsAreaClosed(reportFireDTO.getIsAreaClosed());

        AppUser appUser = appUserRepository.findById(reportFireDTO.getAppUserId())
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado - ID: " + reportFireDTO.getAppUserId()));
        report.setAppUser(appUser);

        return reportFireRepository.save(report);
    }

    public ReportFire updateReport(Long id, ReportFireDTO reportDetailsDTO, Long appUserId) {
        ReportFire existingReport = reportFireRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Relatório não encontrado - ID: " + id));


        existingReport.setState(reportDetailsDTO.getState());
        existingReport.setCity(reportDetailsDTO.getCity());
        existingReport.setLatitude(reportDetailsDTO.getLatitude());
        existingReport.setLongitude(reportDetailsDTO.getLongitude());
        existingReport.setPicture(reportDetailsDTO.getPicture());
        existingReport.setDatetime(reportDetailsDTO.getDatetime());
        existingReport.setIsAreaClosed(reportDetailsDTO.getIsAreaClosed());


        AppUser appUser = appUserRepository.findById(appUserId) // Correto!
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado - ID: " + appUserId));
        existingReport.setAppUser(appUser);

        return reportFireRepository.save(existingReport);
    }

    public void deleteReport(Long id) {
        ReportFire report = reportFireRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Relatório não encontrado - ID: " + id));
        report.setIsDeleted('Y');
        reportFireRepository.save(report);
    }
}