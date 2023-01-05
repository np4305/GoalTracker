package com.goaltracker;

import android.util.Log;

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

public class FileOperations {

    private static final Gson gsonFormater = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
            (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) ->
                    ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime()).create();

    public static GoalRoot getGoalRoot() {
        GoalRoot goals = null;
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

        return goals;
    }

    public static boolean saveGoalRoot(GoalRoot goals) {
        try {
            Writer w = new FileWriter(new File(MainActivity.GOAL_DIR, MainActivity.GOAL_DATABASE), false);
            gsonFormater.toJson(goals, w);
            w.flush();
            w.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
