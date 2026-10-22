package com.teju.greenwoods.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teju.greenwoods.application.repository.SiteImageRepository;
import com.teju.greenwoods.domain.entity.SiteImage;

@Repository
public interface SiteImageRepositoryImpl extends JpaRepository<SiteImage, Long>, SiteImageRepository {
}