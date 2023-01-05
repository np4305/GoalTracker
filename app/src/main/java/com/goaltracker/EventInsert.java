package com.goaltracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goaltracker.Model.Goal;
import com.goaltracker.Model.GoalRoot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

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