
package com.optihealth;

public class DietPlanner {
    public static String getDietPlan(String bmiCategory) {
        switch (bmiCategory) {
            case "Underweight": return "High protein diet";
            case "Normal": return "Balanced diet";
            case "Overweight": return "Low carb diet";
            case "Obese": return "Calorie deficit diet";
            default: return "Consult expert";
        }
    }
}
