<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Dashboard</title>
        <!-- Bootstrap 5 CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Swiper CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@10/swiper-bundle.min.css" />
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" />

        <style>
            .swiper-slide {
                position: relative;
                width: 100%;
                height: 250px;
                overflow: hidden;
                border-radius: 10px;
            }

            .swiper-slide img {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                object-fit: contain;
                border-radius: 10px;
            }
            .swiper {
                width: 100%;
                padding: 20px 0;
            }
            .verify-info {
                display: flex;
                align-items: center;
                margin-bottom: 10px;
            }
            .verify-info .status i {
                font-size: 1.2rem;
                color: #ccc;
            }
            .action-button {
                margin-left: auto;
            }
            .progress-circle {
                transform: rotate(-90deg);
            }
            .loyal-progress {
                position: relative;
                height: 10px;
                background: #f0f0f0;
                border-radius: 5px;
            }
            .custom-progress-single {
                height: 100%;
                background: #00b14f;
                border-radius: 5px;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid p-0">
            <!-- Banner Slider -->
            <div class="swiper mySwiper">
                <div class="swiper-wrapper">
                    <c:forEach var="banner" items="${banners}">
                        <div class="swiper-slide">
                            <a href="${banner.linkUrl}" target="_blank">
                                <img src="${pageContext.request.contextPath}/uploads/${banner.imageUrl}" alt="${banner.title}" />
                            </a>
                        </div>
                    </c:forEach>                    
                </div>
                <div class="swiper-button-next"></div>
                <div class="swiper-button-prev"></div>
            </div>

            <!-- Greeting and Verification -->
            <div class="card mt-4">
                <div class="card-body">
                    <div class="d-flex align-items-center">
                        <div class="progress-container me-3" style="width: 48px; height: 48px;">
                            <svg class="progress-circle" height="48" width="48">
                            <circle cx="24" cy="24" r="21.5" stroke-width="5" class="progress-background" />
                            <circle cx="24" cy="24" r="21.5" stroke-dasharray="135.0884841043611" stroke-dashoffset="135.0884841043611" stroke-width="5" class="progress-bar" />
                            </svg>
<!--                            <div class="progress-text">0%</div>-->
                        </div>
                        <div>
                            <h5 class="mb-1">Xin chào, <span class="user-name" style="color: #02ba5a">${user.fullName}</span></h5>
<!--                            <p class="mb-0">Hãy thực hiện các bước sau để gia tăng tính bảo mật và nhận <span class="highlighted">+2 Top Points</span> để đổi quà khi đăng tin đầu tiên.</p>-->
                        </div>
                    </div>
<!--                    <div class="mt-3">
                        <div class="verify-info">
                            <div class="status"><i class="fa-thin fa-circle"></i></div>
                            <div class="content">Xác thực số điện thoại</div>
                            <div class="action-button"><i class="fa-duotone fa-arrow-up-right"></i></div>
                        </div>
                        <div class="verify-info">
                            <div class="status"><i class="fa-thin fa-circle"></i></div>
                            <div class="content">Cập nhật thông tin công ty</div>
                            <div class="action-button"><i class="fa-duotone fa-arrow-up-right"></i></div>
                        </div>
                        <div class="verify-info">
                            <div class="status"><i class="fa-thin fa-circle"></i></div>
                            <div class="content">Xác thực giấy phép kinh doanh</div>
                            <div class="action-button"><i class="fa-duotone fa-arrow-up-right"></i></div>
                        </div>
                        <div class="verify-info">
                            <div class="status"><i class="fa-thin fa-circle"></i></div>
                            <div class="content">Đăng tin tuyển dụng đầu tiên</div>
                            <div class="action-button"><i class="fa-duotone fa-arrow-up-right"></i></div>
                        </div>
                    </div>-->
                </div>
            </div>

            <!-- Recruitment Explorer -->
            <div class="card mt-4">
                <div class="card-body">
                    <div class="d-flex align-items-center mb-3">
                        <i class="fa-solid fa-compass me-2"></i>
                        <h5>Khám phá TopCV dành cho nhà tuyển dụng</h5>
                    </div>
                    <div class="row">
                        <div class="col-md-4 mb-3">
                            <div class="card h-100">
                                <div class="card-body text-center">
                                    <img src="${pageContext.request.contextPath}/template/images/dang-tin.svg" alt="Post Job" class="mb-2" />
                                    <h5 class="card-title">Đăng tin tuyển dụng</h5>
                                    <button class="btn btn-primary btn-sm"
                                            onclick="window.location.href = '${pageContext.request.contextPath}/app/job-post'">
                                        Thử ngay
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 mb-3">
                            <div class="card h-100">
                                <div class="card-body text-center">
                                    <img src="${pageContext.request.contextPath}/template/images/search.svg" alt="Search CV" class="mb-2" />
                                    <h5 class="card-title">Tìm kiếm CV</h5>
                                    <button class="btn btn-primary btn-sm">Thử ngay</button>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 mb-3">
                            <div class="card h-100">
                                <div class="card-body text-center">
                                    <img src="${pageContext.request.contextPath}/template/images/services.svg" alt="Buy Services" class="mb-2" />
                                    <h5 class="card-title">Mua dịch vụ</h5>
                                    <button class="btn btn-primary btn-sm">Thử ngay</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Swiper JS -->
            <script src="https://cdn.jsdelivr.net/npm/swiper@10/swiper-bundle.min.js"></script>
            <script>
            const totalSlides = document.querySelectorAll(".swiper-slide").length;
            const swiper = new Swiper(".mySwiper", {
                slidesPerView: 2,
                spaceBetween: 20,
                loop: true,
                loopedSlides: 3,
                navigation: {
                    nextEl: ".swiper-button-next",
                    prevEl: ".swiper-button-prev",
                },
                speed: 600,
            });
            </script>
        </div>
    </body>
</html>