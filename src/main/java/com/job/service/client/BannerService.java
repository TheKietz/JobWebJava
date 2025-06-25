/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.service.client;

import com.job.enums.CommonEnums;
import com.job.model.Banner;
import com.job.repository.BannerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author 11090
 */
@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    public int saveBanner(Banner banner) {
        return bannerRepository.save(banner);
    }

    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }

    public Banner getBannerById(int id) {
        return bannerRepository.findById(id);
    }

    public int updateBanner(Banner banner) {
        return bannerRepository.update(banner);
    }

    public int deleteBanner(int id) {
        return bannerRepository.delete(id);
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

