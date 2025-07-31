package com.example.grc.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ae_activities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AeActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // AE_ID/FY/A1 style ID
    @Column(name = "activity_uid", unique = true, nullable = false, length = 50)
    private String activityUid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization auditableEntity;

    @Column(name = "financial_year", nullable = false)
    private Integer financialYear;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "status", length = 20, nullable = false)
    private String status; // Draft or Submitted

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
        if (status == null) {
            status = "Draft";
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
