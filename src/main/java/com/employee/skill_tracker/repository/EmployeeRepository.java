package com.employee.skill_tracker.repository;

import com.employee.skill_tracker.dto.EmployeeSkillsCountDTO;
import com.employee.skill_tracker.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    @Query("SELECT DISTINCT e FROM Employee e JOIN e.employeeSkillList esl WHERE esl.skillName = :skillName")
    List<Employee> findEmployeeBySkillName(String skillName);
}
