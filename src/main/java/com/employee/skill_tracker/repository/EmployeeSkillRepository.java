package com.employee.skill_tracker.repository;
import com.employee.skill_tracker.entity.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkill, UUID> {
    List<EmployeeSkill> findByEmployeeId(UUID id);
    Optional<EmployeeSkill> findBySkillNameAndEmployeeId(String skillName, UUID employeeId);
}
