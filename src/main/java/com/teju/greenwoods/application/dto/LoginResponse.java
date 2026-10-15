package com.teju.greenwoods.application.dto;

public record LoginResponse(String token, String email, String role) {
}