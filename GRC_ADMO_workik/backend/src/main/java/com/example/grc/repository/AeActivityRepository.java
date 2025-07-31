package com.example.grc.repository;

import com.example.grc.domain.AeActivity;
import com.example.grc.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AeActivityRepository extends JpaRepository<AeActivity, Long> {

    List<AeActivity> findByAuditableEntityAndFinancialYear(Organization organization, Integer financialYear);

    boolean existsByActivityUid(String activityUid);
}
