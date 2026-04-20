package com.example.a2450project.logic;

import com.example.a2450project.model.CrewMember;

import java.util.ArrayList;

public class Storage {

    private ArrayList<CrewMember> quartersList;
    private ArrayList<CrewMember> simulatorList;
    private ArrayList<CrewMember> missionControlList;

    private Quarters quarters;

    public Storage() {
        quartersList = new ArrayList<>();
        simulatorList = new ArrayList<>();
        missionControlList = new ArrayList<>();
        quarters = new Quarters();
    }

    public void addCrewMember(CrewMember crew) {
        quartersList.add(crew);
    } //store new character

    public void moveToSimulator(CrewMember crew) {
        if (quartersList.remove(crew)) { //remove
            simulatorList.add(crew);
        }
    }

    public void moveToMissionControl(CrewMember crew) {
        if (quartersList.remove(crew)) { //remove
            missionControlList.add(crew);
        }
    }

    public void returnFromSimulator(CrewMember crew) {
        if (simulatorList.remove(crew)) {
            quarters.restoreEnergy(crew); //restore
            quartersList.add(crew);
        }
    }

    public void returnFromMissionControl(CrewMember crew) {
        if (missionControlList.remove(crew)) {
            quarters.restoreEnergy(crew); //restore
            quartersList.add(crew);
        }
    }

    public void removeDeadCrew(CrewMember crew) {
        quartersList.remove(crew);
        simulatorList.remove(crew);
        missionControlList.remove(crew);
    }

    public ArrayList<CrewMember> getQuarters() {
        return quartersList;
    }

    public ArrayList<CrewMember> getSimulator() {
        return simulatorList;
    }

    public ArrayList<CrewMember> getMissionControl() {
        return missionControlList;
    }
}