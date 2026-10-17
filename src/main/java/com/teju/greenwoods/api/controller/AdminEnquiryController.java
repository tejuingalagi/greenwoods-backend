package com.teju.greenwoods.api.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teju.greenwoods.application.dto.EnquiryResponse;
import com.teju.greenwoods.application.dto.EnquiryStatusUpdateRequest;
import com.teju.greenwoods.application.service.EnquiryService;
import com.teju.greenwoods.domain.enums.EnquiryStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/enquiries")
public class AdminEnquiryController {

    private final EnquiryService enquiryService;

    public AdminEnquiryController(EnquiryService enquiryService) {
        this.enquiryService = enquiryService;
    }

    @GetMapping
    public List<EnquiryResponse> list(
            @RequestParam(name = "status", required = false) EnquiryStatus status) {
        return enquiryService.getEnquiries(status);
    }

    @PutMapping("/{id}/status")
    public EnquiryResponse updateStatus(
            @PathVariable("id") Long id,
            @Valid @RequestBody EnquiryStatusUpdateRequest request) {
        return enquiryService.updateStatus(id, request);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        enquiryService.deleteEnquiry(id);
        return ResponseEntity.noContent().build();
    }
}