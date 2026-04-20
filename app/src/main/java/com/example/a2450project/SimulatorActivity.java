package com.example.a2450project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.a2450project.logic.Simulator;
import com.example.a2450project.model.CrewMember;
import com.example.a2450project.ui.CrewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SimulatorActivity extends AppCompatActivity {

    private CrewAdapter adapter;
    private Simulator simulator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulator);

        RecyclerView list = findViewById(R.id.simulatorList);
        Button trainButton = findViewById(R.id.trainButton);
        Button returnButton = findViewById(R.id.returnToQuartersButton);

        List<CrewMember> crewList = MainActivity.storage.getSimulator();
        simulator = new Simulator();

        adapter = new CrewAdapter(crewList);

        list.setLayoutManager(new GridLayoutManager(this, 2));
        list.setAdapter(adapter);

        trainButton.setOnClickListener(v -> {
            if (adapter.getSelected().isEmpty()) {
                Toast.makeText(this, "Select at least one crew", Toast.LENGTH_SHORT).show();
                return;
            }

            for (CrewMember crew : adapter.getSelected()) {
                simulator.train(crew);
            }

            adapter.notifyDataSetChanged();

            Toast.makeText(this, "Training complete", Toast.LENGTH_SHORT).show();
        });

        returnButton.setOnClickListener(v -> {
            List<CrewMember> simulatorList = MainActivity.storage.getSimulator();

            if (simulatorList.isEmpty()) {
                finish();
                return;
            }

            ArrayList<CrewMember> copy = new ArrayList<>(simulatorList);

            for (CrewMember crew : copy) {
                MainActivity.storage.returnFromSimulator(crew); //restore health
            }

            adapter.getSelected().clear();
            adapter.notifyDataSetChanged(); //refresh

            Toast.makeText(this, "All crew returned to Quarters", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}