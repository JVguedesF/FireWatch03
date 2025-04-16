package com.example.FireWatch03.dto;

import lombok.Getter;

@Getter
public class ErrorDTO {
    private String message;

    public ErrorDTO(String message) {
        this.message = message;
    }
}