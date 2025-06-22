
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8" />
        <link rel="apple-touch-icon" sizes="76x76" href="${pageContext.request.contextPath}/template/assets3/img/apple-icon.png">
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/template/assets3/img/favicon.png">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <title>
            Paper Dashboard 2 by Creative Tim
        </title>
        <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no' name='viewport' />
        <!--     Fonts and icons     -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700,200" rel="stylesheet" />
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css" rel="stylesheet">
        <!-- CSS Files -->
        <link href="${pageContext.request.contextPath}/template/assets3/css/bootstrap.min.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/template/assets3/css/paper-dashboard.css?v=2.0.1" rel="stylesheet" />
        <!-- CSS Just for demo purpose, don't include it in your project -->
        <link href="${pageContext.request.contextPath}/template/assets3/demo/demo.css" rel="stylesheet" />
    </head>

    <body class="">
        <div class="wrapper ">
            <div class="sidebar" data-color="white" data-active-color="danger">
                <div class="logo">                    
                    <a href="https://www.creative-tim.com" class="simple-text logo-normal">        
                        <div class="logo-image-big">
                            <img src="${pageContext.request.contextPath}/template/assets/img/logo/logo.png">
                        </div> 
                    </a>
                </div>
                <div class="sidebar-wrapper">
                    <ul class="nav">
                        <li class="active ">
                            <a href="javascript:;">
                                <i class="nc-icon nc-bank"></i>
                                <p>Trang chủ</p>
                            </a>
                        </li>
                        <li>
                            <a href="javascript:;">
                                <i class="nc-icon nc-briefcase-24"></i>
                                <p>Tin tuyển dụng</p>
                            </a>
                        </li>
                        <li>
                            <a href="javascript:;">
                                <i class="nc-icon nc-badge"></i>
                                <p>Ứng viên</p>
                            </a>
                        </li>
                        <li>
                            <a href="javascript:;">
                                <i class="nc-icon nc-cart-simple"></i>
                                <p>Gói dịch vụ</p>
                            </a>
                        </li>
                        <li>
                            <a href="javascript:;">
                                <i class="nc-icon nc-settings-gear-65"></i>
                                <p>Cài đặt tài khoản</p>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="main-panel" style="height: 100vh;">
                <!-- Navbar -->
                <nav class="navbar navbar-expand-lg navbar-absolute fixed-top navbar-transparent">
                    <div class="container-fluid">
                        <div class="navbar-wrapper">
                            <div class="navbar-toggle">
                                <button type="button" class="navbar-toggler">
                                    <span class="navbar-toggler-bar bar1"></span>
                                    <span class="navbar-toggler-bar bar2"></span>
                                    <span class="navbar-toggler-bar bar3"></span>
                                </button>
                            </div>
                            <a class="navbar-brand" href="javascript:;">Title</a>
                        </div>
                        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navigation" aria-controls="navigation-index" aria-expanded="false" aria-label="Toggle navigation">
                            <span class="navbar-toggler-bar navbar-kebab"></span>
                            <span class="navbar-toggler-bar navbar-kebab"></span>
                            <span class="navbar-toggler-bar navbar-kebab"></span>
                        </button>
                        <div class="collapse navbar-collapse justify-content-end" id="navigation">
                            <form>
                                <div class="input-group no-border">
                                    <input type="text" value="" class="form-control" placeholder="Search...">
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

                                <li class="nav-item dropdown ">
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
                        </div>
                    </div>
                </nav>
