package com.employee.skill_tracker.service.impl;

import com.employee.skill_tracker.entity.Employee;
import com.employee.skill_tracker.entity.EmployeeSkill;
import com.employee.skill_tracker.enums.SkillLevel;
import com.employee.skill_tracker.repository.EmployeeSkillRepository;
import com.employee.skill_tracker.service.EmployeeService;
import com.employee.skill_tracker.service.EmployeeSkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author piyumi_navodani
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeSkillServiceImpl implements EmployeeSkillService {
    private final EmployeeSkillRepository employeeSkillRepository;
    private final EmployeeService employeeService;

    /**
     * This method is to update skills of the employee
     *
     * @param empId
     * @param employeeSkill
     * @return saved skill
     */
    @Override
    public EmployeeSkill updateSkill(UUID empId, EmployeeSkill employeeSkill) {
        log.info("EmployeeSkillServiceImpl.updateSkill() method started");
        try {
            if (empId != null) {
                Employee employee = employeeService.getEmployeeById(empId);
                String skillLevel = employeeSkill.getSkillLevel();
                if (employee != null) {
                    log.info("Employee record found for given ID");
                    SkillLevel skillLevelEnum;
                    try {
                        skillLevelEnum = SkillLevel.fromLabel(skillLevel);
                    } catch (IllegalArgumentException e) {
                        log.warn("Wrong skill level");
                        throw new IllegalArgumentException("Wrong skill level");
                    }

                    employeeSkill.setId(null);
                    employeeSkill.setSkillLevel(skillLevel);
                    employeeSkill.setSkillName(employeeSkill.getSkillName());
                    employeeSkill.setEmployee(employeeService.getEmployeeById(empId));
                    log.info("Saving skills for employee");
                    EmployeeSkill savedSkill = employeeSkillRepository.save(employeeSkill);
                    return savedSkill;


                } else {
                    log.warn("Employee not found for given ID.");
                    return null;
                }
            } else {
                log.warn("Employee Id cannot be null");
                return null;
            }
        } catch (RuntimeException e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    /**
     * This method use to delete task of a specific employee
     *
     * @param empId
     * @param skill
     * @return
     */
    @Override
    public String deleteSkill(final UUID empId, final String skill) {
        log.info("EmployeeSkillServiceImpl.deleteSkill() method started");
        try {
            if (empId == null || skill == null || skill.isEmpty()) {
                log.warn("Employee ID or Skill Name is invalid");
                return null;
            }
            Optional<EmployeeSkill> skillOpt = employeeSkillRepository.findBySkillNameAndEmployeeId(skill, empId);
            if (skillOpt.isPresent()) {
                employeeSkillRepository.delete(skillOpt.get());
                log.info("Skill '{}' deleted for employee ID {}", skill, empId);
                return "Skill Deleted Successfully";
            } else {
                log.warn("Skill '{}' not found for employee ID {}", skill, empId);
                return "Skill not found for given employee";
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    /**
     * This method is to get skill set for a specific employee
     *
     * @param empId
     * @return skill set
     */
    @Override
    public List<EmployeeSkill> getSkillsById(final UUID empId) {
        log.info("EmployeeSkillServiceImpl.deleteSkill() method started");
        try {
            if (empId == null) {
                throw new IllegalArgumentException("Employee id cannot be null");

            }
            return employeeSkillRepository.findByEmployeeId(empId);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    /**
     * This method use to get all the skills as a list
     *
     * @return skills list
     */
    @Override
    public List<EmployeeSkill> getAllSkills() {
        log.info("EmployeeSkillServiceImpl.deleteSkill() method started");
        try {
            return employeeSkillRepository.findAll();
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
