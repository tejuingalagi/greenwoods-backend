package com.teju.greenwoods.application.dto;

import java.time.LocalDateTime;

import com.teju.greenwoods.domain.enums.EnquirySource;
import com.teju.greenwoods.domain.enums.EnquiryStatus;

public record EnquiryResponse(
        Long id,
        String name,
        String phone,
        String email,
        String interestedIn,
        String budget,
        String bestTimeToCall,
        String message,
        Boolean homeLoanRequired,
        EnquirySource source,
        EnquiryStatus status,
        String notes,
        LocalDateTime createdAt
) {
}