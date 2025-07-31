package com.example.grc.repository;

import com.example.grc.domain.Organization;
import com.example.grc.domain.RiskAssessment;
import com.example.grc.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface RiskAssessmentRepository extends JpaRepository<RiskAssessment, Long> {
    boolean existsByAssessmentUid(String assessmentUid);

    Page<RiskAssessment> findByProcessOwner(User processOwner, Pageable pageable);

    Page<RiskAssessment> findByIaManager(User iaManager, Pageable pageable);

    Page<RiskAssessment> findByApprover(User approver, Pageable pageable);

    Page<RiskAssessment> findByAuditableEntity(Organization ae, Pageable pageable);

    Page<RiskAssessment> findAll(Pageable pageable);
}
