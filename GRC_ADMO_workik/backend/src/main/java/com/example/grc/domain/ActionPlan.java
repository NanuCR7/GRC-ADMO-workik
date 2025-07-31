package com.example.grc.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "action_plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "action_plan_uid", unique = true, nullable = false, length = 50)
    private String actionPlanUid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "risk_assessment_id", nullable = false)
    private RiskAssessment riskAssessment;

    @Column(name = "description", length = 2000, nullable = false)
    private String description;

    @Column(name = "status", length = 20, nullable = false)
    private String status; // Open/In Progress/On Hold/Closed/Delayed

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "action_plan_users",
        joinColumns = @JoinColumn(name = "action_plan_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> partiesResponsible;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
        if (status == null) {
            status = "Open";
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
