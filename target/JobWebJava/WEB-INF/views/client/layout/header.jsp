<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="vi">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Job Board - Trang tìm việc làm</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- SEO -->
        <meta name="description" content="Nền tảng tìm kiếm việc làm tốt nhất">

        <!-- Favicon & Manifest -->
        <link rel="manifest" href="<c:url value='/template/client/site.webmanifest'/>">
        <link rel="shortcut icon" href="<c:url value='/template/client/assets1/img/favicon.ico'/>">

        <!-- CSS -->
        <link rel="stylesheet" href="<c:url value='/template/client/assets1/css/style.css'/>">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />

        <!-- JS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </head>

    <body>
        <header>
            <nav class="navbar navbar-expand-lg bg-white border-bottom sticky-top">
                <div class="container-fluid d-flex align-items-center ms-4 me-4">
                    <!-- Logo trái -->
                    <a class="navbar-brand d-flex align-items-center gap-2" href="<c:url value='/'/>">
                        <img src="<c:url value='/template/client/assets1/img/logo/logo.png'/>" alt="Logo" style="height: 40px;">
                    </a>

                    <!-- Toggle cho mobile -->
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNavbar">
                        <span class="navbar-toggler-icon"></span>
                    </button>

                    <!-- Navbar Center + Right -->
                    <div class="collapse navbar-collapse justify-content-center" id="mainNavbar">
                        <!-- Menu giữa -->
                        <ul class="navbar-nav gap-3">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown">Việc làm</a>
                                <ul class="dropdown-menu">
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/jobs">Danh sách việc làm</a></li>
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/favorite-jobs">Việc làm đã lưu</a></li>
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/applications">Việc làm đã ứng tuyển</a></li>
                                </ul>
                            </li>

                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown">Công ty</a>
                                <ul class="dropdown-menu">
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/employers">Danh sách công ty</a></li>
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/favorite-employers">Công ty đã lưu</a></li>
                                </ul>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/app/login">Đăng tin tuyển dụng</a>
                            </li>
                        </ul>
                    </div>

                    <!-- Avatar bên phải -->
                    <div class="d-flex align-items-center ms-auto">
                        <c:choose>
                            <c:when test="${empty sessionScope.loggedInUser}">
                                <a class="btn btn-outline-primary me-2" href="${pageContext.request.contextPath}/signup">Đăng ký</a>
                                <a class="btn btn-primary text-white" href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                            </c:when>
                            <c:otherwise>
                                <div class="dropdown">
                                    <a href="#" class="d-flex align-items-center nav-link dropdown-toggle" data-bs-toggle="dropdown">
                                        <img src="<c:url value='/template/client/assets1/img/avata.png'/>"
                                             alt="Avatar" class="rounded-circle me-2" width="32" height="32">
                                        <span class="d-none d-md-inline">${fn:escapeXml(sessionScope.loggedInUser.fullName)}</span>
                                    </a>
                                    <ul class="dropdown-menu dropdown-menu-end">
                                        <li class="px-3 py-2">
                                            <strong>${fn:escapeXml(sessionScope.loggedInUser.fullName)}</strong><br>
                                            <small class="${sessionScope.loggedInUser.status == 'ACTIVE' ? 'text-success' : 'text-warning'}">
                                                ${sessionScope.loggedInUser.status == 'ACTIVE' ? 'Tài khoản đã xác thực' : 'Tài khoản chưa xác thực'}
                                            </small><br>
                                            <small>ID: ${sessionScope.loggedInUser.id}</small><br>
                                            <small>${fn:escapeXml(sessionScope.loggedInUser.email)}</small>
                                        </li>
                                        <li><hr class="dropdown-divider"></li>

                                        <!-- Mục mới: Hồ sơ cá nhân -->
                                        <li>
                                            <a class="dropdown-item" href="${pageContext.request.contextPath}/profile">
                                                <i class="bi bi-person-circle me-1"></i>Hồ sơ cá nhân
                                            </a>
                                        </li>

                                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/favorites">Việc làm đã lưu</a></li>
                                        <li><a class="dropdown-item" href="/applied-jobs">Đã ứng tuyển</a></li>
                                        <li><a class="dropdown-item" href="/cv-management">CV của tôi</a></li>
                                        <li><hr class="dropdown-divider"></li>
                                        <li>
                                            <form id="logoutForm" action="${pageContext.request.contextPath}/logout" method="post" style="display: none;"></form>
                                            <a href="#" class="dropdown-item text-danger" onclick="document.getElementById('logoutForm').submit(); return false;">
                                                <i class="bi bi-box-arrow-right me-1"></i>Đăng xuất
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </nav>
        </header>
    </body>
</html>

