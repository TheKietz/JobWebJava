package com.job.service.client;

import com.job.dto.CandidateProfileDTO;
import com.job.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public CandidateProfileDTO getProfileByUserId(int userId) {
        return profileRepository.findByUserId(userId);
    }
}
