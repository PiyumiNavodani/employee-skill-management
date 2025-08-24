package com.employee.skill_tracker.service;

import com.employee.skill_tracker.entity.EmployeeSkill;

import java.util.List;
import java.util.UUID;

/**
 * @auther piyumi_navodani
 */
public interface EmployeeSkillService {
    /**
     * This method is to update skills of the employee
     * @param empId
     * @param employeeSkill
     * @return saved skill
     */
    EmployeeSkill updateSkill(final UUID empId, final EmployeeSkill employeeSkill);

    /**
     * This method use to delete task of a specific employee
     * @param empId
     * @param skill
     * @return
     */
    String deleteSkill(final UUID empId, final String skill );

    /**
     * This method is to get skill set for a specific employee
     * @param empId
     * @return skill set
     */
    List<EmployeeSkill> getSkillsById(final UUID empId);

    /**
     * This method use to get all the skills as a list
     * @return skills list
     */
    List<EmployeeSkill> getAllSkills();
}
