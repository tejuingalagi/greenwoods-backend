package com.teju.greenwoods.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teju.greenwoods.application.dto.GalleryItemRequest;
import com.teju.greenwoods.application.dto.GalleryItemResponse;
import com.teju.greenwoods.application.service.GalleryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/gallery")
public class AdminGalleryController {

    private final GalleryService galleryService;

    public AdminGalleryController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }

    @GetMapping
    public List<GalleryItemResponse> list() {
        return galleryService.listAll();
    }

    @PostMapping
    public ResponseEntity<GalleryItemResponse> create(@Valid @RequestBody GalleryItemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(galleryService.create(request));
    }

    @PutMapping("/{id}")
    public GalleryItemResponse update(@PathVariable("id") Long id,
                                      @Valid @RequestBody GalleryItemRequest request) {
        return galleryService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        galleryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}