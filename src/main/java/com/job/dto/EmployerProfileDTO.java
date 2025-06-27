/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.dto;

/**
 *
 * @author 11090
 */

import com.job.model.User;
import com.job.model.Employer;
import jakarta.validation.Valid;

public class EmployerProfileDTO {

    @Valid
    private User user;

    @Valid
    private Employer employer;

    // Getter & Setter
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }
}
