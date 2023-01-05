package com.goaltracker.Model;

import java.util.ArrayList;

public class GoalRoot {
    ArrayList<Goal> goals;

    public GoalRoot()
    {
        goals = new ArrayList<>();
    }

    public ArrayList<Goal> getGoals() {
        return goals;
    }
}
