package com.teju.greenwoods.domain.entity;

import java.time.LocalDateTime;
import java.time.LocalDate;

import com.teju.greenwoods.domain.enums.EnquirySource;
import com.teju.greenwoods.domain.enums.EnquiryStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "enquiries")
public class Enquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(length = 150)
    private String email;

    @Column(name = "interested_in", length = 100)
    private String interestedIn;

    @Column(length = 100)
    private String budget;

    @Column(name = "best_time_to_call", length = 100)
    private String bestTimeToCall;

    @Column(length = 1000)
    private String message;

    @Column(name = "home_loan_required")
    private Boolean homeLoanRequired;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EnquirySource source;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EnquiryStatus status;

    @Column(length = 1000)
    private String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = EnquiryStatus.NEW;
        }
    }
    
    @Column(name = "visit_date")
    private LocalDate visitDate;

    @Column(name = "time_slot", length = 50)
    private String timeSlot;

    private Integer visitors;

    
}