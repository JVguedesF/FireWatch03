package com.example.FireWatch03.domain.dto;

public record ApiResponse<T>(boolean success, T data, String message) {
}