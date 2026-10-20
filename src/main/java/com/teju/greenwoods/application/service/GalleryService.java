package com.teju.greenwoods.application.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teju.greenwoods.application.dto.GalleryItemRequest;
import com.teju.greenwoods.application.dto.GalleryItemResponse;
import com.teju.greenwoods.application.dto.GalleryPublicResponse;
import com.teju.greenwoods.application.exception.ResourceNotFoundException;
import com.teju.greenwoods.application.repository.GalleryRepository;
import com.teju.greenwoods.domain.entity.GalleryItem;

@Service
public class GalleryService {

    // Category first (in the order of the enum), then the client's order
    private static final Comparator<GalleryItem> DISPLAY_ORDER =
            Comparator.comparing(GalleryItem::getCategory)
                    .thenComparingInt(GalleryItem::getSortOrder)
                    .thenComparing(GalleryItem::getId);

    private final GalleryRepository galleryRepository;
    private final MediaService mediaService;

    public GalleryService(GalleryRepository galleryRepository, MediaService mediaService) {
        this.galleryRepository = galleryRepository;
        this.mediaService = mediaService;
    }

    @Transactional(readOnly = true)
    public List<GalleryPublicResponse> listPublished() {
        return galleryRepository.findByPublishedTrue().stream()
                .sorted(DISPLAY_ORDER)
                .map(this::toPublicResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<GalleryItemResponse> listAll() {
        return galleryRepository.findAll().stream()
                .sorted(DISPLAY_ORDER)
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public GalleryItemResponse create(GalleryItemRequest request) {
        GalleryItem item = new GalleryItem();
        item.setCategory(request.category());
        item.setCaption(request.caption().trim());
        item.setImageKey(request.imageKey());
        item.setPublished(request.published() == null || request.published());

        int nextOrder = galleryRepository
                .findFirstByCategoryOrderBySortOrderDesc(request.category())
                .map(last -> last.getSortOrder() + 1)
                .orElse(0);
        item.setSortOrder(request.sortOrder() != null ? request.sortOrder() : nextOrder);

        return toResponse(galleryRepository.save(item));
    }

    @Transactional
    public GalleryItemResponse update(Long id, GalleryItemRequest request) {
        GalleryItem item = galleryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gallery item " + id + " not found"));

        String oldKey = item.getImageKey();

        item.setCategory(request.category());
        item.setCaption(request.caption().trim());
        item.setImageKey(request.imageKey());
        if (request.published() != null) {
            item.setPublished(request.published());
        }
        if (request.sortOrder() != null) {
            item.setSortOrder(request.sortOrder());
        }

        GalleryItem saved = galleryRepository.save(item);

        // The photo was replaced: remove the old file so storage doesn't fill up
        if (!oldKey.equals(saved.getImageKey())) {
            mediaService.delete(oldKey);
        }
        return toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        GalleryItem item = galleryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gallery item " + id + " not found"));

        String key = item.getImageKey();
        galleryRepository.delete(item);
        mediaService.delete(key);
    }

    private GalleryItemResponse toResponse(GalleryItem item) {
        return new GalleryItemResponse(
                item.getId(),
                item.getCategory(),
                item.getCaption(),
                item.getImageKey(),
                mediaService.urlFor(item.getImageKey()),
                item.getSortOrder(),
                item.isPublished(),
                item.getCreatedAt());
    }

    private GalleryPublicResponse toPublicResponse(GalleryItem item) {
        return new GalleryPublicResponse(
                item.getId(),
                item.getCategory(),
                item.getCaption(),
                mediaService.urlFor(item.getImageKey()));
    }
}