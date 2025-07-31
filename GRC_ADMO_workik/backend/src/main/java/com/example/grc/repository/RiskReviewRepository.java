package com.example.grc.repository;

import com.example.grc.domain.RiskReview;
import com.example.grc.domain.Risk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiskReviewRepository extends JpaRepository<RiskReview, Long> {
    List<RiskReview> findByRisk(Risk risk);
}
