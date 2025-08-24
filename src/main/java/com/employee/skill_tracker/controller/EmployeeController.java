package com.employee.skill_tracker.controller;

import com.employee.skill_tracker.dto.EmployeeSkillsCountDTO;
import com.employee.skill_tracker.entity.Employee;
import com.employee.skill_tracker.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author piyumi_navodani
 */
@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {
    private final EmployeeService employeeService;

    /**
     * This is the end point to save a new employee
     * @param employee
     * @return saved employee
     */
    @PostMapping
    public Employee createEmployee(@RequestBody final Employee employee){
        log.info("EmployeeController.createEmployee() started.");
        return employeeService.createEmployee(employee);
    }

    /**
     * This is the endpoint get all the employee list
     * @return employee list
     */
    @GetMapping
    public List<Employee> getEmployees(){
        log.info("EmployeeController.getEmployees() started.");
        return employeeService.getEmployees();
    }

    /**
     * This is the end point to get employee by employeeId
     * @param id
     * @return employee
     */
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable final UUID id){
        log.info("EmployeeController.getEmployeeById() started.");
        return employeeService.getEmployeeById(id);
    }

    /**
     * This is the end point to update employee details by employee id
     * @param id
     * @param employee
     * @return updated employee
     */
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable final UUID id, @RequestBody final Employee employee){
        log.info("EmployeeController.updateEmployee() started.");
        return employeeService.updateEmployee(id, employee);
    }


    /**
     * This is the end point to delete employee record by employee ID
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable final UUID id){
        log.info("EmployeeController.deleteEmployee() started.");
        employeeService.deleteEmployee(id);
    }

    /**
     * This is the end point to get employees list by sending the skill name
     * @param skillName
     * @return employee list
     */
    @GetMapping("/skill")
    public List<Employee> getEmployeesBySkill(@RequestParam final String skillName){
        log.info("EmployeeController.getEmployeesBySkill() started.");
        return employeeService.getEmployeesBySkill(skillName);
    }

    /**
     * This is the endpoint to get the skill count of each employee and sort it to descending order
     * @return EmployeeSkillsCountDTO
     */
    @GetMapping("/by-skill-count")
    public List<EmployeeSkillsCountDTO> getEmpSkillCount(){
        return employeeService.sortBySkillCount();
    }

    /**
     * This is the endpoint to get skills related to each department
     * @return skills by department
     */
    @GetMapping("/skill-by-department")
    public List<Map<String, Map<String, Integer>>> getSkillByDepartment(){
        return employeeService.getSkillByDepartment();
    }
}
