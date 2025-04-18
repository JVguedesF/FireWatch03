package com.example.FireWatch03.repositories;

import com.example.FireWatch03.domain.models.ReportFire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReportFireRepository extends JpaRepository<ReportFire, Long> {
    List<ReportFire> findByIsDeleted(char isDeleted);
}