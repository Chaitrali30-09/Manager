
package com.optihealth;

public class BMICalculator {
    public static double calculateBMI(double heightCm, double weightKg) {
        double heightM = heightCm / 100;
        return weightKg / (heightM * heightM);
    }

    public static String getBMICategory(double bmi) {
        if (bmi < 18.5) return "Underweight";
        else if (bmi < 25) return "Normal";
        else if (bmi < 30) return "Overweight";
        else return "Obese";
    }
}
