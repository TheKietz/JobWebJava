// src/main/java/com/job/service/storage/LocalStorageServiceImpl.java
package com.job.service.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LocalStorageServiceImpl implements FileStorageService { // Triển khai interface FileStorageService

    private static final Logger logger = LoggerFactory.getLogger(LocalStorageServiceImpl.class);

    @Value("${file.upload-dir}")
    private String uploadDir;

    private Path fileStorageLocation;

    @PostConstruct
    public void init() {
        try {
            this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(this.fileStorageLocation);
            logger.info("File storage location initialized to: {}", this.fileStorageLocation); // Thêm dòng này
        } catch (IOException ex) {
            logger.error("Could not create the directory where the uploaded files will be stored.", ex); // Thêm dòng này
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        String originalFileName = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
        String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

        try {
            // Kiểm tra tên file có chứa ký tự nguy hiểm không
            if (fileName.contains("..")) {
                throw new RuntimeException("Tên file không hợp lệ: " + originalFileName);
            }

            // Kiểm tra định dạng file hợp lệ (PDF, DOC, DOCX)
            String contentType = file.getContentType();
            if (!contentType.equals("application/pdf")
                    && !contentType.equals("application/msword")
                    && !contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
                throw new RuntimeException("Chỉ cho phép upload file PDF hoặc Word (doc, docx).");
            }

            // Kiểm tra kích thước file (ví dụ 5MB max)
            if (file.getSize() > 5 * 1024 * 1024) {
                throw new RuntimeException("File quá lớn. Giới hạn tối đa là 5MB.");
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/" + fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Không thể lưu file " + originalFileName + ". Vui lòng thử lại.", ex);
        }
    }

    @Override // Triển khai phương thức từ interface
    public Path loadFileAsResource(String fileName) {
        return this.fileStorageLocation.resolve(fileName).normalize();
    }

    @Override // Triển khai phương thức từ interface
    public boolean deleteFile(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            return Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            System.err.println("Could not delete file " + fileName + ": " + ex.getMessage());
            return false;
        }
    }
}
