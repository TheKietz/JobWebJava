<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
        <meta name="description" content=""/>
        <meta name="author" content=""/>
        <title>Dashtreme Admin - Free Dashboard for Bootstrap 4 by Codervent</title>
        <!-- loader-->
        <link href="<c:url value='/template/assets/css/pace.min.css'/>" rel="stylesheet"/>
        <script src="<c:url value='/template/assets/js/pace.min.js'/>"></script>
        <!--favicon-->
        <link rel="icon" href="<c:url value='assets/images/favicon.ico'/>" type="image/x-icon">
        <!-- Vector CSS -->
        <link href="<c:url value='/template/assets/plugins/vectormap/jquery-jvectormap-2.0.2.css'/>" rel="stylesheet"/>
        <!-- simplebar CSS-->
        <link href="<c:url value='/template/assets/plugins/simplebar/css/simplebar.css'/>" rel="stylesheet"/>
        <!-- Bootstrap core CSS-->
        <link href="<c:url value='/template/assets/css/bootstrap.min.css'/>" rel="stylesheet"/>
        <!-- animate CSS-->
        <link href="<c:url value='/template/assets/css/animate.css'/>" rel="stylesheet" type="text/css"/>
        <!-- Icons CSS-->
        <link href="<c:url value='/template/assets/css/icons.css'/>" rel="stylesheet" type="text/css"/>
        <!-- Sidebar CSS-->
        <link href="<c:url value='/template/assets/css/sidebar-menu.css'/>" rel="stylesheet"/>
        <!-- Custom Style-->
        <link href="<c:url value='/template/assets/css/app-style.css'/>" rel="stylesheet"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.css">

        <script>
            document.querySelectorAll('.sidebar-toggle').forEach(function (toggle) {
                toggle.addEventListener('click', function (e) {
                    e.preventDefault();
                    var submenu = this.nextElementSibling;
                    submenu.style.display = submenu.style.display === 'block' ? 'none' : 'block';
                });
            });
        </script>
    </head>
    <body class="bg-theme bg-dark">

        <!-- Start wrapper-->
        <div id="wrapper">

            <!--Start sidebar-wrapper-->
            <div id="sidebar-wrapper" data-simplebar="" data-simplebar-auto-hide="true">
                <div class="brand-logo">
                    <a href="index.html">
                        <img src="<c:url value='/template/assets/images/logo-icon.png'/>" class="logo-icon" alt="logo icon">
                        <h5 class="logo-text">Dashtreme Admin</h5>
                    </a>
                </div>
                <ul class="sidebar-menu do-nicescrol">
                    <li class="sidebar-header">MAIN NAVIGATION</li>
                    <li>
                        <a href="${pageContext.request.contextPath}/dashboard">
                            <i class="zmdi zmdi-view-dashboard"></i> <span>Dashboard</span>
                        </a>
                    </li>     
                    <li>
                        <a href="javascript:void(0);" class="sidebar-toggle">
                            <i class="zmdi zmdi-account"></i>
                            <span>User Management</span>
                            <i class='fas fa-angle-down fa-3x'></i>
                        </a>
                        <ul class="sidebar-submenu">
                            <li >
                                <a href="${pageContext.request.contextPath}/admin/users">
                                    <i class="zmdi zmdi-dot-circle-alt"></i> Users
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/admin/employers">
                                    <i class="zmdi zmdi-dot-circle-alt"></i> Employers
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/admin/candidates">
                                    <i class="zmdi zmdi-dot-circle-alt"></i> Candidates
                                </a>
                            </li>
                        </ul>
                    </li>                    
                    <li>
                        <a href="${pageContext.request.contextPath}/admin/jobs">
                            <i class="zmdi zmdi-case-check"></i> <span>Jobs</span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/admin/applications">
                            <i class="zmdi zmdi-assignment-account"></i> <span>Applications</span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/admin/logout" target="_blank">
                            <i class="zmdi zmdi-lock"></i> <span>Logout</span>
                        </a>
                    </li> 
                    <li class="sidebar-header">LABELS</li>
                    <li><a href="javaScript:void();"><i class="zmdi zmdi-coffee text-danger"></i> <span>Important</span></a></li>
                    <li><a href="javaScript:void();"><i class="zmdi zmdi-chart-donut text-success"></i> <span>Warning</span></a></li>
                    <li><a href="javaScript:void();"><i class="zmdi zmdi-share text-info"></i> <span>Information</span></a></li>

                </ul>

            </div>
            <!--End sidebar-wrapper-->

            <!--Start topbar header-->
            <header class="topbar-nav">
                <nav class="navbar navbar-expand fixed-top">
                    <ul class="navbar-nav mr-auto align-items-center">
                        <li class="nav-item">
                            <a class="nav-link toggle-menu" href="javascript:void();">
                                <i class="icon-menu menu-icon"></i>
                            </a>
                        </li>
