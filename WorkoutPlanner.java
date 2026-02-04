
package com.optihealth;

public class WorkoutPlanner {
    public static String getWorkout(String bodyPart) {
        if (bodyPart.equalsIgnoreCase("Belly"))
            return "Plank, crunches";
        else
            return "Full body workout";
    }
}
