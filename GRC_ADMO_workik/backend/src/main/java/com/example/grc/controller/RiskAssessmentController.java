package com.example.grc.controller;

import com.example.grc.domain.RiskAssessment;
import com.example.grc.service.RiskAssessmentService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/risk-assessments")
@RequiredArgsConstructor
@Validated
public class RiskAssessmentController {

    private final RiskAssessmentService riskAssessmentService;

    @GetMapping
    public ResponseEntity<Page<RiskAssessment>> listRiskAssessments(Pageable pageable) {
        Page<RiskAssessment> page = riskAssessmentService.getAllRiskAssessments(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RiskAssessment> getRiskAssessment(@PathVariable Long id) {
        Optional<RiskAssessment> ra = riskAssessmentService.getRiskAssessmentById(id);
        return ra.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RiskAssessment> createRiskAssessment(@Valid @RequestBody RiskAssessment riskAssessment) {
        RiskAssessment created = riskAssessmentService.createRiskAssessment(riskAssessment);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RiskAssessment> updateRiskAssessment(@PathVariable Long id, @Valid @RequestBody RiskAssessment riskAssessment) {
        RiskAssessment updated = riskAssessmentService.updateRiskAssessment(id, riskAssessment);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRiskAssessment(@PathVariable Long id) {
        riskAssessmentService.deleteRiskAssessment(id);
        return ResponseEntity.noContent().build();
    }
}
