package com.example.FireWatch03.repository;

import com.example.FireWatch03.model.ReportFire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReportFireRepository extends JpaRepository<ReportFire, Long> {
    List<ReportFire> findByIsDeleted(char isDeleted);
}
