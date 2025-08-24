package com.employee.skill_tracker.service;

import com.employee.skill_tracker.dto.EmployeeSkillsCountDTO;
import com.employee.skill_tracker.entity.Employee;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @auther piyumi_navodani
 */
public interface EmployeeService {
    /**
     * This method is to save a new employee record
     * @param employee
     * @return saved employee
     */
    Employee createEmployee(final Employee employee);

    /**
     * This method is use to get all the employee list
     * @return employee list
     */
    List<Employee> getEmployees();

    /**
     * This method is use to get employee by employeeId
     * @param id
     * @return employee
     */
    Employee getEmployeeById(final UUID id);

    /**
     * This method is use to update employee details by employee id
     * @param id
     * @param employee
     * @return updated employee
     */
    Employee updateEmployee (final UUID id, final Employee employee);

    /**
     * This method is to delete employee record by employee ID
     * @param id
     */
    void deleteEmployee(final UUID id);

    /**
     * This method is use to get employees list by sending the skill name
     * @param skill
     * @return employees list
     */
    List<Employee> getEmployeesBySkill(final String skill);

    /**
     * This method use to get the skill count of each employee and sort it to descending order
     * @return EmployeeSkillsCountDTO
     */
    List<EmployeeSkillsCountDTO> sortBySkillCount();

    /**
     * This method use to get skills related to each department
     * @return skills by department
     */
    List<Map<String, Map<String, Integer>>> getSkillByDepartment();
}
