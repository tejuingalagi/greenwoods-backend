package com.teju.greenwoods.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teju.greenwoods.application.repository.GalleryRepository;
import com.teju.greenwoods.domain.entity.GalleryItem;

@Repository
public interface GalleryRepositoryImpl extends JpaRepository<GalleryItem, Long>, GalleryRepository {
}