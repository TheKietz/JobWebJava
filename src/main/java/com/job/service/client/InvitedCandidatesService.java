/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.service.client; // Hoặc package phù hợp với service của bạn

import com.job.model.InvitedCandidates;
import com.job.repository.InvitedCandidatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvitedCandidatesService {

    @Autowired
    private InvitedCandidatesRepository invitedCandidatesRepository;

    public Integer addInvitation(InvitedCandidates invitedCandidate) {

        return invitedCandidatesRepository.add(invitedCandidate);
    }

    public boolean updateInvitation(InvitedCandidates invitedCandidate) {
        return invitedCandidatesRepository.update(invitedCandidate);
    }

    public boolean deleteInvitationById(Integer id) {
        return invitedCandidatesRepository.deleteById(id);
    }

    public InvitedCandidates getInvitationById(Integer id) {
        return invitedCandidatesRepository.findById(id);
    }

    List<InvitedCandidates> getAllInvitations() {
        return invitedCandidatesRepository.findAll();
    }

    public List<InvitedCandidates> getInvitationsByEmployerId(Integer employerId) {
        return invitedCandidatesRepository.findByEmployerId(employerId);
    }

    public List<InvitedCandidates> getInvitationsByCandidateId(Integer candidateId) {
        return invitedCandidatesRepository.findByCandidateId(candidateId);
    }

    public InvitedCandidates getInvitationByEmployerAndCandidateId(Integer employerId, Integer candidateId) {
        return invitedCandidatesRepository.findByEmployerAndCandidateId(employerId, candidateId);
    }
}
