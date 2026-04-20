package com.example.a2450project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a2450project.logic.Storage;
import com.example.a2450project.model.CrewMember;
import com.example.a2450project.model.Engineer;
import com.example.a2450project.model.Medic;
import com.example.a2450project.model.Pilot;
import com.example.a2450project.model.Scientist;
import com.example.a2450project.model.Soldier;

public class MainActivity extends AppCompatActivity {

    public static Storage storage = new Storage(); //share same data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText nameInput = findViewById(R.id.nameInput);
        Spinner classSpinner = findViewById(R.id.classSpinner);
        Button createButton = findViewById(R.id.createButton);
        Button goToQuarters = findViewById(R.id.goToQuarters);

        String[] classes = {"Pilot", "Engineer", "Medic", "Scientist", "Soldier"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                classes
        );

        classSpinner.setAdapter(adapter);

        createButton.setOnClickListener(v -> {

            String name = nameInput.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this, "Enter a name", Toast.LENGTH_SHORT).show();
                return;
            }

            String selectedClass = classSpinner.getSelectedItem().toString();
            CrewMember crew;

            switch (selectedClass) {
                case "Engineer":
                    crew = new Engineer(name);
                    break;
                case "Medic":
                    crew = new Medic(name);
                    break;
                case "Scientist":
                    crew = new Scientist(name);
                    break;
                case "Soldier":
                    crew = new Soldier(name);
                    break;
                default:
                    crew = new Pilot(name);
                    break;
            } //create different character

            storage.addCrewMember(crew); //store Quarter

            Toast.makeText(this, selectedClass + " created in Quarters", Toast.LENGTH_SHORT).show();

            nameInput.setText("");
        });

        goToQuarters.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuartersActivity.class);
            startActivity(intent);
        });
    }
}