package com.teju.greenwoods.api.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teju.greenwoods.application.service.SiteImageService;

@RestController
@RequestMapping("/api/site-images")
public class PublicSiteImageController {

    private final SiteImageService siteImageService;

    public PublicSiteImageController(SiteImageService siteImageService) {
        this.siteImageService = siteImageService;
    }

    @GetMapping
    public Map<String, String> list() {
        return siteImageService.publicUrls();
    }
}