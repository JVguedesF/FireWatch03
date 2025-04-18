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

    public ReportFire createReport(ReportFire report, String appUserId) { // Alterado de Long para String
        AppUser appUser = appUserRepository.findById(appUserId)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado - ID: " + appUserId));
        report.setAppUser(appUser);
        return reportFireRepository.save(report);
    }

    public ReportFire updateReport(Long id, ReportFireDTO reportDetailsDTO, String appUserId) { // Alterado de Long para String
        ReportFire existingReport = reportFireRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Relatório não encontrado - ID: " + id));

        existingReport.updateFromDTO(reportDetailsDTO);

        AppUser appUser = appUserRepository.findById(appUserId)
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