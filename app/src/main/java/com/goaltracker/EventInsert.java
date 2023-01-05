package com.goaltracker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.goaltracker.Model.Goal;
import com.goaltracker.Model.GoalRoot;

import java.time.LocalDateTime;

public class EventInsert extends AppCompatActivity {

    Button buttonCreateEvent;
    EditText editTextEventName;

    GoalRoot goals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_insert);

        buttonCreateEvent = findViewById(R.id.buttonCreate);
        editTextEventName = findViewById(R.id.editTextEventName);

        buttonCreateEvent.setOnClickListener(Vew -> {
            String name = editTextEventName.getText().toString();
            LocalDateTime date = LocalDateTime.now();

            Goal g = new Goal(name, date);

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