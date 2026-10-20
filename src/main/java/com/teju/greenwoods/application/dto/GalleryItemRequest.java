package com.teju.greenwoods.application.dto;

import com.teju.greenwoods.domain.enums.GalleryCategory;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record GalleryItemRequest(

        @NotNull(message = "Category is required")
        GalleryCategory category,

        @NotBlank(message = "Caption is required")
        @Size(max = 150, message = "Caption must be at most 150 characters")
        String caption,

        @NotBlank(message = "Image is required")
        @Pattern(regexp = "^gallery/[0-9a-fA-F-]{36}\\.(jpg|png|webp)$",
                 message = "Invalid image reference")
        String imageKey,

        Boolean published,

        @Min(value = 0, message = "Order cannot be negative")
        @Max(value = 9999, message = "Order is too large")
        Integer sortOrder
) {
}