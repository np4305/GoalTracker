package com.goaltracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class DaysDone extends Fragment {

    GoalRoot goals;
    Gson gsonFormater;

    TableLayout tableLayoutDaysDone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_days_done, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tableLayoutDaysDone = view.findViewById(R.id.tableLayoutDaysDone);

        gsonFormater = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime();
            }
        }).create();

        loadAllGoals();

        goals.getGoals().forEach(goal -> {
            TableRow row = new TableRow(getContext());
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView tw1 = new TextView(getContext());
            tw1.setText(goal.getName());

            row.addView(tw1);

            TextView tw2 = new TextView(getContext());
            tw2.setText(goal.getStart());

            row.addView(tw2);

            tableLayoutDaysDone.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            tableLayoutDaysDone.requestLayout();
        });
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
}