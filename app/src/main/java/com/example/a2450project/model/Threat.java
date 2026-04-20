package com.example.a2450project.model;

public class Threat {

    private String name;
    private int skill;
    private int resilience;
    private int energy;
    private int maxEnergy;

    public Threat(String name, int skill, int resilience, int maxEnergy) {
        this.name = name;
        this.skill = skill;
        this.resilience = resilience;
        this.maxEnergy = maxEnergy;
        this.energy = maxEnergy;
    }

    public int attack() {
        return skill;
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

    public boolean isDefeated() {
        return energy <= 0;
    }

    public boolean isAlive() {
        return energy > 0;
    }

    public String getName() {
        return name;
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
}