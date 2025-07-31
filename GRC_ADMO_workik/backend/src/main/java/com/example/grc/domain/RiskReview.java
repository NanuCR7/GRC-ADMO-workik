package com.example.grc.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "risk_reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "risk_id", nullable = false)
    private Risk risk;

    @Column(name = "stage", length = 50, nullable = false)
    private String stage;

    @Column(name = "reviewer_username", length = 100, nullable = false)
    private String reviewerUsername;

    @Column(name = "status", length = 20, nullable = false)
    private String status; // Accepted, Declined, Pending

    @Column(name = "remarks", length = 2000)
    private String remarks;

    @Column(name = "review_date")
    private LocalDateTime reviewDate;

    @PrePersist
    public void prePersist() {
        if (reviewDate == null) {
            reviewDate = LocalDateTime.now();
        }
    }
}
