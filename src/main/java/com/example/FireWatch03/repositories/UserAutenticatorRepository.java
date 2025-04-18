package com.example.FireWatch03.repositories;

import com.example.FireWatch03.domain.models.UserAutenticator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserAutenticatorRepository extends JpaRepository<UserAutenticator, String> {
    UserDetails findByLogin(String login);
}