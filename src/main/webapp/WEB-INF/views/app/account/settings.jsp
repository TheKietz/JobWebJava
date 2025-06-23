<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="content">
    <div class="row">
        <div class="col-md-12">
            <!-- CARD CHUNG BO GÓC CHỨA SIDEBAR + MAIN -->
            <div class="card shadow-sm" style="border-radius: 10px;">
                <div class="row g-0">
                    <!-- Sidebar -->
                    <div class="col-md-3 border-end" style="background-color: #f8f9fa; border-top-left-radius: 10px; border-bottom-left-radius: 10px;">
                        <div class="list-group list-group-flush py-3 px-2">
                            <a href="${pageContext.request.contextPath}/app/settings/password-login" class="list-group-item list-group-item-action border-0">
                                <i class="fa fa-lock me-2"></i> Đổi mật khẩu
                            </a>
                            <a href="${pageContext.request.contextPath}/app/settings" class="list-group-item list-group-item-action border-0 active">
                                <i class="fa fa-user me-2"></i> Thông tin cá nhân
                            </a>
                            <a href="${pageContext.request.contextPath}/app/settings/business-license" class="list-group-item list-group-item-action border-0">
                                <i class="fa fa-file-alt me-2"></i> Giấy đăng ký doanh nghiệp
                            </a>
                            <a href="${pageContext.request.contextPath}/app/settings/company" class="list-group-item list-group-item-action border-0">
                                <i class="fa fa-building me-2"></i> Thông tin công ty
                            </a>
                            <a href="${pageContext.request.contextPath}/app/settings/general" class="list-group-item list-group-item-action border-0">
                                <i class="fa fa-cog me-2"></i> Cài đặt
                            </a>
                        </div>
                    </div>

                    <!-- Main Content -->
                    <div class="col-md-9 px-4 py-4">
                        <h5 class="text-success">Tài khoản xác thực: <strong>Cấp 1/3</strong></h5>
                        <p>Nâng cấp tài khoản lên <strong class="text-success">cấp 2/3</strong> để nhận <strong class="text-success">100 lượt xem CV</strong> ứng viên từ công cụ tìm kiếm CV.</p>
                        <hr/>
                        <h6 class="mb-3">Vui lòng thực hiện các bước xác thực dưới đây:</h6>

                        <ul class="list-group mb-4">
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                <span><i class="fa fa-check-circle text-muted me-2"></i> Xác thực số điện thoại</span>
                                <a href="${pageContext.request.contextPath}/app/phone-verify" class="btn btn-outline-success btn-sm">→</a>
                            </li>
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                <span><i class="fa fa-building text-muted me-2"></i> Cập nhật thông tin công ty</span>
                                <a href="${pageContext.request.contextPath}/app/settings/company" class="btn btn-outline-success btn-sm">→</a>
                            </li>
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                <span><i class="fa fa-file-alt text-muted me-2"></i> Xác thực Giấy đăng ký doanh nghiệp</span>
                                <a href="${pageContext.request.contextPath}/app/settings/business-license" class="btn btn-outline-success btn-sm">→</a>
                            </li>
                        </ul>

                        <div class="d-flex justify-content-end">
                            <a href="#" class="btn btn-outline-primary">Tìm hiểu thêm</a>
                        </div>
                        <br><!-- comment -->
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Avatar</label><br>
                                    <div class="avatar mb-2" style="width: 80px; height: 80px; background-image: url('<c:url value='${user.avatarUrl}'/>'); background-size: cover; background-position: center; border-radius: 50%;"></div>
                                    <input type="file" name="avatarFile" accept="image/*" class="form-control"/>
                                </div>
                            </div>
                            <div class="col-md-8">
                                <div class="form-group">
                                    <label>Email</label>
                                    <input type="text" class="form-control" value="${user.email}" readonly />
                                </div>
                            </div>
                        </div>
                        <br>
                        <div class="col-md-9 px-4 py-4">
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="title">Cập nhật thông tin cá nhân</h5>
                                </div>
                                <div class="card-body">
                                    <form:form modelAttribute="user" action="${pageContext.request.contextPath}/app/settings" method="POST" enctype="multipart/form-data">
                                        <input type="hidden" name="csrfToken" value="${csrfToken}"/>

                                        <div class="mb-3">
                                            <label class="form-label">Họ và tên</label>
                                            <form:input path="fullName" class="form-control"/>
                                            <form:errors path="fullName" cssClass="text-danger"/>
                                        </div>

                                        <div class="mb-3">
                                            <label class="form-label">Giới tính</label>
                                            <form:select path="gender" class="form-select">
                                                <form:option value="">Giới tính</form:option>
                                                <form:option value="MALE">Nam</form:option>
                                                <form:option value="FEMALE">Nữ</form:option>
                                                <form:option value="OTHER">Khác</form:option>
                                            </form:select>
                                            <form:errors path="gender" cssClass="text-danger"/>
                                        </div>

                                        <div class="mb-3">
                                            <label class="form-label">Số điện thoại</label>
                                            <form:input path="phone" class="form-control" readonly="true"/>
                                        </div>

                                        <div class="d-flex justify-content-between">
                                            <a href="${pageContext.request.contextPath}/app/dashboard" class="btn btn-secondary">Hủy</a>
                                            <button type="submit" class="btn btn-primary">Lưu</button>
                                        </div>
                                    </form:form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

