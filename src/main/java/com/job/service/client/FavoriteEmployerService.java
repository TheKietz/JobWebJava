package com.job.service.client;

import com.job.model.Employer;
import com.job.repository.FavoriteEmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteEmployerService {

    @Autowired
    private FavoriteEmployerRepository favoriteEmployerRepository;

    public void save(int candidateId, int employerId) {
        if (!favoriteEmployerRepository.isFavorited(candidateId, employerId)) {
            favoriteEmployerRepository.save(candidateId, employerId);
        }
    }

    public void remove(int candidateId, int employerId) {
        favoriteEmployerRepository.remove(candidateId, employerId);
    }

    public List<Employer> getFavorites(int candidateId) {
        return favoriteEmployerRepository.findByCandidateId(candidateId);
    }

    public boolean isFavorited(int candidateId, int employerId) {
        return favoriteEmployerRepository.isFavorited(candidateId, employerId);
    }
}
