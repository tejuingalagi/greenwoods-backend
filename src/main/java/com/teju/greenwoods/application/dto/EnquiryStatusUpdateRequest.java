package com.teju.greenwoods.application.dto;

import com.teju.greenwoods.domain.enums.EnquiryStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EnquiryStatusUpdateRequest(

        @NotNull(message = "Status is required")
        EnquiryStatus status,

        @Size(max = 1000, message = "Notes must be at most 1000 characters")
        String notes
) {
}