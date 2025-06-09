
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
        <link rel="shortcut icon" type="image/x-icon" href="<c:url value='/template/assets/img/favicon.ico'/>">

        <link rel="stylesheet" href="<c:url value='/template/assets/css/bootstrap.min.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/assets/css/owl.carousel.min.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/assets/css/flaticon.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/assets/css/price_rangs.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/assets/css/slicknav.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/assets/css/animate.min.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/assets/css/magnific-popup.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/assets/css/fontawesome-all.min.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/assets/css/themify-icons.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/assets/css/slick.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/assets/css/nice-select.css'/>">
        <link rel="stylesheet" href="<c:url value='/template/assets/css/style.css'/>">

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
                                    <a href="<c:url value='/'/>"><img src="<c:url value='/template/assets/img/logo/logo.png'/>" alt=""></a>
                                </div>  
                            </div>
                            <div class="col-lg-9 col-md-9">
                                <div class="menu-wrapper">
                                    <!-- Main-menu -->
                                    <div class="main-menu">
                                        <nav class="d-none d-lg-block">
                                            <ul id="navigation">
                                                <li><a href="${pageContext.request.contextPath}/client/home">Home</a></li>
                                                <li><a href="${pageContext.request.contextPath}/jobs">Find a Jobs </a></li>
                                            </ul>
                                        </nav>
                                    </div>          
                                    <!-- Header-btn -->
                                    <div class="header-btn d-none f-right d-lg-block">
                                        <a href="${pageContext.request.contextPath}/login" class="btn head-btn1">Register</a>
                                        <a href="${pageContext.request.contextPath}/login" class="btn head-btn2">Login</a>
                                        <a href="${pageContext.request.contextPath}/profiles/dashboard" class="simple-text logo-mini d-flex align-items-center">
                                            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQlWi6fx13t3nmhNDxOwxj80l8QTzZrnf2_lA&s" alt="Logo" style="height: 30px; margin-right: 8px;">
                                            <span>Profile</span>
                                        </a>
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