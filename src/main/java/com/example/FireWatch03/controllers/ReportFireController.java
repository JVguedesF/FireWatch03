package com.example.FireWatch03.controllers;

import com.example.FireWatch03.domain.dto.ReportFireDTO;
import com.example.FireWatch03.domain.models.AppUser;
import com.example.FireWatch03.domain.models.ReportFire;
import com.example.FireWatch03.services.ReportFireService;
import com.example.FireWatch03.repositories.AppUserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/report-fires")
public class ReportFireController {

    private final ReportFireService reportFireService;
    private final AppUserRepository appUserRepository;

    @Autowired
    public ReportFireController(ReportFireService reportFireService, AppUserRepository appUserRepository) {
        this.reportFireService = reportFireService;
        this.appUserRepository = appUserRepository;
    }

    @GetMapping
    public List<ReportFireDTO> getAllReports() {
        return reportFireService.getAllReports().stream()
                .map(ReportFireDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ReportFireDTO getReportById(@PathVariable Long id) {
        ReportFire report = reportFireService.getReportById(id);
        return new ReportFireDTO(report);
    }

    @PostMapping
    public ResponseEntity<ReportFireDTO> createReport(@Valid @RequestBody ReportFireDTO reportFireDTO) {
        ReportFire report = new ReportFire();
        report.setState(reportFireDTO.getState());
        report.setCity(reportFireDTO.getCity());
        report.setLatitude(reportFireDTO.getLatitude());
        report.setLongitude(reportFireDTO.getLongitude());
        report.setPicture(reportFireDTO.getPicture());
        report.setDatetime(reportFireDTO.getDatetime());
        report.setIsAreaClosed(reportFireDTO.isAreaClosed() ? 'Y' : 'N');

        // Use o appUserId do DTO para buscar o usuário
        AppUser appUser = appUserRepository.findById(reportFireDTO.getAppUserId())
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado - ID: " + reportFireDTO.getAppUserId()));
        report.setAppUser(appUser);

        ReportFire createdReport = reportFireService.createReport(report, reportFireDTO.getAppUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ReportFireDTO(createdReport));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportFireDTO> updateReport(@PathVariable Long id, @Valid @RequestBody ReportFireDTO reportFireDTO) {
        ReportFire updatedReport = reportFireService.updateReport(id, reportFireDTO, reportFireDTO.getAppUserId());
        return ResponseEntity.ok(new ReportFireDTO(updatedReport));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportFireService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
}