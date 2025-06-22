/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.service.client;

import com.job.model.Job;
import com.job.repository.FavoriteJobRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteJobService {

    @Autowired
    private FavoriteJobRepository favoriteJobRepository;

    public void save(int candidateId, int jobId) {
        favoriteJobRepository.save(candidateId, jobId);
    }

    public List<Job> getFavorites(int candidateId) {
        return favoriteJobRepository.findByCandidateId(candidateId);
    }

    public void remove(int candidateId, int jobId) {
        favoriteJobRepository.remove(candidateId, jobId);
    }

    public boolean isFavorited(int candidateId, int jobId) {
        return favoriteJobRepository.exists(candidateId, jobId);
    }
}

