package com.teju.greenwoods.api.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.teju.greenwoods.application.dto.MediaResponse;
import com.teju.greenwoods.application.exception.InvalidRequestException;
import com.teju.greenwoods.application.service.MediaService;
import com.teju.greenwoods.domain.enums.MediaFolder;

@RestController
@RequestMapping("/api/admin/media")
public class AdminMediaController {

    private final MediaService mediaService;

    public AdminMediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MediaResponse> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("folder") MediaFolder folder) {

        byte[] content;
        try {
            content = file.getBytes();
        } catch (IOException e) {
            throw new InvalidRequestException("The file could not be read");
        }

        MediaResponse response = mediaService.uploadImage(content, folder);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}