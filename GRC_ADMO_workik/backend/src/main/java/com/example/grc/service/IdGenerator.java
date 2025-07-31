package com.example.grc.util;

import com.example.grc.repository.RiskAssessmentRepository;
import com.example.grc.repository.AeObjectiveRepository;
import com.example.grc.repository.AeActivityRepository;
import com.example.grc.repository.RiskRepository;
import com.example.grc.repository.ActionPlanRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdGenerator {

    private final RiskAssessmentRepository riskAssessmentRepository;
    private final AeObjectiveRepository aeObjectiveRepository;
    private final AeActivityRepository aeActivityRepository;
    private final RiskRepository riskRepository;
    private final ActionPlanRepository actionPlanRepository;

    public String generateRiskAssessmentUid(int financialYear, String aeCode) {
        // Find existing max sequence number
        int maxSeq = 0;
        var allAssessments = riskAssessmentRepository.findAll();
        final String prefix = "ADMO/" + financialYear + "/" + aeCode + "/RA";

        for (var ra : allAssessments) {
            if (ra.getAssessmentUid() != null && ra.getAssessmentUid().startsWith(prefix)) {
                String seqStr = ra.getAssessmentUid().substring(prefix.length());
                try {
                    int seq = Integer.parseInt(seqStr);
                    if (seq > maxSeq) maxSeq = seq;
                } catch (NumberFormatException ignored) {}
            }
        }

        int nextSeq = maxSeq + 1;
        return prefix + nextSeq;
    }

    public String generateAeObjectiveUid(String aeCode, int financialYear) {
        int maxSeq = 0;
        var allObjectives = aeObjectiveRepository.findAll();
        final String prefix = aeCode + "/" + financialYear + "/O";

        for (var obj : allObjectives) {
            if (obj.getObjectiveUid() != null && obj.getObjectiveUid().startsWith(prefix)) {
                String seqStr = obj.getObjectiveUid().substring(prefix.length());
                try {
                    int seq = Integer.parseInt(seqStr);
                    if (seq > maxSeq) maxSeq = seq;
                } catch (NumberFormatException ignored) {}
            }
        }
        int nextSeq = maxSeq + 1;
        return prefix + nextSeq;
    }

    public String generateAeActivityUid(String aeCode, int financialYear) {
        int maxSeq = 0;
        var allActivities = aeActivityRepository.findAll();
        final String prefix = aeCode + "/" + financialYear + "/A";

        for (var act : allActivities) {
            if (act.getActivityUid() != null && act.getActivityUid().startsWith(prefix)) {
                String seqStr = act.getActivityUid().substring(prefix.length());
                try {
                    int seq = Integer.parseInt(seqStr);
                    if (seq > maxSeq) maxSeq = seq;
                } catch (NumberFormatException ignored) {}
            }
        }
        int nextSeq = maxSeq + 1;
        return prefix + nextSeq;
    }

    public String generateRiskUid(int financialYear, String aeCode) {
        int maxSeq = 0;
        var allRisks = riskRepository.findAll();
        final String prefix = "ADMO/" + financialYear + "/" + aeCode + "/R";

        for (var risk : allRisks) {
            if (risk.getRiskUid() != null && risk.getRiskUid().startsWith(prefix)) {
                String seqStr = risk.getRiskUid().substring(prefix.length());
                try {
                    int seq = Integer.parseInt(seqStr);
                    if (seq > maxSeq) maxSeq = seq;
                } catch (NumberFormatException ignored) {}
            }
        }
        int nextSeq = maxSeq + 1;
        return prefix + nextSeq;
    }

    public String generateActionPlanUid() {
        int maxSeq = 0;
        var allActions = actionPlanRepository.findAll();
        final String prefix = "AP";

        for (var ap : allActions) {
            String apId = ap.getActionPlanUid();
            if (apId != null && apId.startsWith(prefix)) {
                String seqStr = apId.substring(prefix.length());
                try {
                    int seq = Integer.parseInt(seqStr);
                    if (seq > maxSeq) maxSeq = seq;
                } catch (NumberFormatException ignored) {}
            }
        }
        int nextSeq = maxSeq + 1;
        return prefix + String.format("%05d", nextSeq);
    }
}
