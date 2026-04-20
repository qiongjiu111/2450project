package com.example.a2450project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.a2450project.model.CrewMember;
import com.example.a2450project.ui.CrewAdapter;

import java.util.ArrayList;
import java.util.List;

public class QuartersActivity extends AppCompatActivity {

    private CrewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quarters);

        RecyclerView list = findViewById(R.id.quartersList);
        Button simulatorButton = findViewById(R.id.goSimulator);
        Button missionButton = findViewById(R.id.goMission);
        Button backButton = findViewById(R.id.backToCreate);

        List<CrewMember> crewList = MainActivity.storage.getQuarters(); //get members

        adapter = new CrewAdapter(crewList);

        list.setLayoutManager(new GridLayoutManager(this, 2));
        list.setAdapter(adapter);

        simulatorButton.setOnClickListener(v -> {
            if (adapter.getSelected().isEmpty()) {
                Toast.makeText(this, "Select at least one crew", Toast.LENGTH_SHORT).show();
                return;
            }

            ArrayList<CrewMember> selectedCrew = new ArrayList<>(adapter.getSelected());

            for (CrewMember crew : selectedCrew) {
                MainActivity.storage.moveToSimulator(crew); //move simulator
            }

            adapter.getSelected().clear();
            adapter.notifyDataSetChanged();

            startActivity(new Intent(this, SimulatorActivity.class));
        });

        missionButton.setOnClickListener(v -> {
            if (adapter.getSelected().size() < 2) {
                Toast.makeText(this, "Select at least 2 crew", Toast.LENGTH_SHORT).show();
                return;
            }

            ArrayList<CrewMember> selectedCrew = new ArrayList<>(adapter.getSelected());

            for (CrewMember crew : selectedCrew) {
                MainActivity.storage.moveToMissionControl(crew); //move mission
            }

            adapter.getSelected().clear();
            adapter.notifyDataSetChanged();

            startActivity(new Intent(this, MissionActivity.class));
        });

        backButton.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}