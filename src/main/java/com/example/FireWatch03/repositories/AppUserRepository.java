package com.example.FireWatch03.repositories;

import com.example.FireWatch03.domain.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
}