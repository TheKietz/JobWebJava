<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<div class="content mt-5 mb-5">
    <div class="card card-user">
        <div class="card-header">
            <h5 class="card-title">Chỉnh sửa thông tin</h5>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/profile/edit" method="post">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label>Họ và tên</label>
                        <input type="text" class="form-control" name="fullName" value="${profile.fullName}">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label>Email</label>
                        <input type="email" class="form-control" value="${profile.email}" disabled>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label>Số điện thoại</label>
                        <input type="text" class="form-control" name="phone" value="${profile.phone}">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label>Vị trí</label>
                        <input type="text" class="form-control" name="experienceLevel" value="${profile.experienceLevel}">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label>Khu vực làm việc</label>
                        <input type="text" class="form-control" name="location" value="${profile.location}">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label>Kỹ năng</label>
                        <input type="text" class="form-control" name="skills" value="${profile.skills}">
                    </div>
                </div>

                <div class="row mt-3">
                    <div class="text-center">
                        <button type="submit" class="btn btn-primary px-4 py-2">Lưu thay đổi</button>
                        <a href="${pageContext.request.contextPath}/profile" class="btn btn-secondary px-4 py-2 ms-2">Hủy</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>


