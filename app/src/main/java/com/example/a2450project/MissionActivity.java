package com.example.a2450project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2450project.logic.MissionControl;
import com.example.a2450project.logic.MissionResult;
import com.example.a2450project.model.CrewMember;
import com.example.a2450project.model.Threat;
import com.example.a2450project.ui.CrewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MissionActivity extends AppCompatActivity {

    private CrewAdapter adapter;
    private MissionControl missionControl;
    private Threat previewThreat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);

        RecyclerView list = findViewById(R.id.missionList);
        Button startButton = findViewById(R.id.startMissionButton);
        Button returnButton = findViewById(R.id.returnFromMissionButton);

        TextView threatName = findViewById(R.id.threatName);
        TextView threatHPText = findViewById(R.id.threatHPText);
        TextView threatStats = findViewById(R.id.threatStats);
        ProgressBar threatHPBar = findViewById(R.id.threatHP);
        ImageView threatImage = findViewById(R.id.threatImage);

        TextView battleLog = findViewById(R.id.battleLog);

        List<CrewMember> crewList = MainActivity.storage.getMissionControl();

        missionControl = new MissionControl();
        adapter = new CrewAdapter(crewList);

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        previewThreat = missionControl.generateThreat();

        if (previewThreat != null) {
            threatName.setText(previewThreat.getName());
            threatHPText.setText("HP " + previewThreat.getEnergy() + "/" + previewThreat.getMaxEnergy());
            threatStats.setText("ATK " + previewThreat.getSkill() + " DEF " + previewThreat.getResilience());
            threatHPBar.setMax(previewThreat.getMaxEnergy());
            threatHPBar.setProgress(previewThreat.getEnergy());
            threatImage.setImageResource(R.drawable.threat);
        }

        startButton.setOnClickListener(v -> {

            if (adapter.getSelected().size() < 2) {
                Toast.makeText(this, "Select at least 2 crew", Toast.LENGTH_SHORT).show();
                return;
            }

            CrewMember[] selectedArray = adapter.getSelected().toArray(new CrewMember[0]);

            MissionResult result = missionControl.launchMission(selectedArray[0], selectedArray[1]);

            Threat threat = missionControl.getCurrentThreat();

            if (threat != null) {
                threatName.setText(threat.getName());
                threatHPText.setText("HP " + threat.getEnergy() + "/" + threat.getMaxEnergy());
                threatStats.setText("ATK " + threat.getSkill() + " DEF " + threat.getResilience());
                threatHPBar.setMax(threat.getMaxEnergy());
                threatHPBar.setProgress(threat.getEnergy());
                threatImage.setImageResource(R.drawable.threat);
            }

            battleLog.setText(result.getLog());

            List<CrewMember> missionList = MainActivity.storage.getMissionControl();
            ArrayList<CrewMember> copy = new ArrayList<>(missionList);

            for (CrewMember crew : copy) {
                if (!crew.isAlive()) {
                    MainActivity.storage.removeDeadCrew(crew);
                }
            }

            adapter.notifyDataSetChanged();

            if (result.isSuccess()) {
                Toast.makeText(this, "Mission completed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Mission failed", Toast.LENGTH_SHORT).show();
            }
        });

        returnButton.setOnClickListener(v -> {

            List<CrewMember> missionList = MainActivity.storage.getMissionControl();
            ArrayList<CrewMember> copy = new ArrayList<>(missionList);

            for (CrewMember crew : copy) {
                if (crew.isAlive()) {
                    MainActivity.storage.returnFromMissionControl(crew);
                } else {
                    MainActivity.storage.removeDeadCrew(crew);
                }
            }

            adapter.getSelected().clear();
            adapter.notifyDataSetChanged();

            Toast.makeText(this, "All crew returned to Quarters", Toast.LENGTH_SHORT).show();

            finish();
        });
    }
}
