
package com.optihealth;

public class MainActivity {
    public static void main(String[] args) {
        double height = 170;
        double weight = 75;

        double bmi = BMICalculator.calculateBMI(height, weight);
        String category = BMICalculator.getBMICategory(bmi);

        System.out.println("BMI: " + bmi);
        System.out.println("Category: " + category);
        System.out.println("Diet: " + DietPlanner.getDietPlan(category));
        System.out.println("Workout: " + WorkoutPlanner.getWorkout("Belly"));
    }
}
