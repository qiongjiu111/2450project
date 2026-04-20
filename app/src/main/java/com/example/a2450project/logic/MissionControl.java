package com.example.a2450project.logic;

import com.example.a2450project.model.CrewMember;
import com.example.a2450project.model.Threat;

import java.util.Random;

public class MissionControl {

    private Threat currentThreat;

    public MissionResult launchMission(CrewMember first, CrewMember second) {

        currentThreat = generateThreat();

        StringBuilder log = new StringBuilder();

        log.append("=== MISSION START ===\n");
        log.append("Threat: ")
                .append(currentThreat.getName())
                .append(" | ATK: ").append(currentThreat.getSkill())
                .append(" | DEF: ").append(currentThreat.getResilience())
                .append(" | HP: ").append(currentThreat.getEnergy())
                .append("/").append(currentThreat.getMaxEnergy())
                .append("\n\n");

        int round = 1;

        while (currentThreat.isAlive() && (first != null || second != null)) {

            log.append("--- Round ").append(round).append(" ---\n");

            if (first != null && first.isAlive()) {
                int damage = first.getSkill() - currentThreat.getResilience();
                if (damage < 0) damage = 0;

                currentThreat.defend(first.getSkill());

                log.append(first.getName())
                        .append(" attacks ")
                        .append(currentThreat.getName())
                        .append(" for ")
                        .append(damage)
                        .append(" damage. ")
                        .append(currentThreat.getName())
                        .append(" HP: ")
                        .append(currentThreat.getEnergy())
                        .append("/")
                        .append(currentThreat.getMaxEnergy())
                        .append("\n");
            }

            if (second != null && second.isAlive() && currentThreat.isAlive()) {
                int damage = second.getSkill() - currentThreat.getResilience();
                if (damage < 0) damage = 0;

                currentThreat.defend(second.getSkill());

                log.append(second.getName())
                        .append(" attacks ")
                        .append(currentThreat.getName())
                        .append(" for ")
                        .append(damage)
                        .append(" damage. ")
                        .append(currentThreat.getName())
                        .append(" HP: ")
                        .append(currentThreat.getEnergy())
                        .append("/")
                        .append(currentThreat.getMaxEnergy())
                        .append("\n");
            }

            if (!currentThreat.isAlive()) {

                if (first != null && first.isAlive()) {
                    first.gainExperience(5);
                }
                if (second != null && second.isAlive()) {
                    second.gainExperience(5);
                }

                log.append("\nThe threat has been defeated.\n");

                return new MissionResult(true, log.toString());
            }

            if (first != null && first.isAlive()) {
                int damage = currentThreat.getSkill() - first.getResilience();
                if (damage < 0) damage = 0;

                first.defend(currentThreat.getSkill());

                log.append(currentThreat.getName())
                        .append(" attacks ")
                        .append(first.getName())
                        .append(" for ")
                        .append(damage)
                        .append(" damage. ")
                        .append(first.getName())
                        .append(" HP: ")
                        .append(first.getEnergy())
                        .append("/")
                        .append(first.getMaxEnergy())
                        .append("\n");

                if (!first.isAlive()) {
                    log.append(first.getName()).append(" has been defeated.\n");
                    first = null;
                }
            }

            if (second != null && second.isAlive()) {
                int damage = currentThreat.getSkill() - second.getResilience();
                if (damage < 0) damage = 0;

                second.defend(currentThreat.getSkill());

                log.append(currentThreat.getName())
                        .append(" attacks ")
                        .append(second.getName())
                        .append(" for ")
                        .append(damage)
                        .append(" damage. ")
                        .append(second.getName())
                        .append(" HP: ")
                        .append(second.getEnergy())
                        .append("/")
                        .append(second.getMaxEnergy())
                        .append("\n");

                if (!second.isAlive()) {
                    log.append(second.getName()).append(" has been defeated.\n");
                    second = null;
                }
            }

            log.append("\n");
            round++;
        }

        log.append("Mission failed.\n");
        return new MissionResult(false, log.toString());
    }

    public Threat generateThreat() {
        Random r = new Random();

        int hp = 25 + r.nextInt(11);
        int atk = 5 + r.nextInt(5);
        int def = 2 + r.nextInt(4);

        return new Threat("Threat", atk, def, hp);
    }

    public Threat getCurrentThreat() {
        return currentThreat;
    }
}