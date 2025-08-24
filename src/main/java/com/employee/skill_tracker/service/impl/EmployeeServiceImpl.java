package com.employee.skill_tracker.service.impl;

import com.employee.skill_tracker.dto.EmployeeSkillsCountDTO;
import com.employee.skill_tracker.entity.Employee;
import com.employee.skill_tracker.entity.EmployeeSkill;
import com.employee.skill_tracker.repository.EmployeeRepository;
import com.employee.skill_tracker.repository.EmployeeSkillRepository;
import com.employee.skill_tracker.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author piyumi_navodani
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeSkillRepository employeeSkillRepository;

    /**
     * This method is to save a new employee record
     *
     * @param employee
     * @return saved employee
     */
    @Override
    public Employee createEmployee(final Employee employee) {
        log.info("EmployeeServiceImpl.createEmployee() started.");
        try {
            if (employee != null) {
                log.info("Saving employee...");
                employee.setId(null);
                employee.setName(employee.getName());
                employee.setDepartment(employee.getDepartment());
                employee.setCreatedAt(employee.getCreatedAt());
                employee.setUpdatedAt(employee.getUpdatedAt());
                if (employee.getEmployeeSkillList() != null) {
                    for (EmployeeSkill skill : employee.getEmployeeSkillList()) {
                        skill.setEmployee(employee);
                    }
                }
                log.info("Employee saved.");
                Employee savedEmployee = employeeRepository.save(employee);
                return savedEmployee;
            } else {
                log.warn("Request body is missing or malformed.");
                return null;
            }
        } catch (Exception e) {
            log.warn("Error occured while saving the employee");
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * This method is use to return a list of employees
     *
     * @return employee list
     */
    @Override
    public List<Employee> getEmployees() {
        log.info("EmployeeServiceImpl.getEmployees() started.");
        try {
            List<Employee> employees = employeeRepository.findAll();
            log.info("Employee list returned successfully.");
            return employees;
        } catch (Exception e) {
            log.warn("Error occurs while returning the employee list");
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * This method is use to get employee by employeeId
     *
     * @param id
     * @return employee
     */
    @Override
    public Employee getEmployeeById(final UUID id) {
        log.info("EmployeeServiceImpl.getEmployeeById() started.");
        if (id == null) {
            log.warn("Id is null. Cannot fetch the employee");
            throw new IllegalArgumentException("Employee Id must not be null");
        }
        try {
            return employeeRepository.findById(id).orElseThrow(() -> {
                log.error("Employee not found with the ID: " + id);
                return new EntityNotFoundException("Employee not found with the ID: " + id);
            });
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.warn("Error while fetching employee with ID: {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch task", e);
        }
    }

    /**
     * This method is use to update employee details by employee id
     *
     * @param id
     * @param employee
     * @return updated employee
     */
    @Override
    public Employee updateEmployee(final UUID id, final Employee employee) {
        log.info("EmployeeServiceImpl.updateEmployee() started.");
        if (id == null || employee == null) {
            log.warn("Employee ID or request payload is null. Cannot proceed with update.");
            throw new IllegalArgumentException("Employee ID and request payload cannot be nul.");
        }
        try {
            Employee emp = getEmployeeById(id);
            log.info("Updating employee with ID: {}", id);
            emp.setName(employee.getName());
            emp.setDepartment(employee.getDepartment());
            Employee updatedEmployee = employeeRepository.save(emp);
            log.info("Employee updated successfully. ID: {}", updatedEmployee.getId());
            return updatedEmployee;
        } catch (EntityNotFoundException e) {
            log.error("Employee not found with ID: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("Error while updating employee with ID: {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to update employee", e);
        }

    }

    /**
     * This method is to delete employee record by employee ID
     *
     * @param id
     */
    @Override
    public void deleteEmployee(final UUID id) {
        log.info("EmployeeServiceImpl.deleteEmployee() started.");
        if (id == null) {
            log.warn("Employee ID is null");
            throw new IllegalArgumentException("Employee ID must not be null.");
        }
        try {
            employeeRepository.deleteById(id);
            log.info("Task deleted successfully.");
        } catch (EmptyResultDataAccessException e) {
            log.warn("Employee with ID {} not found. Nothing to delete.", id);
            throw new EntityNotFoundException("Employee not found for the id " + id);
        } catch (Exception e) {
            log.error("Error while deleting employee with ID: {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to delete employee", e);
        }
    }

    /**
     * This method is to get employees list by sending the skill name
     *
     * @param skill
     * @return employees list
     */
    @Override
    public List<Employee> getEmployeesBySkill(final String skill) {
        log.info("EmployeeServiceImpl.getEmployeesBySkill() started.");
        try {
            log.info("Employee list returned successfully.");
            return employeeRepository.findEmployeeBySkillName(skill);
        } catch (EmptyResultDataAccessException e) {
            log.warn("No employees found for the given skill");
            throw new EntityNotFoundException("No employees have for skill " + skill);
        } catch (Exception e) {
            log.error("Error while returning employees with skill");
            throw new RuntimeException("Failed to return employees", e);
        }
    }

    /**
     * This method use to get the skill count of each employee and sort it to descending order
     *
     * @return EmployeeSkillsCountDTO
     */
    @Override
    public List<EmployeeSkillsCountDTO> sortBySkillCount() {
        log.info("EmployeeServiceImpl.getEmployeesBySkill() started.");
        List<Employee> employees = employeeRepository.findAll();
        try {
            return employees.stream().map(emp -> new EmployeeSkillsCountDTO(emp.getId(), emp.getName(), emp.getDepartment(), emp.getEmployeeSkillList().size())).sorted(Comparator.comparingInt(EmployeeSkillsCountDTO::getSkillCount).reversed()).collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * This method use to get skills related to each department
     *
     * @return skills by department
     */
    public List<Map<String, Map<String, Integer>>> getSkillByDepartment() {
        log.info("EmployeeServiceImpl.getSkillByDepartment() started.");
        try {
            List<Employee> employees = employeeRepository.findAll();

            Map<String, Map<String, Integer>> departmentSkillMap = new HashMap<>();

            for (Employee employee : employees) {
                String department = employee.getDepartment();

                Map<String, Integer> skillMap = departmentSkillMap.computeIfAbsent(department, k -> new HashMap<>());

                for (EmployeeSkill skill : employee.getEmployeeSkillList()) {
                    skillMap.merge(skill.getSkillName(), 1, Integer::sum);
                }
            }

            return Collections.singletonList(departmentSkillMap);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
