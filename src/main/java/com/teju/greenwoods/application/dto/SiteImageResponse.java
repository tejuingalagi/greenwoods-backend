package com.teju.greenwoods.application.dto;

import java.time.LocalDateTime;

public record SiteImageResponse(
        String slot,
        String imageKey,
        String imageUrl,
        String altText,
        LocalDateTime updatedAt
) {
}