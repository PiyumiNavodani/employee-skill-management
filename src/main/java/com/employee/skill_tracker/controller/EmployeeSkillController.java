package com.employee.skill_tracker.controller;

import com.employee.skill_tracker.entity.EmployeeSkill;
import com.employee.skill_tracker.service.EmployeeSkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @author piyumi_navodani
 */
@RestController
@RequestMapping("/api/employeeSkill")
@RequiredArgsConstructor
@Slf4j
public class EmployeeSkillController {
    private final EmployeeSkillService employeeSkillService;

    /**
     * This is the end point to update skills of the employee
     * @param id
     * @param employeeSkill
     * @return saved skill
     */
    @PostMapping("/{id}")
    public EmployeeSkill updateSkill(@PathVariable final UUID id, @RequestBody final EmployeeSkill employeeSkill){
        log.info("EmployeeSkillController.updateSkill() method started");
        return employeeSkillService.updateSkill(id, employeeSkill);
    }

    /**
     * This is the endpoint to delete task of a specific employee
     * @param id
     * @param skillName
     * @return
     */
    @DeleteMapping("{id}/skills/{skillName}")
    public String deleteSkill(@PathVariable final UUID id, @PathVariable final String skillName){
        log.info("EmployeeSkillController.deleteSkill() method started");
        return employeeSkillService.deleteSkill(id, skillName);
    }

    /**
     * This is the endpoint to get skill set for a specific employee
     * @param id
     * @return skill set
     */
    @GetMapping("{id}/skills")
    public List<EmployeeSkill> getSkillByEmpId(@PathVariable final UUID id){
        log.info("EmployeeSkillController.getSkillByEmpId() method started");
        return employeeSkillService.getSkillsById(id);
    }

    /**
     * This is the endpoint to get all the skills as a list
     * @return skills list
     */
    @GetMapping
    public List<EmployeeSkill> getAllSkills(){
        log.info("EmployeeSkillController.getAllSkills() method started");
        return employeeSkillService.getAllSkills();
    }
}
