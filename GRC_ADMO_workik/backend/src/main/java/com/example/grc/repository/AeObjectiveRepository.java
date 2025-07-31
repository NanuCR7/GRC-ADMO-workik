package com.example.grc.repository;

import com.example.grc.domain.AeObjective;
import com.example.grc.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AeObjectiveRepository extends JpaRepository<AeObjective, Long> {

    List<AeObjective> findByAuditableEntityAndFinancialYear(Organization organization, Integer financialYear);

    boolean existsByObjectiveUid(String objectiveUid);
}
