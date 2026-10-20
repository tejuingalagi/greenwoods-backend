package com.teju.greenwoods.application.dto;

import java.time.LocalDateTime;

import com.teju.greenwoods.domain.enums.GalleryCategory;

public record GalleryItemResponse(
        Long id,
        GalleryCategory category,
        String caption,
        String imageKey,
        String imageUrl,
        int sortOrder,
        boolean published,
        LocalDateTime createdAt
) {
}