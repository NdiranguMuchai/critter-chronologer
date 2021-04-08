package com.ndirangu.critterchronologer.dto;

import com.ndirangu.critterchronologer.model.EmployeeSkill;

import java.time.LocalDate;
import java.util.Set;

/**
 * These DTO classes are used in the presentation layer to control what the client has access to.
 *
 * This DTO represents a request to find available employees by skills.
 */
public class EmployeeRequestDTO {
    private Set<EmployeeSkill> skills;
    private LocalDate date;

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
