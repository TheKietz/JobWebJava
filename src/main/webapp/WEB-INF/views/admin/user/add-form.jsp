
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="content-wrapper">
    <div class="container-fluid">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">${user.id == null ? 'Thêm người dùng' : 'Chỉnh sửa người dùng'}</h5>
                        <hr>
                        <!-- Hiển thị lỗi chung -->
                        <c:if test="${not empty result && result.hasErrors()}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                Vui lòng sửa các lỗi sau:
                                <ul>
                                    <c:forEach items="${result.allErrors}" var="error">
                                        <li>${fn:escapeXml(error.defaultMessage)}</li>
                                    </c:forEach>
                                </ul>
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                ${fn:escapeXml(error)}
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>
                        <c:if test="${not empty success}">
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                                ${fn:escapeXml(success)}
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>
                        <form:form method="post" modelAttribute="user" action="${pageContext.request.contextPath}/admin/users/save" id="userForm">
                            <form:hidden path="id"/>
                            <form:hidden path="createdAt"/>
                            <form:hidden path="role" value="${user.role != null ? user.role.name() : 'ADMIN'}"/>
                            <input type="hidden" name="size" value="${pageSize}"/>
                            <input type="hidden" name="keyword" value="${fn:escapeXml(keyword)}"/>
                            <div class="mb-3">
                                <label for="fullName" class="form-label">Họ và tên <span class="text-danger">*</span></label>
                                <form:input path="fullName" cssClass="form-control" id="fullName" placeholder="Nhập họ và tên" required="true"/>
                                <form:errors path="fullName" cssClass="text-danger small"/>
                            </div>
                            <div class="mb-3">
                                <label for="email" class="form-label">Email <span class="text-danger">*</span></label>
                                <form:input path="email" type="email" cssClass="form-control" id="email" placeholder="Nhập địa chỉ email" required="true"/>
                                <form:errors path="email" cssClass="text-danger small"/>
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label">Mật khẩu <span class="text-danger">${user.id == null ? '*' : ''}</span></label>
                                <form:input path="password" type="password" cssClass="form-control" id="password" placeholder="Nhập mật khẩu" autocomplete="off" required="${user.id == null ? 'true' : 'false'}"/>
                                <form:errors path="password" cssClass="text-danger small"/>
                                <small class="form-text text-muted">
                                    ${user.id != null ? 'Để trống để giữ mật khẩu hiện tại. ' : ''}Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường và số.
                                </small>
                            </div>
                            <div class="mb-3">
                                <label for="passwordConfirm" class="form-label">Xác nhận mật khẩu <span class="text-danger">${user.id == null ? '*' : ''}</span></label>
                                <input type="password" name="passwordConfirm" class="form-control" id="passwordConfirm" placeholder="Nhập lại mật khẩu" autocomplete="off" ${user.id == null ? 'required' : ''}/>
                                <span id="passwordConfirmError" class="text-danger small"></span>
                            </div>
                            <div class="mb-3">
                                <label for="phone" class="form-label">Số điện thoại</label>
                                <form:input path="phone" cssClass="form-control" id="phone" placeholder="Nhập số điện thoại"/>
                                <form:errors path="phone" cssClass="text-danger small"/>
                            </div>
                            <div class="mb-3">
                                <label for="roleDisplay" class="form-label">Vai trò</label>
                                <input type="text" class="form-control" id="roleDisplay" value="${user.role != null ? user.role.name() : 'ADMIN'}" disabled="true"/>
                                <form:errors path="role" cssClass="text-danger small"/>
                            </div>
                            <div class="mb-3">
                                <label for="status" class="form-label">Trạng thái</label>
                                <form:select path="status" cssClass="form-control" id="status">
                                    <form:option value="ACTIVE" label="Kích hoạt"/>
                                    <form:option value="INACTIVE" label="Không kích hoạt"/>
                                </form:select>
                                <form:errors path="status" cssClass="text-danger small"/>
                            </div>
                            <div class="mb-3">
                                <button type="submit" class="btn btn-primary">Lưu</button>
                                <a href="${pageContext.request.contextPath}/admin/users?page=1&size=${pageSize}&keyword=${fn:escapeXml(keyword)}" class="btn btn-secondary">Hủy</a>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
        <div class="overlay toggle-menu"></div>
    </div>
</div>

<script>
    document.getElementById('userForm').addEventListener('submit', function(event) {
        const password = document.getElementById('password').value;
        const passwordConfirm = document.getElementById('passwordConfirm').value;
        const passwordConfirmError = document.getElementById('passwordConfirmError');
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;

        // Reset error
        passwordConfirmError.textContent = '';

        // Validate password strength for new user or when password is entered
        if (password || ${user.id == null}) {
            if (!passwordRegex.test(password)) {
                event.preventDefault();
                passwordConfirmError.textContent = 'Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường và số.';
                return;
            }
            if (password !== passwordConfirm) {
                event.preventDefault();
                passwordConfirmError.textContent = 'Mật khẩu và xác nhận mật khẩu không khớp.';
                return;
            }
        }
    });
</script>
