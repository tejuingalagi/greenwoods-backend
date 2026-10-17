package com.teju.greenwoods.application.repository;

import java.util.List;
import java.util.Optional;

import com.teju.greenwoods.domain.entity.Enquiry;
import com.teju.greenwoods.domain.enums.EnquiryStatus;

public interface EnquiryRepository {

    Enquiry save(Enquiry enquiry);

    Optional<Enquiry> findById(Long id);

    List<Enquiry> findAllByOrderByCreatedAtDesc();

    List<Enquiry> findByStatusOrderByCreatedAtDesc(EnquiryStatus status);
    
    boolean existsById(Long id);

    void deleteById(Long id);
}