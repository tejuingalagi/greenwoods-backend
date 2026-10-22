package com.teju.greenwoods.application.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teju.greenwoods.application.dto.SiteImageRequest;
import com.teju.greenwoods.application.dto.SiteImageResponse;
import com.teju.greenwoods.application.exception.InvalidRequestException;
import com.teju.greenwoods.application.exception.ResourceNotFoundException;
import com.teju.greenwoods.application.repository.SiteImageRepository;
import com.teju.greenwoods.domain.entity.SiteImage;

@Service
public class SiteImageService {

	private static final Set<String> FIXED_SLOTS = Set.of(
	        "HERO_1", "HERO_2", "HERO_3",
	        "MASTER_PLAN",
	        "AMENITIES_CLUBHOUSE", "AMENITIES_POOL"
	);

	private static final String AMENITY_SLOT = "^AMENITY_[1-9][0-9]?$";

    // One slot per floor plan, named FLOOR_PLAN_ plus the plan name
    private static final String FLOOR_PLAN_SLOT = "^FLOOR_PLAN_[A-Z0-9-]{3,40}$";

    private final SiteImageRepository siteImageRepository;
    private final MediaService mediaService;

    public SiteImageService(SiteImageRepository siteImageRepository, MediaService mediaService) {
        this.siteImageRepository = siteImageRepository;
        this.mediaService = mediaService;
    }

    /** For visitors: slot name to temporary link. */
    @Transactional(readOnly = true)
    public Map<String, String> publicUrls() {
        Map<String, String> urls = new LinkedHashMap<>();
        for (SiteImage image : siteImageRepository.findAll()) {
            urls.put(image.getSlot(), mediaService.urlFor(image.getImageKey()));
        }
        return urls;
    }

    @Transactional(readOnly = true)
    public List<SiteImageResponse> listAll() {
        return siteImageRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public SiteImageResponse set(String slot, SiteImageRequest request) {
        requireValidSlot(slot);

        SiteImage image = siteImageRepository.findBySlot(slot).orElseGet(() -> {
            SiteImage created = new SiteImage();
            created.setSlot(slot);
            return created;
        });

        String oldKey = image.getImageKey(); // null for a new slot
        image.setImageKey(request.imageKey());
        image.setAltText(blankToNull(request.altText()));
        SiteImage saved = siteImageRepository.save(image);

        if (oldKey != null && !oldKey.equals(saved.getImageKey())) {
            mediaService.delete(oldKey); // the picture was replaced
        }
        return toResponse(saved);
    }

    @Transactional
    public void remove(String slot) {
        requireValidSlot(slot);

        SiteImage image = siteImageRepository.findBySlot(slot)
                .orElseThrow(() -> new ResourceNotFoundException("No image is set for " + slot));

        String key = image.getImageKey();
        siteImageRepository.delete(image);
        mediaService.delete(key);
    }

    private void requireValidSlot(String slot) {
        boolean valid = slot != null
                && (FIXED_SLOTS.contains(slot)
                    || slot.matches(FLOOR_PLAN_SLOT)
                    || slot.matches(AMENITY_SLOT));

        if (!valid) {
            throw new InvalidRequestException("Unknown image slot");
        }
    }

    private static String blankToNull(String text) {
        return text == null || text.isBlank() ? null : text.trim();
    }

    private SiteImageResponse toResponse(SiteImage image) {
        return new SiteImageResponse(
                image.getSlot(),
                image.getImageKey(),
                mediaService.urlFor(image.getImageKey()),
                image.getAltText(),
                image.getUpdatedAt());
    }
}