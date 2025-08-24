package com.employee.skill_tracker.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author by piyumi_navodani
 */
@AllArgsConstructor
public enum SkillLevel {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    EXPERT("Expert");

    private final String label;

    public static SkillLevel fromLabel(String label) {
        for (SkillLevel level : values()) {
            if (level.label.equalsIgnoreCase(label)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid skill level: " + label);
    }
}
