package com.example.FireWatch03.domain.models;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("user"),
    ADMIN("admin");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

}
