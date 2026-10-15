package com.teju.greenwoods.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teju.greenwoods.application.repository.EnquiryRepository;
import com.teju.greenwoods.domain.entity.Enquiry;

@Repository
public interface EnquiryRepositoryImpl extends JpaRepository<Enquiry, Long>, EnquiryRepository {
}