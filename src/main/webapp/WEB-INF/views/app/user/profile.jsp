<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<head>
    <style>
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
            
                <!-- Navbar -->
                
                <!-- End Navbar -->
                <div class="content">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="card card-user">
                                <div class="image">
                                    <img src="${pageContext.request.contextPath}/template/app/assets/img/damir-bosnjak.jpg" alt="Back ground">
                                </div>
                                
                                <div class="card-body">
                                    <div class="author">
                                        <a href="#">
                                            <img class="avatar border-gray" src="${pageContext.request.contextPath}${employer.logoUrl}" alt="Logo">
                                            <h5 class="title">${user.fullName}</h5>
                                        </a>
                                        <p class="description">
                                            ${user.email}
                                        </p>
                                    </div>
                                    <h5 class="description text-center">
                                       
                                    </h5>
                                </div>
                                <div class="card-footer">
                                    <hr>
                                    <div class="button-container">
                                        <div class="row">
                                            <div class="col-lg-3 col-md-6 col-6 ml-auto">
                                                <h5>${countJob}<br><small>Bài đăng</small></h5>
                                            </div>
                                            <div class="col-lg-4 col-md-6 col-6 ml-auto mr-auto">
                                                <h5>32<br><small>Ứng viên</small></h5>
                                            </div>
                                            <div class="col-lg-3 mr-auto">
                                                <h5>24<br><small>Ngày</small></h5>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>                            
                        </div>
                        <div class="col-md-8">
                            <div class="card card-user">
                                <div class="card-header">
                                    <h5 class="card-title">Tùy chỉnh thông tin công ty</h5>
                                </div>
                                <div class="card-body">
                                    <form>
                                        <div class="row">
                                            <div class="col-md-7 pr-1">
                                                <div class="form-group">
                                                    <label>Tên công ty</label>
                                                    <input type="text" class="form-control" disabled="" placeholder="Tên công ty" value="${employer.companyName}">
                                                </div>
                                            </div>
                                            <div class="col-md-5 pl-1">
                                                <div class="form-group">
                                                    <label>Giám đốc điều hành</label>
                                                    <input type="text" class="form-control" placeholder="Giám đốc điều hành" value="${user.fullName}">
                                                </div>
                                            </div>                                            
                                        </div>
                                        <div class="row">
                                            <div class="col-md-5 pr-1">
                                                <div class="form-group">
                                                    <label for="exampleInputEmail1">Địa chỉ email</label>
                                                    <input type="email" class="form-control" placeholder="${user.email}">
                                                </div>
                                            </div>
                                            <div class="col-md-3 pl-1">
                                                <div class="form-group">
                                                    <label>Số thành viên</label>
                                                    <input type="text" class="form-control" placeholder="Số thành viên" value="${employer.companySize}">
                                                </div>
                                            </div>
                                            <div class="col-md-4 pl-1">
                                                <div class="form-group">
                                                    <label>Đường dẫn web</label>
                                                    <input type="text" class="form-control" placeholder="Đường dẫn web" value="${employer.website}">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label>Địa chỉ</label>
                                                    <input type="text" class="form-control" placeholder="Home Address" value="${employer.address}">
                                                </div>
                                            </div>
                                        </div>                                        
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label>Thông tin giới thiệu</label>
                                                    <textarea class="form-control textarea"> ${employer.description}</textarea>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="update ml-auto mr-auto">
                                                <button type="submit" class="btn btn-light-green btn-round">Cập nhật thông tin</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                