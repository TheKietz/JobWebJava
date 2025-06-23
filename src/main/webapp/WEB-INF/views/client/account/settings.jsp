<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Account Settings</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" rel="stylesheet">
    <link href="<c:url value='/template/assets1/css/style.css'/>" rel="stylesheet">
</head>
<body>
    <main class="page-container">
        <div class="breadcrumb-box">
            <h6 class="breadcrumb-title d-flex">
                <span>Cài đặt tài khoản</span>
            </h6>
        </div>
        <div class="container-fluid page-content">
            <div class="d-flex shadow-sm">
                <!-- Sidebar -->
                <div class="list-group rounded">
                    <a href="<c:url value='/account/settings/password-login'/>" class="list-group-item list-group-item-action border-0">
                        <i class="fa fa-lock mr-2"></i> Đổi mật khẩu
                    </a>
                    <a href="<c:url value='/account/settings'/>" class="list-group-item list-group-item-action border-0 active">
                        <i class="fa fa-user mr-2"></i> Thông tin cá nhân
                    </a>
                    <c:if test="${user.role == 'EMPLOYER'}">
                        <a href="<c:url value='/account/settings/company'/>" class="list-group-item list-group-item-action border-0">
                            <i class="fa fa-building mr-2"></i> Thông tin công ty
                        </a>
                    </c:if>
                    <c:if test="${user.role == 'CANDIDATE'}">
                        <a href="<c:url value='/account/settings/cv-applied'/>" class="list-group-item list-group-item-action border-0">
                            <i class="fa fa-gear mr-2"></i> Cài đặt ứng tuyển
                        </a>
                    </c:if>
                </div>
                <!-- Main Content -->
                <div class="bg-white w-100 rounded">
                    <div class="card-body setting-form mt-3">
                        <!-- Verification Status -->
                        <div class="authen-level">
                            <div class="title px-3 pt-3">
                                Tài khoản xác thực: <span class="text-primary">Cấp 1/3</span>
                            </div>
                            <div class="p-3 border-bottom">
                                <div class="d-flex mb-3 align-items-center">
                                    <div class="mr-3">
                                        <img src="<c:url value='/images/star.png'/>" width="40">
                                    </div>
                                    <div>
                                        Nâng cấp tài khoản lên <span class="text-primary font-weight-bold">cấp 2/3</span> để nhận
                                        <span class="text-primary font-weight-bold">100 lượt xem CV ứng viên</span>.
                                    </div>
                                </div>
                                <div class="verify-step-title">Vui lòng thực hiện các bước xác thực dưới đây:</div>
                                <div class="verify-content">
                                    <div class="verify-content__progress">
                                        <div class="d-flex justify-content-between">
                                            <span class="verify-content__progress__title">Xác thực thông tin</span>
                                            <span>Hoàn thành <span class="text-primary">0%</span></span>
                                        </div>
                                        <div class="progress">
                                            <div class="progress-bar" role="progressbar" style="width: 0%;" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
                                        </div>
                                    </div>
                                    <div class="verify-item not-verified">
                                        <div class="d-flex align-items-center">
                                            <i class="fa fa-circle text-20 step-icon"></i>
                                            <div class="ml-2 font-weight-600">Xác thực số điện thoại</div>
                                        </div>
                                        <a href="<c:url value='/account/phone-verify'/>" class="text-primary btn-to-verify">
                                            <i class="fa fa-arrow-right"></i>
                                        </a>
                                    </div>
                                    <div class="verify-item not-verified">
                                        <div class="d-flex align-items-center">
                                            <i class="fa fa-circle text-20 step-icon"></i>
                                            <div class="ml-2 font-weight-600">
                                                <c:choose>
                                                    <c:when test="${user.role == 'EMPLOYER'}">Cập nhật thông tin công ty</c:when>
                                                    <c:otherwise>Cập nhật thông tin cá nhân</c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                        <a href="<c:url value='/account/settings'/>" class="text-primary btn-to-verify">
                                            <i class="fa fa-arrow-right"></i>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Update Form -->
                        <form:form modelAttribute="user" action="/account/settings" method="POST" enctype="multipart/form-data">
                            <input type="hidden" name="csrfToken" value="${csrfToken}"/>
                            <div class="card-body setting-form mt-3">
                                <div class="font-weight-600 mb-3">Cập nhật thông tin cá nhân</div>
                                <c:if test="${not empty error}">
                                    <p class="text-danger">${error}</p>
                                </c:if>
                                <c:if test="${not empty success}">
                                    <p class="text-success">${success}</p>
                                </c:if>
                                <div class="row">
                                    <div class="form-group col-md-6">
                                        <div class="d-flex align-items-center">
                                            <label class="col-form-label mr-2">Avatar</label>
                                            <div class="mr-2 avatar" style="width: 40px; height: 40px; background-image: url('<c:url value='${user.avatarUrl}'/>');"></div>
                                            <div>
                                                <input type="file" name="avatarFile" accept="image/*" class="d-none" id="avatarFile"/>
                                                <button type="button" class="btn btn-light" onclick="document.getElementById('avatarFile').click()">Đổi avatar</button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label class="col-form-label">Email: ${user.email}</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-6">
                                        <label>Họ và tên</label>
                                        <form:input path="fullName" class="form-control" placeholder="Nhập họ và tên"/>
                                        <form:errors path="fullName" cssClass="text-danger"/>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label>Giới tính</label>
                                        <form:select path="gender" class="form-control">
                                            <form:option value="">Chọn giới tính</form:option>
                                            <form:option value="MALE">Nam</form:option>
                                            <form:option value="FEMALE">Nữ</form:option>
                                            <form:option value="OTHER">Khác</form:option>
                                        </form:select>
                                        <form:errors path="gender" cssClass="text-danger"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-6">
                                        <div class="d-flex justify-content-between">
                                            <label>Số điện thoại</label>
                                            <div>
                                                <a href="<c:url value='/account/phone-verify'/>" class="text-primary pr-2">Cập nhật</a>
                                                <a href="<c:url value='/account/phone-verify'/>" class="text-primary pl-2">Xác thực</a>
                                            </div>
                                        </div>
                                        <form:input path="phone" class="form-control" readonly="true"/>
                                        <form:errors path="phone" cssClass="text-danger"/>
                                    </div>
                                </div>
                                <hr>
                                <div class="form-group mb-0 text-right">
                                    <a href="<c:url value='/dashboard'/>" class="btn btn-secondary mr-2">Hủy</a>
                                    <button type="submit" class="btn btn-primary">Lưu</button>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </main>
</body>
</html>
<style>
    .avatar { background-size: cover; background-position: center; border-radius: 50%; }
    .list-group-item.active { background-color: #007bff; color: white; }
    .verify-item { display: flex; justify-content: space-between; align-items: center; margin-top: 10px; }
    .text-20 { font-size: 20px; }
</style>
