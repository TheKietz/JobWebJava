/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.enums;

/**
 *
 * @author 11090
 */
public class CommonEnums {

    public enum TransactionStatus {
        PENDING, // Chờ xử lý
        SUCCESS, // Thành công
        FAILED, // Thất bại
        CANCELED     // Hủy bởi người dùng hoặc hệ thống
    }

    public enum Status {
        ACTIVE, INACTIVE
    }

    public enum Gender {
        Male, Female, Other
    }

    public enum Role {
        ADMIN, EMPLOYER, CANDIDATE
    }

    public enum JobStatus {
        PENDING, APPROVED, REJECTED, EXPIRED
    }

    public enum ApplicationStatus {
        PENDING, REVIEWED, SHORTLISTED, REJECTED, ACCEPTED
    }

    public enum BannerPosition {
        HOMEPAGE_TOP, SIDEBAR, FOOTER
    }

    public enum NotificationType {
        EMAIL, SMS, IN_APP
    }

    public enum NotificationStatus {
        UNREAD, READ
    }

    public enum MessageStatus {
        SENT, SEEN
    }
}
