package com.goaltracker;

import android.content.Intent;
import android.os.Bundle;

import com.goaltracker.ui.main.PageViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.goaltracker.ui.main.SectionsPagerAdapter;
import com.goaltracker.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    public static final String GOAL_DIR_NAME = "goals";
    public static final String GOAL_DATABASE = "goals.json";
    public static final String TAG = "GoalTraker";

    private ActivityMainBinding binding;
    public static File GOAL_DIR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this);
        ViewPager2 viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        new TabLayoutMediator(tabs, viewPager, (tab, position) ->tab.setText(String.valueOf(position ==0 ? getString(R.string.tab_text_1) : getString(R.string.tab_text_2)))).attach();

        FloatingActionButton add = binding.add;

        add.setOnClickListener(view -> {
            Intent i  =new Intent(getBaseContext(), EventInsert.class);
            startActivity(i);
        });

       GOAL_DIR = new File(getFilesDir(), GOAL_DIR_NAME);
        if (!GOAL_DIR.exists()) {
            Log.d(TAG, "GOAL_DIR does not exists. Creating it.");
            GOAL_DIR.mkdirs();

            try {
                (new File(GOAL_DIR, GOAL_DATABASE)).createNewFile();
            } catch (IOException e) {
                Log.e(TAG, "Cannot create GOAL_DATABASE!", e);
            }
        }
    }
}