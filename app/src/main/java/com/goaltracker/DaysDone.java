package com.goaltracker;

import static java.time.temporal.ChronoUnit.DAYS;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.goaltracker.Model.Goal;
import com.goaltracker.Model.GoalRoot;

import java.time.LocalDateTime;

public class DaysDone extends Fragment {

    GoalRoot goals;
    LinearLayout viewDaysDone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        drawGoals();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_days_done, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewDaysDone = view.findViewById(R.id.viewDaysDone);

        drawGoals();
    }

    private void drawGoals() {
        goals = FileOperations.getGoalRoot(); //get all goals

        viewDaysDone.removeAllViews(); //remove all goals from viewDaysDone

        View header = LayoutInflater.from(getContext()).inflate(R.layout.table_layout, viewDaysDone, false);

        TextView goalName = header.findViewById(R.id.textViewNameOfGoal);
        goalName.setText(R.string.goalNameLabel);
        goalName.setTextColor(Color.CYAN);

        TextView goalDays = header.findViewById(R.id.textViewNumberOfDays);
        goalDays.setText(R.string.goalDaysLabel);
        goalDays.setTextColor(Color.CYAN);

        header.findViewById(R.id.table_layout_delete).setVisibility(View.GONE);
        viewDaysDone.addView(header);

        //go through every goal
        goals.getGoals().forEach(goal -> {

            //We prepare new row to set the data
            View oneRow = LayoutInflater.from(getContext()).inflate(R.layout.table_layout, viewDaysDone, false);

            //Set the goal name
            ((TextView) oneRow.findViewById(R.id.textViewNameOfGoal)).setText(goal.getName());

            //Set the days passed
            ((TextView) oneRow.findViewById(R.id.textViewNumberOfDays)).setText(getDaysFromDate(goal.getStart()));

            //Set on trash can click
            oneRow.findViewById(R.id.table_layout_delete).setOnClickListener(View -> {
                //If deleting from array is a success
                if (goals.getGoals().remove(goal)) {
                    FileOperations.saveGoalRoot(goals); //delete from array

                    //Notify user, that the Goal was deleted
                    Toast.makeText(getContext(), getString(R.string.goalRemovedSuccess), Toast.LENGTH_SHORT).show();

                    //redraw all goals
                    drawGoals();
                }
            });

            viewDaysDone.addView(oneRow); //add to view
            viewDaysDone.requestLayout(); //redraw the layout
        });
    }

    private String getDaysFromDate(String date) {
        try {
            LocalDateTime date1 = LocalDateTime.parse(date, Goal.customFormat); //Get date of Goal
            LocalDateTime now = LocalDateTime.now(); //Get current date

            return Long.toString(DAYS.between(date1, now)); //return number between dates
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}