package com.example.grc.util;

import java.util.HashMap;
import java.util.Map;

public class RiskRatingMatrix {

    private static final Map<Integer, String> likelihoodMap = new HashMap<>();
    private static final Map<Integer, String> impactMap = new HashMap<>();

    static {
        likelihoodMap.put(1, "Rare");
        likelihoodMap.put(2, "Unlikely");
        likelihoodMap.put(3, "Possible");
        likelihoodMap.put(4, "Likely");
        likelihoodMap.put(5, "Almost Certain");

        impactMap.put(1, "Insignificant");
        impactMap.put(2, "Minor");
        impactMap.put(3, "Moderate");
        impactMap.put(4, "Significant");
        impactMap.put(5, "Catastrophic");
    }

    public static String getLikelihoodLabel(int likelihood) {
        return likelihoodMap.getOrDefault(likelihood, "Unknown");
    }

    public static String getImpactLabel(int impact) {
        return impactMap.getOrDefault(impact, "Unknown");
    }

    /**
     * Risk Rating: 2-5=Low, 6=Moderate, 7=Significant, 8-10=Extreme
     * Likelihood + Impact is riskValue
     */
    public static String getRiskRating(int likelihood, int impact) {
        int riskValue = likelihood + impact;
        if (riskValue >= 2 && riskValue <= 5) {
            return "Low";
        }
        if (riskValue == 6) {
            return "Moderate";
        }
        if (riskValue == 7) {
            return "Significant";
        }
        if (riskValue >= 8 && riskValue <= 10) {
            return "Extreme";
        }
        return "Unknown";
    }

    /**
     * Control Rating based on current control evaluation 1-10 scale
     * Example:
     * 1-3=Poor, 4-6=Average, 7-8=Good, 9-10=Excellent
     */
    public static String getControlRating(Integer controlEvaluation) {
        if (controlEvaluation == null) return null;
        if (controlEvaluation >= 1 && controlEvaluation <= 3) return "Poor";
        if (controlEvaluation >= 4 && controlEvaluation <= 6) return "Average";
        if (controlEvaluation >= 7 && controlEvaluation <= 8) return "Good";
        if (controlEvaluation >= 9 && controlEvaluation <= 10) return "Excellent";
        return null;
    }

    /**
     * Residual Risk Rating based on Risk Value and Control Evaluation.
     * Simple example logic reducing risk rating if controls are strong.
     */
    public static String getResidualRiskRating(Integer riskValue, Integer controlEvaluation) {
        if (riskValue == null) return null;
        if (controlEvaluation == null) return "Extreme"; // Without controls max risk

        int adjustedRisk = riskValue - controlEvaluation / 3; // controlEvaluation impact reduces risk
        if (adjustedRisk < 2) adjustedRisk = 2;
        if (adjustedRisk > 10) adjustedRisk = 10;

        if (adjustedRisk >= 2 && adjustedRisk <= 5) {
            return "Low";
        }
        if (adjustedRisk == 6) {
            return "Moderate";
        }
        if (adjustedRisk == 7) {
            return "Significant";
        }
        if (adjustedRisk >= 8 && adjustedRisk <= 10) {
            return "Extreme";
        }
        return "Unknown";
    }
}
