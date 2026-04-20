package com.example.a2450project.model;

public class CrewMember {

    protected String name;
    protected String specialization;

    protected int skill;
    protected int resilience;
    protected int experience;

    protected int energy;
    protected int maxEnergy;

    protected int id;
    protected static int idCounter = 0;

    protected int bonusHP = 0;
    protected int bonusRes = 0;
    protected int bonusSkill = 0;

    public CrewMember(String name, String specialization, int skill, int resilience, int maxEnergy) {
        this.name = name;
        this.specialization = specialization;
        this.skill = skill;
        this.resilience = resilience;
        this.maxEnergy = maxEnergy;

        this.energy = maxEnergy;
        this.experience = 0;

        this.id = idCounter++;
    }

    public int act() {
        return skill + experience;
    }

    public void defend(int damage) {
        int actualDamage = damage - resilience;
        if (actualDamage < 0) {
            actualDamage = 0;
        }

        energy -= actualDamage;

        if (energy < 0) {
            energy = 0;
        }
    }

    public void gainExperience(int xp) {
        experience += xp;

        int hpLevel = experience / 3;
        if (hpLevel > bonusHP) {
            maxEnergy += (hpLevel - bonusHP);
            bonusHP = hpLevel;
        }

        int resLevel = experience / 6;
        if (resLevel > bonusRes) {
            resilience += (resLevel - bonusRes);
            bonusRes = resLevel;
        }

        int skillLevel = experience / 12;
        if (skillLevel > bonusSkill) {
            skill += (skillLevel - bonusSkill);
            bonusSkill = skillLevel;
        }
    }

    public void restoreEnergy() {
        energy = maxEnergy;
    }

    public boolean isAlive() {
        return energy > 0;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public int getSkill() {
        return skill;
    }

    public int getResilience() {
        return resilience;
    }

    public int getEnergy() {
        return energy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public int getExperience() {
        return experience;
    }

    public int getId() {
        return id;
    }
}