/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.model;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 *
 * @author 11090
 */
public class InvitedCandidates {

    private Integer id;
    @NotNull(message = "Employer ID must not be null")
    @Min(value = 1, message = "Employer ID must be a positive integer")
    private Integer employerId;

    @NotNull(message = "Candidate ID must not be null")
    @Min(value = 1, message = "Candidate ID must be a positive integer")
    private Integer candidateId;

    @NotNull(message = "Invitation date must not be null")
    @FutureOrPresent(message = "Invitation date cannot be in the past")
    private LocalDate invitedAt;

    public InvitedCandidates(Integer id, int employerId, int candidateId, LocalDate invitedAt) {
        this.id = id;
        this.employerId = employerId;
        this.candidateId = candidateId;
        this.invitedAt = invitedAt;
    }

    public InvitedCandidates() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployerId() {
        return employerId;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public LocalDate getInvitedAt() {
        return invitedAt;
    }

    public void setInvitedAt(LocalDate invitedAt) {
        this.invitedAt = invitedAt;
    }

}
