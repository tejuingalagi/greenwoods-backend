package com.teju.greenwoods.application.repository;

import java.util.List;
import java.util.Optional;

import com.teju.greenwoods.domain.entity.SiteImage;

public interface SiteImageRepository {

    SiteImage save(SiteImage image);

    Optional<SiteImage> findBySlot(String slot);

    List<SiteImage> findAll();

    void delete(SiteImage image);
}