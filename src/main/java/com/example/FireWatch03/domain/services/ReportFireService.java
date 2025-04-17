package com.example.FireWatch03.domain.services;

import com.example.FireWatch03.dto.ReportFireDTO;
import com.example.FireWatch03.domain.models.AppUser;
import com.example.FireWatch03.domain.models.ReportFire;
import com.example.FireWatch03.domain.repositories.AppUserRepository;
import com.example.FireWatch03.domain.repositories.ReportFireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReportFireService {

    private final ReportFireRepository reportFireRepository;

    private final AppUserRepository appUserRepository;

    @Autowired
    public ReportFireService(ReportFireRepository reportFireRepository, AppUserRepository appUserRepository) {
        this.reportFireRepository = reportFireRepository;
        this.appUserRepository = appUserRepository;
    }

    public List<ReportFire> getAllReports() {
        return reportFireRepository.findByIsDeleted('N');
    }

    public ReportFire getReportById(Long id) {
        return reportFireRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Relatório não encontrado - ID: " + id));
    }

    public ReportFire createReport(ReportFire report, Long appUserId) {
        AppUser appUser = appUserRepository.findById(appUserId)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado - ID: " + appUserId));
        report.setAppUser(appUser); // Define a relação ManyToOne
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