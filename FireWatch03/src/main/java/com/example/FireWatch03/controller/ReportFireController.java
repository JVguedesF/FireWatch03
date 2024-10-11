package com.example.FireWatch03.controller;

import com.example.FireWatch03.dto.ReportFireDTO;
import com.example.FireWatch03.model.ReportFire;
import com.example.FireWatch03.service.ReportFireService;
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

    @Autowired
    private ReportFireService reportFireService;

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
        // Converter ReportFireDTO para ReportFire
        ReportFire report = new ReportFire();
        report.setState(reportFireDTO.getState());
        report.setCity(reportFireDTO.getCity());
        report.setLatitude(reportFireDTO.getLatitude());
        report.setLongitude(reportFireDTO.getLongitude());
        report.setPicture(reportFireDTO.getPicture());
        report.setDatetime(reportFireDTO.getDatetime());
        report.setIsAreaClosed(reportFireDTO.getIsAreaClosed());

        ReportFire createdReport = reportFireService.createReport(report);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ReportFireDTO(createdReport));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportFireDTO> updateReport(@PathVariable Long id, @Valid @RequestBody ReportFireDTO reportFireDTO) {
        ReportFire updatedReport = reportFireService.updateReport(id, reportFireDTO);
        return ResponseEntity.ok(new ReportFireDTO(updatedReport));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportFireService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
}