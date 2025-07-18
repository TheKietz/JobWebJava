<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8" />
        <link rel="apple-touch-icon" sizes="76x76" href="${pageContext.request.contextPath}/template/app/assets/img/apple-icon.png">
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/template/app/assets/img/favicon.png">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <title>
            Employer Dashboard
        </title>        
        <link rel="stylesheet" href="https://cdn.ckeditor.com/ckeditor5/45.2.0/ckeditor5.css">
        <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no' name='viewport' />
        <!--     Fonts and icons     -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700,200" rel="stylesheet" />
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css" rel="stylesheet">
        <!-- Fonts awsome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <!-- CSS Files -->
        
        <link href="${pageContext.request.contextPath}/template/app/assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/template/app/assets/css/paper-dashboard.css?v=2.0.1" rel="stylesheet" />


        <style>
            .bold-link p {
                color: #000;         /* màu đen */
                font-weight: 700;    /* đậm hơn */
            }

            .icon-bold {
                color: #000 !important;       /* màu đen */
                font-weight: bold;            /* nếu font hỗ trợ, sẽ đậm hơn */
            }

            .sidebar .nav li.active a p {
                color: #e6902b !important;  /* Ví dụ: màu cam khi active */
                font-weight: 700;
            }

            .sidebar .nav li.active a i {
                color: #e6902b !important;  /* icon cũng đổi màu */
            }

            .sidebar {
                width: 260px;
                position: fixed;
                top: 0;
                left: 0;
                height: 100vh;
                z-index: 1000;
                transition: all 0.3s ease;
            }
            .sidebar.active {
                margin-left: -260px;
            }
            .main-panel {
                margin-left: 260px;
                transition: margin-left 0.3s ease;
                min-height: 100vh;
            }
            .sidebar.active + .main-panel {
                margin-left: 0;
            }
            .navbar-toggle {
                position: fixed;
                left: 20px; /* Move button outside sidebar */
                top: 20px;
                z-index: 1050; /* Ensure it is above sidebar */
            }
            .navbar-toggler {
                background-color: transparent;
                border: none;
                cursor: pointer;
                padding: 10px;
            }
            .navbar-toggler-bar {
                display: block;
                width: 22px;
                height: 2px;
                background-color: #333;
                margin: 4px 0;
                transition: all 0.3s ease;
            }
        </style>
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
                background-color: #56b97c;
            }

            .btn-light-green:focus,
            .btn-light-green:active {
                background-color: #4ea36e !important;  /* đậm hơn khi nhấn */
                box-shadow: none !important;           /* bỏ viền mặc định Bootstrap */
                outline: none;
            }

        </style>
    </head>

    <body class="">
        <div class="wrapper">
            <div class="sidebar" id="sidebar" data-color="white" data-active-color="danger">
                <div class="logo">
                    <a href="${pageContext.request.contextPath}/app/dashboard" class="simple-text logo-mini">
                        <div class="logo-image-small">
                            <img src="${pageContext.request.contextPath}/template/app/assets/img/logo-small.png">
                        </div>
                        <!-- <p>CT</p> -->
                    </a>
                    <a href="${pageContext.request.contextPath}/app/dashboard" class="simple-text logo-normal">
                        <span class="user-name">JobFInder</span>                    
                    </a>
                </div>
                <div class="sidebar-wrapper">
                    <ul class="nav" id="sidebarNavList">
                        <li class="">
                            <a class="bold-link" href="${pageContext.request.contextPath}/app/dashboard">
                                <i class="nc-icon nc-bank icon-bold"></i>
                                <p>Trang chủ</p>
                            </a>
                        </li>
                        <hr>
                        <li>
                            <a class="bold-link" href="${pageContext.request.contextPath}/app/jobs">
                                <i class="nc-icon nc-briefcase-24 icon-bold"></i>
                                <p>Tin tuyển dụng</p>
                            </a>
                        </li>
                        <li>
                            <a class="bold-link" href="${pageContext.request.contextPath}/app/suggest-cv">
                                <i class="fa fa-thumbs-up icon-bold" aria-hidden="true"></i>
                                <p>CV đề xuất</p>
                            </a>
                        </li>
                        <hr>
                        <li>
                            <a class="bold-link" href="${pageContext.request.contextPath}/app/applications">
                                <i class="nc-icon nc-badge icon-bold"></i>
                                <p>Ứng viên</p>
                            </a>
                        </li>
                        <hr>
                        <li>
                            <a class="bold-link" href="${pageContext.request.contextPath}/app/service_packages">
                                <i class="nc-icon nc-cart-simple icon-bold"></i>
                                <p>Mua dịch vụ</p>
                            </a>
                        </li>
                        <li>
                            <a class="bold-link" href="${pageContext.request.contextPath}/app/shop/employer/orders">
                                <i class="nc-icon nc-paper icon-bold"></i>
                                <p>Theo dõi dịch vụ</p>
                            </a>
                        </li>
                        <hr>
                        <li>
                            <a class="bold-link" href="${pageContext.request.contextPath}/app/profile">
                                <i class="nc-icon nc-single-02 icon-bold"></i>
                                <p>Trang cá nhân</p>
                            </a>
                        </li>
                        <hr>
                        <li>
                            <a class="bold-link" href="${pageContext.request.contextPath}/app/settings">
                                <i class="nc-icon nc-settings-gear-65 icon-bold"></i>
                                <p>Cài đặt tài khoản</p>
                            </a>
                        </li>
                        <hr>
                        
                        <li>
                            <a class="bold-link" href="${pageContext.request.contextPath}/app/supports?tab=report">
                                <i class="nc-icon nc-email-85 icon-bold"></i>
                                <p>Hộp thư hỗ trợ</p>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            <!--                                 min-height: 100vh;-->
            <div class="main-panel" style="overflow: auto">
                <!-- Navbar -->
                <nav class="navbar navbar-expand-lg navbar-absolute fixed-top navbar-transparent">
                    <div class="container-fluid">
                        <div class="navbar-wrapper">
                            <div class="navbar-toggle">
                                <button type="button" class="navbar-toggler" onclick="toggleSidebar()">
                                    <span class="navbar-toggler-bar bar1"></span>
                                    <span class="navbar-toggler-bar bar2"></span>
                                    <span class="navbar-toggler-bar bar3"></span>
                                </button>
                            </div>
                        </div>
                        <button class="navbar-toggler" type="button" onclick="toggleSidebar()" data-toggle="collapse" data-target="#navigation" aria-controls="navigation-index" aria-expanded="false" aria-label="Toggle navigation">
                            <span class="navbar-toggler-bar navbar-kebab"></span>
                            <span class="navbar-toggler-bar navbar-kebab"></span>
                            <span class="navbar-toggler-bar navbar-kebab"></span>
                        </button>
                        <div class="collapse navbar-collapse justify-content-end" id="navigation">
                            <form>
                                <div class="input-group no-border">
                                    <input type="text" id="sidebarSearchInput" value=""  class="form-control" placeholder="Search...">
                                    <div class="input-group-append">
                                        <div class="input-group-text">
                                            <i class="nc-icon nc-zoom-split"></i>
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <ul class="navbar-nav">
                                <li class="nav-item btn-rotate dropdown">
                                    <a class="nav-link dropdown-toggle" href="http://example.com" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        <i class="nc-icon nc-bell-55"></i>
                                        <p>
                                            <span class="d-lg-none d-md-block">Some Actions</span>
                                        </p>
                                    </a>
                                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink">
                                        <a class="dropdown-item" href="#">Action</a>
                                        <a class="dropdown-item" href="#">Another action</a>
                                        <a class="dropdown-item" href="#">Something else here</a> 
                                </li>
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="https://example.com" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="false" aria-expanded="true">
                                        <i class="nc-icon nc-bullet-list-67"></i>
                                    </a>
                                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink">
                                        <a class="dropdown-item" href="#">
                                            <i class="nc-icon nc-email-85"></i> Thông báo hệ thống
                                        </a>
                                        <a class="dropdown-item" href="#">
                                            <i class="nc-icon nc-umbrella-13"></i> Hỗ trợ
                                        </a>
                                        <a class="dropdown-item" href="#">
                                            <i class="nc-icon nc-settings"></i> Cài đặt tài khoản
                                        </a>
                                        <div class="divider"></div>
                                        <form id="logoutForm" action="${pageContext.request.contextPath}/app/logout" method="post" style="display: none;">
                                        </form>
                                        <a href="#" class="dropdown-item text-danger" onclick="document.getElementById('logoutForm').submit(); return false;">
                                            <i class="nc-icon nc-button-power"></i> Đăng xuất
                                        </a>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </nav>
                <script>
                    function toggleSidebar() {
                        const sidebar = document.getElementById("sidebar");
                        sidebar.classList.toggle("active");
                    }
                </script>
                <script>
                    document.addEventListener("DOMContentLoaded", function () {
                        const navLinks = document.querySelectorAll(".nav li a");
                        const currentPath = window.location.pathname;

                        navLinks.forEach(link => {
                            const li = link.closest("li");
                            const href = link.getAttribute("href");

                            // Kiểm tra nếu currentPath bắt đầu với href (hữu ích khi URL có thêm /edit, /detail,...)
                            if (href && currentPath.startsWith(href)) {
                                li.classList.add("active");
                            } else {
                                li.classList.remove("active");
                            }
                        });
                    });
                </script>
                <script>
            document.addEventListener("DOMContentLoaded", function () {
                const sidebarSearchInput = document.getElementById("sidebarSearchInput");
                const sidebarNavList = document.getElementById("sidebarNavList");

                if (sidebarSearchInput && sidebarNavList) { // Đảm bảo các phần tử tồn tại
                    const navListItems = sidebarNavList.querySelectorAll("li"); // Lấy tất cả các thẻ <li> trong danh sách nav

                    sidebarSearchInput.addEventListener("input", function () {
                        const searchTerm = sidebarSearchInput.value.toLowerCase().trim(); // Lấy từ khóa tìm kiếm, chuyển về chữ thường và bỏ khoảng trắng đầu cuối

                        navListItems.forEach(item => {
                            // Lấy thẻ <p> chứa văn bản trong mỗi mục sidebar
                            const pElement = item.querySelector("a.bold-link p");
                            const hrElement = item.nextElementSibling; // Lấy thẻ <hr> liền kề (nếu có)

                            if (pElement) {
                                const itemText = pElement.textContent.toLowerCase(); // Lấy văn bản của mục, chuyển về chữ thường

                                if (itemText.includes(searchTerm)) {
                                    // Nếu văn bản của mục chứa từ khóa tìm kiếm, hiển thị mục đó
                                    item.style.display = ""; // Đặt lại về giá trị display mặc định (ví dụ: "list-item" hoặc "block")
                                    if (hrElement && hrElement.tagName === 'HR') { // Nếu có thẻ <hr> liền kề, hiển thị nó
                                        hrElement.style.display = "";
                                    }
                                } else {
                                    // Nếu không khớp, ẩn mục đó
                                    item.style.display = "none";
                                    if (hrElement && hrElement.tagName === 'HR') { // Ẩn luôn thẻ <hr> liền kề
                                        hrElement.style.display = "none";
                                    }
                                }
                            }
                        });
                    });
                }
            });
        </script>