<!--                        <li class="nav-item">
                            <form class="search-bar" action="${pageContext.request.contextPath}/admin/users" method="get">
                                <input type="hidden" name="page" value="1">
                                <input type="hidden" name="size" value="${pageSize}">
                                <input type="search" class="form-control" name="keyword" aria-label="Search" placeholder="Search by company name or email" value="${fn:escapeXml(keyword)}">
                                <a href="javascript:void(0);" onclick="this.closest('form').submit();"><i class="icon-magnifier"></i></a>
                            </form>
                        </li>-->

                    </ul>

                    <ul class="navbar-nav align-items-center right-nav-link">
                        <li class="nav-item dropdown-lg">
                            <a class="nav-link dropdown-toggle dropdown-toggle-nocaret waves-effect" data-toggle="dropdown" href="javascript:void();">
                                <i class="fa fa-envelope-open-o"></i>Message</a>
                        </li>
                        <li class="nav-item dropdown-lg">
                            <a class="nav-link dropdown-toggle dropdown-toggle-nocaret waves-effect" data-toggle="dropdown" href="javascript:void();">
                                <i class="fa fa-bell-o"></i>Notice</a>
                        </li>
                        <li class="nav-item language">
                            <a class="nav-link dropdown-toggle dropdown-toggle-nocaret waves-effect" data-toggle="dropdown" href="javascript:void();"><i class="fa fa-flag"></i></a>
                            <ul class="dropdown-menu dropdown-menu-right">
                                <li class="dropdown-item"> <i class="flag-icon flag-icon-gb mr-2"></i> English</li>
                                <li class="dropdown-item"> <i class="flag-icon flag-icon-fr mr-2"></i> French</li>
                                <li class="dropdown-item"> <i class="flag-icon flag-icon-cn mr-2"></i> Chinese</li>
                                <li class="dropdown-item"> <i class="flag-icon flag-icon-de mr-2"></i> German</li>
                            </ul>
                        </li>
                        <li class="nav-item">
<!--                            <a class="nav-link dropdown-toggle dropdown-toggle-nocaret" data-toggle="dropdown" href="#">
                                <span class="user-profile">
                                    <img src="${pageContext.request.contextPath}/resources/images/avatar.jpg" class="img-circle" alt="user avatar">
                                </span>
                            </a>-->
                            <ul class="dropdown-menu dropdown-menu-right">
                                <li class="dropdown-item user-details">
                                    <a href="javaScript:void();">
                                        <div class="media">
                                            
                                            <div class="media-body">
                                                <h6 class="mt-2 user-title">Sarajhon Mccoy</h6>
                                                <p class="user-subtitle">mccoy@example.com</p>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                                <li class="dropdown-divider"></li>
                                <li class="dropdown-item"><i class="icon-envelope mr-2"></i> Inbox</li>
                                <li class="dropdown-divider"></li>
                                <li class="dropdown-item"><i class="icon-wallet mr-2"></i> Account</li>
                                <li class="dropdown-divider"></li>
                                <li class="dropdown-item"><i class="icon-settings mr-2"></i> Setting</li>
                                <li class="dropdown-divider"></li>
                                <li class="dropdown-item"><i class="icon-power mr-2"></i> Logout</li>
                            </ul>
                        </li>
                    </ul>
                </nav>
            </header>
            <!--End topbar header-->

            <div class="clearfix"></div>
