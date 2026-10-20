package com.teju.greenwoods.application.dto;

import com.teju.greenwoods.domain.enums.GalleryCategory;

public record GalleryPublicResponse(
        Long id,
        GalleryCategory category,
        String caption,
        String imageUrl
) {
}