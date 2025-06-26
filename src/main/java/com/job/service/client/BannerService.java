package com.job.service.client;

import com.job.enums.CommonEnums;
import com.job.model.Banner;
import com.job.repository.BannerRepository;
import com.job.service.storage.FileStorageService; // Import FileStorageService
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BannerService {
    
    private final BannerRepository bannerRepository;
    private final FileStorageService fileStorageService;

    @Autowired
    public BannerService(BannerRepository bannerRepository, FileStorageService fileStorageService) {
        this.bannerRepository = bannerRepository;
        this.fileStorageService = fileStorageService;
    }
    public void save(Banner banner){
        bannerRepository.save(banner);
    }
    public void update(Banner banner){
        bannerRepository.update(banner);
    }
    // Phương thức để lưu Banner mới (bao gồm xử lý file)
    public void saveBanner(Banner banner, MultipartFile imageFile) {
        try {
            // Xử lý upload file ảnh nếu có
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = fileStorageService.storeFile(imageFile); // Lưu file và lấy đường dẫn
                banner.setImageUrl(imageUrl); // Cập nhật URL ảnh vào đối tượng Banner
            } else {
                // Nếu không có file được cung cấp khi tạo mới, đảm bảo imageUrl là null
                banner.setImageUrl(null);
            }

            // Đặt trạng thái mặc định nếu chưa được thiết lập (ví dụ: ACTIVE)
            if (banner.getStatus() == null) {
                banner.setStatus(CommonEnums.Status.ACTIVE);
            }

        } catch (Exception e) {
            System.err.println("Failed to process image file for new banner: " + e.getMessage());
            // Ném lại ngoại lệ để Controller có thể bắt và xử lý lỗi
            throw new RuntimeException("Error processing banner image for new record.", e);
        }
         bannerRepository.save(banner);
    }

    // Phương thức để cập nhật Banner (bao gồm xử lý file)
    public void updateBanner(Banner banner, MultipartFile imageFile) {
        try {
            // Nếu có file mới được upload, lưu file và cập nhật imageUrl
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = fileStorageService.storeFile(imageFile);
                banner.setImageUrl(imageUrl); // Cập nhật imageUrl với đường dẫn mới
            } else {
                // Nếu không có file mới được chọn,
                // Đảm bảo Banner.imageUrl giữ giá trị cũ (từ DB hoặc từ hidden field trên form)
                // Điều này quan trọng để không bị mất ảnh cũ nếu người dùng không upload ảnh mới.
                // Đoạn code dưới đây là một biện pháp phòng ngừa nếu imageUrl bị mất trong quá trình binding.
                if (banner.getId() != null && banner.getImageUrl() == null) { 
                    Banner existingBanner = bannerRepository.findById(banner.getId());
                    if (existingBanner != null) {
                        banner.setImageUrl(existingBanner.getImageUrl());
                    }
                }
            }
            // Không cần đặt trạng thái mặc định ở đây vì khi cập nhật, trạng thái đã có giá trị.

        } catch (Exception e) {
            System.err.println("Failed to process image file for banner update: " + e.getMessage());
            throw new RuntimeException("Error processing banner image for update.", e);
        }
        bannerRepository.update(banner);
    }
    
    // Các phương thức khác giữ nguyên
    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }

    public Banner getBannerById(int id) {
        return bannerRepository.findById(id);
    }

    public void deleteBanner(int id) {
        // Tùy chọn: Xóa file vật lý khi xóa banner
        // Banner bannerToDelete = bannerRepository.findById(id);
        // if (bannerToDelete != null && bannerToDelete.getImageUrl() != null) {
        //     // fileStorageService.deleteFile(bannerToDelete.getImageUrl()); // Cần thêm phương thức deleteFile vào FileStorageService
        // }
        bannerRepository.delete(id);
    }

    public List<Banner> getActiveBannersByPosition(CommonEnums.BannerPosition position) {
        return bannerRepository.findActiveByPosition(position);
    }
    public List<Banner> search(String keyword) {
        return bannerRepository.search(keyword);
    }

    public List<Banner> getPage(List<Banner> list, int page, int size) {
        return bannerRepository.getPage(list, page, size);
    }

    public int countPages(List<Banner> list, int size) {
        return bannerRepository.countPages(list, size);
    }
}