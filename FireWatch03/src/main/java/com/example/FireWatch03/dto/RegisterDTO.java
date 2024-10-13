package com.example.FireWatch03.dto;

import com.example.FireWatch03.domain.models.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
