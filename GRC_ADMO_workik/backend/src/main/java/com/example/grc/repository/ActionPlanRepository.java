package com.example.grc.repository;

import com.example.grc.domain.ActionPlan;
import com.example.grc.domain.RiskAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionPlanRepository extends JpaRepository<ActionPlan, Long> {

    List<ActionPlan> findByRiskAssessment(RiskAssessment riskAssessment);

    boolean existsByActionPlanUid(String actionPlanUid);
}
