package com.teju.greenwoods.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teju.greenwoods.application.dto.GalleryPublicResponse;
import com.teju.greenwoods.application.service.GalleryService;

@RestController
@RequestMapping("/api/gallery")
public class PublicGalleryController {

    private final GalleryService galleryService;

    public PublicGalleryController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }

    @GetMapping
    public List<GalleryPublicResponse> list() {
        return galleryService.listPublished();
    }
}