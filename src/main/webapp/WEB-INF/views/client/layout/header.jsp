
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!doctype html>
<html class="no-js" lang="zxx">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Job board HTML-5 Template </title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="manifest" href="<c:url value='/template/site.webmanifest'/>">
        <link rel="shortcut icon" type="image/x-icon" href="<c:url value='/template/assets1/img/favicon.ico'/>">

        <link rel="stylesheet" href="<c:url value='/template/assets1/css/bootstrap.min.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/assets1/css/owl.carousel.min.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/assets1/css/flaticon.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/assets1/css/price_rangs.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/assets1/css/slicknav.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/assets1/css/animate.min.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/assets1/css/magnific-popup.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/assets1/css/fontawesome-all.min.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/assets1/css/themify-icons.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/assets1/css/slick.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/assets1/css/nice-select.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/assets1/css/style.css'/>">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>


    </head>

    <body>

        <header>
            <!-- Header Start -->
            <div class="header-area header-transparrent">
                <div class="headder-top header-sticky">
                    <div class="container">
                        <div class="row align-items-center">
                            <div class="col-lg-3 col-md-2">
                                <!-- Logo -->
                                <div class="logo">
                                    <a href="<c:url value='/'/>"><img src="<c:url value='/template/assets1/img/logo/logo.png'/>" alt=""></a>
                                </div>  
                            </div>
                            <div class="col-lg-9 col-md-9">
                                <div class="menu-wrapper">
                                    <!-- Main-menu -->
                                    <div class="main-menu">
                                        <nav class="d-none d-lg-block">
                                            <ul id="navigation">
                                                <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
                                                <li><a href="${pageContext.request.contextPath}/jobs">Find a Jobs </a></li>
                                            </ul>
                                        </nav>
                                    </div>          
                                    <!-- Header-btn -->
                                    <div class="header-btn d-none f-right d-lg-block">
                                        <a href="${pageContext.request.contextPath}/login" class="btn head-btn1">Register</a>
                                        <a href="${pageContext.request.contextPath}/login" class="btn head-btn2">Login</a>
<!--                                        <a href="${pageContext.request.contextPath}/profiles/dashboard" class="simple-text logo-mini d-flex align-items-center">
                                            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQlWi6fx13t3nmhNDxOwxj80l8QTzZrnf2_lA&s" alt="Logo" style="height: 30px; margin-right: 8px;">
                                            <span>Profile</span>
                                        </a>-->
                                    </div>
                                    <!-- Avatar trigger -->
                                    <div class="dropdown text-end">
                                        <a href="#" id="profileDropdown" class="d-block link-dark text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                            <img src="${pageContext.request.contextPath}/template/images/imac wallaper.jsp" alt="mdo" width="40" height="40" class="rounded-circle">
                                        </a>

                                        <!-- Dropdown content -->
                                        <ul class="dropdown-menu text-small" aria-labelledby="profileDropdown" style="width: 300px;">
                                            <li class="px-3 py-2">
                                                <strong>Kiệt Nguyễn Thế</strong><br>
                                                <small class="text-success">Tài khoản đã xác thực</small><br>
                                                <small>ID: 9581959</small><br>
                                                <small>kietnt1122@uef.edu.vn</small>
                                            </li>
                                            <li><hr class="dropdown-divider"></li>

                                            <!-- Quản lý tìm việc -->
                                            <li><a class="dropdown-item" href="/saved-jobs"><i class="bi bi-briefcase"></i> Việc làm đã lưu</a></li>
                                            <li><a class="dropdown-item" href="/applied-jobs">Việc làm đã ứng tuyển</a></li>
                                            <li><a class="dropdown-item" href="/recommended-jobs">Việc làm phù hợp với bạn</a></li>
                                            <li><a class="dropdown-item" href="/job-suggestions">Cài đặt gợi ý việc làm</a></li>

                                            <li><hr class="dropdown-divider"></li>

                                            <!-- Quản lý CV -->
                                            <li><a class="dropdown-item" href="/cv-management"><i class="bi bi-file-earmark-text"></i> CV của tôi</a></li>
                                            <li><a class="dropdown-item" href="/cover-letters">Cover Letter của tôi</a></li>
                                            <li><a class="dropdown-item" href="/profile-views">Nhà tuyển dụng xem hồ sơ</a></li>

                                            <li><hr class="dropdown-divider"></li>

                                            <!-- Cài đặt -->
                                            <li><a class="dropdown-item" href="/email-settings"><i class="bi bi-gear"></i> Cài đặt email & thông báo</a></li>
                                            <li><a class="dropdown-item" href="/personal-security">Cá nhân & Bảo mật</a></li>
                                            <li><a class="dropdown-item" href="/upgrade-account"><i class="bi bi-star-fill text-warning"></i> Nâng cấp tài khoản</a></li>
                                        </ul>
                                    </div>

                                </div>
                            </div>
                            <!-- Mobile Menu -->
                            <div class="col-12">
                                <div class="mobile_menu d-block d-lg-none"></div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Header End -->
        </header>