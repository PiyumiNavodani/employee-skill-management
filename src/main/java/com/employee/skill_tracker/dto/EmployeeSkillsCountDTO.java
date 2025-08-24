package com.employee.skill_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

/**
 * @author by piyumi_navodani
 */
@Data
public class EmployeeSkillsCountDTO {
        private UUID id;
        private String name;
        private String department;
        private int skillCount;

        public EmployeeSkillsCountDTO(UUID id, String name, String department, int skillCount) {
                this.id = id;
                this.name = name;
                this.department = department;
                this.skillCount = skillCount;
        }
}
