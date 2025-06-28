<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<head>
    <style>
        .btn-light-blue {
            background-color: #3498db;
            color: white;
            border: none;
        }
        .btn-light-blue:hover {
            background-color: #2980b9;
        }
        .card-user {
            height: 100%;
        }
    </style>
</head>

<div class="content mt-5 mb-5">
    <div class="row">
        <div class="col-md-4 d-flex flex-column">
            <div class="card card-user h-100">
                <div class="card-body">
                    <div class="author text-center">
                        <img src="<c:url value='/template/client/assets1/img/avata.png'/>" alt="Avatar" class="rounded-circle me-2" width="150" height="150">
                        <h5 class="title mt-2">${profile.fullName}</h5>
                        <p class="description">${profile.email}</p>
                    </div>
                </div>
                <div class="card-footer">
                    <hr>
                    <div class="row text-center">
                        <div class="col-6">
                            <h5><small>Vị trí</small></h5>
                            <p>${profile.experienceLevel}</p>
                        </div>
                        <div class="col-6">
                            <h5><small>Khu vực làm việc</small></h5>
                            <p>${profile.location}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-md-8 d-flex flex-column">
            <div class="card card-user h-100">
                <div class="card-header">
                    <h5 class="card-title">Thông tin cá nhân ứng viên</h5>
                </div>
                <div class="card-body">
                    <form>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <div class="form-group">
                                    <label>Họ và tên</label>
                                    <input type="text" class="form-control mt-2" disabled value="${profile.fullName}">
                                </div>
                            </div>
                            <div class="col-md-6 mb-3">
                                <div class="form-group">
                                    <label>Email</label>
                                    <input type="email" class="form-control mt-2" disabled value="${profile.email}">
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <div class="form-group">
                                    <label>Số điện thoại</label>
                                    <input type="text" class="form-control mt-2" disabled value="${profile.phone}">
                                </div>
                            </div>
                            <div class="col-md-6 mb-3">
                                <div class="form-group">
                                    <label>Tạo lúc</label>
                                    <input type="text" class="form-control mt-2" disabled value="${profile.createdAtFormatted}">
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-12 mb-3">
                                <div class="form-group">
                                    <label>Kĩ năng</label>
                                    <input type="text" class="form-control mt-2" disabled value="${profile.skills}">
                                </div>
                            </div>
                        </div>

                        <div class="row mt-4">
                            <div class="col">
                                <div class="d-flex justify-content-start gap-2">
                                    <a href="${pageContext.request.contextPath}/profile/edit"
                                       class="btn btn-light-blue btn-round px-4 py-2">
                                        Chỉnh Sửa Thông Tin
                                    </a>
                                    <a href="${pageContext.request.contextPath}/profile/delete"
                                       class="btn btn-danger btn-round px-4 py-2">
                                        Xoá tài khoản
                                    </a>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

