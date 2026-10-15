package com.teju.greenwoods.application.dto;

import com.teju.greenwoods.domain.enums.EnquirySource;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EnquiryRequest(

        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must be at most 100 characters")
        String name,

        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "^[0-9+\\-\\s]{10,15}$", message = "Enter a valid phone number")
        String phone,

        @Email(message = "Enter a valid email")
        @Size(max = 150)
        String email,

        @Size(max = 100)
        String interestedIn,

        @Size(max = 100)
        String budget,

        @Size(max = 100)
        String bestTimeToCall,

        @Size(max = 1000, message = "Message must be at most 1000 characters")
        String message,

        Boolean homeLoanRequired,

        @NotNull(message = "Source is required")
        EnquirySource source
) {
}