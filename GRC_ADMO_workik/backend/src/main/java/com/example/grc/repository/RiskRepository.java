package com.example.grc.repository;

import com.example.grc.domain.Risk;
import com.example.grc.domain.RiskAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiskRepository extends JpaRepository<Risk, Long> {
    List<Risk> findByRiskAssessment(RiskAssessment riskAssessment);

    boolean existsByRiskUid(String riskUid);
}
