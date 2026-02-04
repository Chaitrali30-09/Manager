package com.optihealth;

public class healthController {

    public static class PlanRequest {
        public double heightCm;
        public double weightKg;
        public double targetWeightKg;
        public String targetBodyPart;
        public int targetWeeks;
    }

    public static class PlanResponse {
        public double bmi;
        public String bmiCategory;
        public String dietPlan;
        public String workoutPlan;
    }

    // Plain Java method (no Spring dependencies) to compute a plan
    public static PlanResponse getPlan(PlanRequest req) {
        double bmi = BMICalculator.calculateBMI(req.heightCm, req.weightKg);
        String category = BMICalculator.getBMICategory(bmi);

        PlanResponse res = new PlanResponse();
        res.bmi = Math.round(bmi * 10.0) / 10.0;
        res.bmiCategory = category;
        res.dietPlan = DietPlanner.getDietPlan(category);
        res.workoutPlan = WorkoutPlanner.getWorkout(req.targetBodyPart);
        return res;
    }
}