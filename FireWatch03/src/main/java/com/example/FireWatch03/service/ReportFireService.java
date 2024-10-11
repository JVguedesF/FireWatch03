package com.example.FireWatch03.service;

import com.example.FireWatch03.dto.ReportFireDTO;
import com.example.FireWatch03.model.ReportFire;
import com.example.FireWatch03.repository.ReportFireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReportFireService {

    @Autowired
    private ReportFireRepository reportFireRepository;

    public List<ReportFire> getAllReports() {
        return reportFireRepository.findByIsDeleted('N');
    }

    public ReportFire getReportById(Long id) {
        return reportFireRepository.findById(id) // Correto
                .orElseThrow(() -> new NoSuchElementException("Relatório não encontrado - ID: " + id));
    }

    public ReportFire createReport(ReportFire report) {
        return reportFireRepository.save(report);
    }

    public ReportFire updateReport(Long id, ReportFireDTO reportDetailsDTO) {
        ReportFire existingReport = reportFireRepository.findById(id) // Correto
                .orElseThrow(() -> new NoSuchElementException("Relatório não encontrado - ID: " + id));

        // Atualize os campos de existingReport com os dados de reportDetailsDTO
        existingReport.setState(reportDetailsDTO.getState());
        existingReport.setCity(reportDetailsDTO.getCity());
        existingReport.setLatitude(reportDetailsDTO.getLatitude());
        existingReport.setLongitude(reportDetailsDTO.getLongitude());
        existingReport.setPicture(reportDetailsDTO.getPicture());
        existingReport.setDatetime(reportDetailsDTO.getDatetime());
        existingReport.setIsAreaClosed(reportDetailsDTO.getIsAreaClosed());

        return reportFireRepository.save(existingReport);
    }

    public void deleteReport(Long id) {
        ReportFire report = reportFireRepository.findById(id) // Correto
                .orElseThrow(() -> new NoSuchElementException("Relatório não encontrado - ID: " + id));
        report.setIsDeleted('Y');
        reportFireRepository.save(report);
    }
}