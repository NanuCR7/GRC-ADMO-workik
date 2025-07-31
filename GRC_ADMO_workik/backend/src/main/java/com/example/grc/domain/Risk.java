package com.example.grc.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "risks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Risk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Auto generated ID: ADMO/FY/AE_ID/R1 style
    @Column(name = "risk_uid", unique = true, nullable = false, length = 50)
    private String riskUid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "risk_assessment_id", nullable = false)
    private RiskAssessment riskAssessment;

    // Linked either to AEObjective or AEActivity using relational optional fields
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ae_objective_id")
    private AeObjective aeObjective;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ae_activity_id")
    private AeActivity aeActivity;

    @Column(name = "risk_scenario_title", length = 1000)
    private String riskScenarioTitle;

    @Column(name = "risk_description", length = 2000)
    private String riskDescriptionStatement;

    @Column(name = "root_cause", length = 2000)
    private String rootCause;

    @Column(name = "consequences", length = 2000)
    private String consequences;

    @Column(name = "risk_type", length = 100)
    private String riskType;

    @Column(name = "risk_owner", length = 100)
    private String riskOwner;

    // Likelihood (1-5)
    @Column(name = "likelihood")
    private Integer likelihood;

    // Impact (1-5)
    @Column(name = "impact")
    private Integer impact;

    // Calculated Risk Value (Likelihood + Impact)
    @Column(name = "risk_value")
    private Integer riskValue;

    @Column(name = "risk_rating", length = 20)
    private String riskRating;

    // Residual risk fields
    @Column(name = "standard_expected_controls", length = 1000)
    private String standardExpectedControls;

    @Column(name = "control_type", length = 100)
    private String controlType;

    @Column(name = "current_control_evaluation")
    private Integer currentControlEvaluation;

    @Column(name = "control_rating", length = 20)
    private String controlRating;

    @Column(name = "residual_risk_rating", length = 20)
    private String residualRiskRating;

    @Column(name = "audit_recommendation", length = 2000)
    private String auditRecommendation;

    @Column(name = "status", length = 20)
    private String status; // e.g., Draft, Submitted, Locked

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
        calculateRiskValueAndRating();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
        calculateRiskValueAndRating();
    }

    public void calculateRiskValueAndRating() {
        if (likelihood != null && impact != null) {
            this.riskValue = likelihood + impact;
            this.riskRating = com.example.grc.util.RiskRatingMatrix.getRiskRating(likelihood, impact);
            this.controlRating = com.example.grc.util.RiskRatingMatrix.getControlRating(currentControlEvaluation);
            this.residualRiskRating = com.example.grc.util.RiskRatingMatrix.getResidualRiskRating(riskValue, currentControlEvaluation);
        } else {
            this.riskValue = null;
            this.riskRating = null;
            this.controlRating = null;
            this.residualRiskRating = null;
        }
    }
}
