package com.teju.greenwoods.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teju.greenwoods.application.dto.EnquiryRequest;
import com.teju.greenwoods.application.dto.EnquiryResponse;
import com.teju.greenwoods.application.service.EnquiryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/enquiries")
public class EnquiryController {

    private final EnquiryService enquiryService;

    public EnquiryController(EnquiryService enquiryService) {
        this.enquiryService = enquiryService;
    }

    @PostMapping
    public ResponseEntity<EnquiryResponse> create(@Valid @RequestBody EnquiryRequest request) {
        EnquiryResponse response = enquiryService.createEnquiry(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}