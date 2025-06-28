package com.job.service.client;

import com.job.dto.CandidateProfileDTO;
import com.job.repository.ProfileRepository;
import com.job.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private UserRepository userRepository;

    public CandidateProfileDTO getProfileByUserId(int userId) {
        return profileRepository.findByUserId(userId);
    }

    public void updateProfile(CandidateProfileDTO profileDTO) {
        profileRepository.updateCandidate(profileDTO);
    }

    public boolean deactivateUser(int userId) {
        int rows = profileRepository.deactivateUserById(userId);
        return rows > 0;
    }
}