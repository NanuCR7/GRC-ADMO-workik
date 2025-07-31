package com.example.grc.service;

import com.example.grc.domain.Organization;
import com.example.grc.domain.RiskAssessment;
import com.example.grc.domain.User;
import com.example.grc.exception.ResourceNotFoundException;
import com.example.grc.exception.ValidationException;
import com.example.grc.repository.OrganizationRepository;
import com.example.grc.repository.RiskAssessmentRepository;
import com.example.grc.repository.UserRepository;
import com.example.grc.util.IdGenerator;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RiskAssessmentService {

    private final RiskAssessmentRepository riskAssessmentRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final IdGenerator idGenerator;

    @Transactional(readOnly = true)
    public Page<RiskAssessment> getAllRiskAssessments(Pageable pageable) {
        return riskAssessmentRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<RiskAssessment> getRiskAssessmentById(Long id) {
        return riskAssessmentRepository.findById(id);
    }

    @Transactional
    public RiskAssessment createRiskAssessment(RiskAssessment ra) {
        validateMandatoryFields(ra);

        // Fetch AE code used for ID generation
        Organization ae = organizationRepository.findById(ra.getAuditableEntity().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));
        ra.setAuditableEntity(ae);

        // Verify users
        User processOwner = userRepository.findById(ra.getProcessOwner().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Process Owner not found"));
        User iaManager = userRepository.findById(ra.getIaManager().getId())
                .orElseThrow(() -> new ResourceNotFoundException("IA Manager not found"));
        User approver = userRepository.findById(ra.getApprover().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Approver not found"));
        ra.setProcessOwner(processOwner);
        ra.setIaManager(iaManager);
        ra.setApprover(approver);

        // Generate AE Risk Assessment ID
        String aeCode = ae.getName().replaceAll("\\s+","").toUpperCase();
        String generatedId = idGenerator.generateRiskAssessmentUid(ra.getFinancialYear(), aeCode);
        ra.setAssessmentUid(generatedId);

        // Default status
        if (ra.getStatus() == null) {
            ra.setStatus("Draft");
        }

        return riskAssessmentRepository.save(ra);
    }

    @Transactional
    public RiskAssessment updateRiskAssessment(Long id, RiskAssessment updatedRa) {
        RiskAssessment existing = riskAssessmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Risk Assessment not found"));

        if (!existing.isActive()) {
            throw new ValidationException("Risk Assessment is inactive, cannot update");
        }

        validateMandatoryFields(updatedRa);

        existing.setFinancialYear(updatedRa.getFinancialYear());

        Organization ae = organizationRepository.findById(updatedRa.getAuditableEntity().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));
        existing.setAuditableEntity(ae);

        User processOwner = userRepository.findById(updatedRa.getProcessOwner().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Process Owner not found"));
        existing.setProcessOwner(processOwner);

        User iaManager = userRepository.findById(updatedRa.getIaManager().getId())
                .orElseThrow(() -> new ResourceNotFoundException("IA Manager not found"));
        existing.setIaManager(iaManager);

        User approver = userRepository.findById(updatedRa.getApprover().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Approver not found"));
        existing.setApprover(approver);

        existing.setStatus(updatedRa.getStatus());

        return riskAssessmentRepository.save(existing);
    }

    @Transactional
    public void deleteRiskAssessment(Long id) {
        RiskAssessment existing = riskAssessmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Risk Assessment not found"));

        existing.setActive(false);
        riskAssessmentRepository.save(existing);
    }

    private void validateMandatoryFields(RiskAssessment ra) {
        if (ra.getFinancialYear() == null ||
                ra.getFinancialYear() < 2000 || ra.getFinancialYear() > 2099) {
            throw new ValidationException("Financial Year must be between 2000 and 2099");
        }
        if (ra.getAuditableEntity() == null || ra.getAuditableEntity().getId() == null) {
            throw new ValidationException("Auditable Entity is required");
        }
        if (ra.getProcessOwner() == null || ra.getProcessOwner().getId() == null) {
            throw new ValidationException("Process Owner is required");
        }
        if (ra.getIaManager() == null || ra.getIaManager().getId() == null) {
            throw new ValidationException("IA Manager is required");
        }
        if (ra.getApprover() == null || ra.getApprover().getId() == null) {
            throw new ValidationException("Approver is required");
        }
    }
}
