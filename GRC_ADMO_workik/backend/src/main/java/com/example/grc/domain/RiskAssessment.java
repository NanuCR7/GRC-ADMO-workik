package com.example.grc.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "risk_assessments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Auto-generated AE Risk Assessment ID: ADMO/FY/AE_ID/RA1
    @Column(name = "assessment_uid", unique = true, nullable = false, length = 50)
    private String assessmentUid;

    @Column(name = "financial_year", nullable = false)
    private Integer financialYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization auditableEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_owner_id", nullable = false)
    private User processOwner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ia_manager_id", nullable = false)
    private User iaManager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id", nullable = false)
    private User approver;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "active")
    private boolean active = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        if (this.active == false) this.active = true;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
