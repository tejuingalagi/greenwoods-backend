package com.teju.greenwoods.application.repository;

import java.util.List;
import java.util.Optional;

import com.teju.greenwoods.domain.entity.GalleryItem;
import com.teju.greenwoods.domain.enums.GalleryCategory;

public interface GalleryRepository {

    GalleryItem save(GalleryItem item);

    Optional<GalleryItem> findById(Long id);

    List<GalleryItem> findAll();

    List<GalleryItem> findByPublishedTrue();

    Optional<GalleryItem> findFirstByCategoryOrderBySortOrderDesc(GalleryCategory category);

    void delete(GalleryItem item);
}