package com.teju.greenwoods.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teju.greenwoods.application.dto.SiteImageRequest;
import com.teju.greenwoods.application.dto.SiteImageResponse;
import com.teju.greenwoods.application.service.SiteImageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/site-images")
public class AdminSiteImageController {

    private final SiteImageService siteImageService;

    public AdminSiteImageController(SiteImageService siteImageService) {
        this.siteImageService = siteImageService;
    }

    @GetMapping
    public List<SiteImageResponse> list() {
        return siteImageService.listAll();
    }

    @PutMapping("/{slot}")
    public SiteImageResponse set(@PathVariable("slot") String slot,
                                 @Valid @RequestBody SiteImageRequest request) {
        return siteImageService.set(slot, request);
    }

    @DeleteMapping("/{slot}")
    public ResponseEntity<Void> remove(@PathVariable("slot") String slot) {
        siteImageService.remove(slot);
        return ResponseEntity.noContent().build();
    }
}