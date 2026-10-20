package com.teju.greenwoods.application.dto;

public record MediaResponse(
        String key,
        String url,
        String contentType,
        long size
) {
}