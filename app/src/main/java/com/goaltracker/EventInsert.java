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

    Gson gsonFormater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_insert);

        buttonCreateEvent = findViewById(R.id.buttonCreate);
        editTextEventName = findViewById(R.id.editTextEventName);

        buttonCreateEvent.setOnClickListener(Vew ->{
            String name = editTextEventName.getText().toString();
            LocalDateTime date = LocalDateTime.now();

            Goal g = new Goal(name, date);

            goals.getGoals().add(g);

            saveAllGoals();

            Toast.makeText(this, "Goal was created!", Toast.LENGTH_SHORT).show();
            finish();

        });

        gsonFormater = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime();
            }
        }).create();

        loadAllGoals();

    }

    private void loadAllGoals() {
        try {
            FileInputStream fis = new FileInputStream(new File(MainActivity.GOAL_DIR, MainActivity.GOAL_DATABASE));
            InputStreamReader br = new InputStreamReader(new BufferedInputStream(fis));

            //if root is empty, then we create an empty root
            goals = gsonFormater.fromJson(br, GoalRoot.class);
            if (goals == null) {
                Log.d(MainActivity.TAG, "root in SaveFile is empty. Creating empty root.");
                goals = new GoalRoot();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveAllGoals() {
        try {
            Writer w = new FileWriter(new File(MainActivity.GOAL_DIR, MainActivity.GOAL_DATABASE), false);
            gsonFormater.toJson(goals, w);
            w.flush();
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}