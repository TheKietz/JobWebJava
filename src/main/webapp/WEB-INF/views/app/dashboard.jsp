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

            .btn-light-green {
                background-color: #6fcf97; /* màu xanh lá sáng hơn */
                color: white;
                border: none;
            }
            .btn-light-green:hover {
                background-color: #5ec488;
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
                    <div class="d-flex align-items-center mb-3">
                        <h5 class="mb-1">Xin chào, <span class="user-name" style="color: #02ba5a">${user.fullName}</span></h5>
                    </div>
                    <div class="row g-4">
                        <!-- CV chưa xem -->
                        <div class="col-md-4 mb-3">
                            <div class="card h-100 d-flex justify-content-center">
                                <a href="${pageContext.request.contextPath}/app/job-post" class="text-decoration-none text-dark">
                                    <div class="card-body d-flex align-items-center justify-content-center" style="height: 150px;">
                                        <div class="d-flex align-items-center">
                                            <!-- Hình ảnh bên trái -->
                                            <div class="me-3">
                                                <img src="${pageContext.request.contextPath}/template/assets3/images/cv-not-watch.95fc35d.svg"
                                                     alt="Post Job" style="width: 48px; height: 48px;" />
                                            </div>
                                            <!-- Văn bản bên phải -->
                                            <div>
                                                <div class="fw-bold text-uppercase">CV chưa xem</div>
                                                <div class="text-muted small">SL CV chưa xem</div>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                            </div>
                        </div> 

                        <!-- CV chưa đánh giá -->
                        <div class="col-md-4 mb-3">
                            <div class="card h-100 d-flex justify-content-center">
                                <a href="${pageContext.request.contextPath}/app/job-post" class="text-decoration-none text-dark">
                                    <div class="card-body d-flex align-items-center justify-content-center" style="height: 150px;">
                                        <div class="d-flex align-items-center">
                                            <!-- Hình ảnh bên trái -->
                                            <div class="me-3">
                                                <img src="${pageContext.request.contextPath}/template/assets3/images/cv-not-rate.6effe05.svg"
                                                     alt="Post Job" style="width: 48px; height: 48px;" />
                                            </div>
                                            <!-- Văn bản bên phải -->
                                            <div>
                                                <div class="fw-bold text-uppercase">CV chưa đánh giá</div>
                                                <div class="text-muted small">SL tin chưa đánh giá</div>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                            </div>
                        </div>                      


                        <!-- Tin hết hạn -->
                        <div class="col-md-4 mb-3">
                            <div class="card h-100 d-flex justify-content-center">
                                <a href="${pageContext.request.contextPath}/app/job-post" class="text-decoration-none text-dark">
                                    <div class="card-body d-flex align-items-center justify-content-center" style="height: 150px;">
                                        <div class="d-flex align-items-center">
                                            <!-- Hình ảnh bên trái -->
                                            <div class="me-3">
                                                <img src="${pageContext.request.contextPath}/template/assets3/images/expired-job.4949e52.svg"
                                                     alt="Post Job" style="width: 48px; height: 48px;" />
                                            </div>
                                            <!-- Văn bản bên phải -->
                                            <div>
                                                <div class="fw-bold text-uppercase">Tin hết hạn</div>
                                                <div class="text-muted small">Gia hạn hiển thị tin</div>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <!-- Recruitment Explorer -->
        <div class="card mt-4">
            <div class="card-body">
                <div class="d-flex align-items-center mb-3">
                    <i class="fa-solid fa-compass me-2"></i>
                    <h5>Khám phá <span class="user-name" style="color: #02ba5a">JobFInder</span> dành cho nhà tuyển dụng</h5>
                </div>
                <div class="row">
                    <div class="col-md-4 mb-3">
                        <div class="card h-100">
                            <div class="card-body text-center">
                                <img src="${pageContext.request.contextPath}/template/images/dang-tin.svg" alt="Post Job" class="mb-2" />
                                <h5 class="card-title">Đăng tin tuyển dụng</h5>
                                <button class="btn btn-light-green btn-sm"
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
                                <button class="btn btn-light-green btn-sm">Thử ngay</button>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4 mb-3">
                        <div class="card h-100">
                            <div class="card-body text-center">
                                <img src="${pageContext.request.contextPath}/template/images/services.svg" alt="Buy Services" class="mb-2" />
                                <h5 class="card-title">Mua dịch vụ</h5>
                                <button class="btn btn-light-green btn-sm">Thử ngay</button>
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
    
</body>
</html>