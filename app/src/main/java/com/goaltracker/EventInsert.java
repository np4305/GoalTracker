package com.goaltracker;

import static java.time.temporal.ChronoUnit.DAYS;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.goaltracker.Model.Goal;
import com.goaltracker.Model.GoalRoot;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.TimeZone;

public class EventInsert extends AppCompatActivity {

    Button buttonCreateEvent;
    EditText editTextEventName;
    CalendarView calendarView;

    GoalRoot goals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_insert);

        buttonCreateEvent = findViewById(R.id.buttonCreate);
        editTextEventName = findViewById(R.id.editTextEventName);
        calendarView = findViewById(R.id.calendarView);

        //show the selected date as a toast
        calendarView.setOnDateChangeListener((view, year, month, day) -> {
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            view.setDate(c.getTimeInMillis());
        });

        buttonCreateEvent.setOnClickListener(Vew -> {
            String name = editTextEventName.getText().toString();

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime dateTo = LocalDateTime.ofInstant(Instant.ofEpochMilli(calendarView.getDate()), ZoneId.systemDefault());

            Goal g;
            if (DAYS.between(dateTo, now) == 0) {
                g = new Goal(name, now);
            } else {
                g = new Goal(name, now, dateTo);
            }

            goals.getGoals().add(g);

            if (FileOperations.saveGoalRoot(goals)) {
                Toast.makeText(this, "Goal was created!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Goal was not created!", Toast.LENGTH_SHORT).show();
            }

            finish();
        });

        goals = FileOperations.getGoalRoot();
    }
}