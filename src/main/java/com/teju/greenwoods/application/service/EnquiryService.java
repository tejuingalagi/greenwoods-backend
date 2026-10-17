package com.teju.greenwoods.application.service;

import org.springframework.stereotype.Service;
import java.time.LocalDate;

import com.teju.greenwoods.application.exception.InvalidRequestException;
import com.teju.greenwoods.domain.enums.EnquirySource;
import org.springframework.transaction.annotation.Transactional;
import com.teju.greenwoods.application.dto.EnquiryRequest;
import com.teju.greenwoods.application.dto.EnquiryResponse;
import com.teju.greenwoods.application.repository.EnquiryRepository;
import com.teju.greenwoods.domain.entity.Enquiry;
import java.util.List;

import com.teju.greenwoods.application.dto.EnquiryStatusUpdateRequest;
import com.teju.greenwoods.application.exception.ResourceNotFoundException;
import com.teju.greenwoods.domain.enums.EnquiryStatus;

@Service
public class EnquiryService {

    private final EnquiryRepository enquiryRepository;

    public EnquiryService(EnquiryRepository enquiryRepository) {
        this.enquiryRepository = enquiryRepository;
    }

    @Transactional
    public EnquiryResponse createEnquiry(EnquiryRequest request) {
        validateSiteVisit(request);

        Enquiry enquiry = new Enquiry();
        enquiry.setName(request.name().trim());
        enquiry.setPhone(request.phone().trim());
        enquiry.setEmail(request.email());
        enquiry.setInterestedIn(request.interestedIn());
        enquiry.setBudget(request.budget());
        enquiry.setBestTimeToCall(request.bestTimeToCall());
        enquiry.setMessage(request.message());
        enquiry.setHomeLoanRequired(request.homeLoanRequired());
        enquiry.setSource(request.source());

        if (request.source() == EnquirySource.SITE_VISIT) {
            enquiry.setVisitDate(request.visitDate());
            enquiry.setTimeSlot(request.timeSlot().trim());
            enquiry.setVisitors(request.visitors() != null ? request.visitors() : 1);
        }

        Enquiry saved = enquiryRepository.save(enquiry);
        return toResponse(saved);
    }

    private void validateSiteVisit(EnquiryRequest request) {
        if (request.source() != EnquirySource.SITE_VISIT) {
            return;
        }
        if (request.visitDate() == null) {
            throw new InvalidRequestException("Preferred visit date is required");
        }
        if (request.visitDate().isBefore(LocalDate.now())) {
            throw new InvalidRequestException("Visit date cannot be in the past");
        }
        if (request.timeSlot() == null || request.timeSlot().isBlank()) {
            throw new InvalidRequestException("Time slot is required");
        }
    }

    private EnquiryResponse toResponse(Enquiry e) {
        return new EnquiryResponse(
                e.getId(),
                e.getName(),
                e.getPhone(),
                e.getEmail(),
                e.getInterestedIn(),
                e.getBudget(),
                e.getBestTimeToCall(),
                e.getMessage(),
                e.getHomeLoanRequired(),
                e.getSource(),
                e.getStatus(),
                e.getNotes(),
                e.getCreatedAt(),
                e.getVisitDate(),
                e.getTimeSlot(),
                e.getVisitors());
    }
    
    @Transactional(readOnly = true)
    public List<EnquiryResponse> getEnquiries(EnquiryStatus status) {
        List<Enquiry> enquiries = (status == null)
                ? enquiryRepository.findAllByOrderByCreatedAtDesc()
                : enquiryRepository.findByStatusOrderByCreatedAtDesc(status);
        return enquiries.stream().map(this::toResponse).toList();
    }

    @Transactional
    public EnquiryResponse updateStatus(Long id, EnquiryStatusUpdateRequest request) {
        Enquiry enquiry = enquiryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enquiry " + id + " not found"));

        enquiry.setStatus(request.status());

        if (request.notes() != null) {
            String notes = request.notes().trim();
            enquiry.setNotes(notes.isEmpty() ? null : notes);
        }

        return toResponse(enquiryRepository.save(enquiry));
    }
}