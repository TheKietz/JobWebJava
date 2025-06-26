// src/main/java/com/job/service/storage/FileStorageService.java
package com.job.service.storage;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;

public interface FileStorageService {
    String storeFile(MultipartFile file);
    Path loadFileAsResource(String fileName); // Phương thức để tải file nếu cần
    boolean deleteFile(String fileName); // Phương thức để xóa file nếu cần
}