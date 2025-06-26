// com.job.service.client.FeedbackService.java
package com.job.service.client;

import com.job.enums.CommonEnums;
import com.job.model.Feedback;
import com.job.repository.FeedbackRepository;
import com.job.service.storage.FileStorageService; // Import FileStorageService mới
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FileStorageService fileStorageService; // Inject FileStorageService

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository, FileStorageService fileStorageService) {
        this.feedbackRepository = feedbackRepository;
        this.fileStorageService = fileStorageService;
    }
    
    public Feedback findByID(Integer feedbackId) {
        return feedbackRepository.findById(feedbackId);
    }
    public void add(Feedback feedback) {
        feedbackRepository.add(feedback);
    }
    
    public void createFeedback(Feedback feedback, List<MultipartFile> attachments) {
        if (feedback.getSubmittedAt() == null) {
            feedback.setSubmittedAt(LocalDateTime.now());
        }
        if (feedback.getStatus() == null) {
            feedback.setStatus(CommonEnums.FeedbackStatus.PENDING);
        }

        List<String> uploadedUrls = null;
        if (attachments != null && !attachments.isEmpty()) {
            uploadedUrls = attachments.stream()
                .map(file -> {
                    try {
                        // Gọi dịch vụ lưu trữ file thực tế
                        return fileStorageService.storeFile(file);
                    } catch (Exception e) {
                        System.err.println("Failed to upload file " + file.getOriginalFilename() + ": " + e.getMessage());
                        // Xử lý lỗi: có thể throw lại exception hoặc trả về null và log
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
            
            feedback.setAttachmentUrls(uploadedUrls.isEmpty() ? null : String.join(",", uploadedUrls));
        }

        feedbackRepository.add(feedback);
        System.out.println("Feedback with ID " + feedback.getId() + " saved successfully by service.");
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public List<Feedback> searchFeedbacks(String keyword) {
        return feedbackRepository.search(keyword);
    }
    
    public void updateFeedbackStatus(Integer feedbackId, CommonEnums.FeedbackStatus newStatus) {
        feedbackRepository.updateStatus(feedbackId, newStatus);
    }

    public List<Feedback> getPagedFeedbacks(List<Feedback> list, int page, int size) {
        return feedbackRepository.getPage(list, page, size);
    }

    public int countTotalPages(List<Feedback> list, int intValue) {
        return feedbackRepository.countPages(list, intValue);
    }
}