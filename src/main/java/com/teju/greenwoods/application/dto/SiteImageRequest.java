package com.teju.greenwoods.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SiteImageRequest(

        @NotBlank(message = "Image is required")
        @Pattern(regexp = "^(hero|amenities|floor-plans|site)/[0-9a-fA-F-]{36}\\.(jpg|png|webp)$",
                 message = "Invalid image reference")
        String imageKey,

        @Size(max = 150, message = "Description must be at most 150 characters")
        String altText
) {
}