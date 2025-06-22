
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="vi">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Đăng ký nhà tuyển dụng - Job Board</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <style>
        .register-container {
            max-width: 600px;
            margin: 50px auto;
        }
        .card {
            border: none;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .btn-register {
            background-color: #28a745;
            border-color: #28a745;
        }
        .btn-register:hover {
            background-color: #218838;
            border-color: #218838;
        }
        .error {
            display: block;
        }
    </style>
</head>
<body>
    <div class="register-container">
        <div class="card">
            <div class="card-body p-4">
                <div class="row">
                    <div class="col-md-3">
                        <a href="${pageContext.request.contextPath}/home">
                            <img src="${pageContext.request.contextPath}/template/assets/images/logo-icon.png" alt="Logo"/>
                        </a>
                    </div>
                    <div class="col-md-8">
                        <h3 class="text-center mb-4">Đăng ký nhà tuyển dụng</h3>
                    </div>
                </div>
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
                <form:form method="post" modelAttribute="registerDTO" action="${pageContext.request.contextPath}/app/register" id="registerForm">
                    <h5 class="mb-3">Thông tin tài khoản</h5>
                    <div class="mb-3">
                        <label for="user.fullName" class="form-label">Họ và tên <span class="text-danger">*</span></label>
                        <form:input path="user.fullName" cssClass="form-control" id="user.fullName" placeholder="Nhập họ và tên" required="true"/>
                        <form:errors path="user.fullName" cssClass="text-danger small"/>
                    </div>
                    <div class="mb-3">
                        <label for="user.email" class="form-label">Email <span class="text-danger">*</span></label>
                        <form:input path="user.email" type="email" cssClass="form-control" id="user.email" placeholder="Nhập email" required="true"/>
                        <form:errors path="user.email" cssClass="text-danger small"/>
                    </div>
                    <div class="mb-3">
                        <label for="user.password" class="form-label">Mật khẩu <span class="text-danger">*</span></label>
                        <form:input path="user.password" type="password" cssClass="form-control" id="user.password" placeholder="Nhập mật khẩu" required="true"/>
                        <form:errors path="user.password" cssClass="text-danger small"/>
                        <small class="form-text text-muted">Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường và số.</small>
                    </div>
                    <div class="mb-3">
                        <label for="passwordConfirm" class="form-label">Xác nhận mật khẩu <span class="text-danger">*</span></label>
                        <form:input path="passwordConfirm" type="password" cssClass="form-control" id="passwordConfirm" placeholder="Nhập lại mật khẩu" required="true"/>
                        <form:errors path="passwordConfirm" cssClass="text-danger small"/>
                    </div>
                    <div class="mb-3">
                        <label for="user.phone" class="form-label">Số điện thoại</label>
                        <form:input path="user.phone" cssClass="form-control" id="user.phone" placeholder="Nhập số điện thoại (10 số)"/>
                        <form:errors path="user.phone" cssClass="text-danger small"/>
                    </div>
                    <h5 class="mb-3">Thông tin công ty</h5>
                    <div class="mb-3">
                        <label for="employer.companyName" class="form-label">Tên công ty <span class="text-danger">*</span></label>
                        <form:input path="employer.companyName" cssClass="form-control" id="employer.companyName" placeholder="Nhập tên công ty" required="true"/>
                        <form:errors path="employer.companyName" cssClass="text-danger small"/>
                    </div>
                    <div class="mb-3">
                        <label for="employer.website" class="form-label">Website</label>
                        <form:input path="employer.website" cssClass="form-control" id="employer.website" placeholder="Nhập URL website (ví dụ: https://example.com)"/>
                        <form:errors path="employer.website" cssClass="text-danger small"/>
                    </div>
                    <div class="mb-3">
                        <label for="employer.address" class="form-label">Địa chỉ <span class="text-danger">*</span></label>
                        <form:input path="employer.address" cssClass="form-control" id="employer.address" placeholder="Nhập địa chỉ công ty" required="true"/>
                        <form:errors path="employer.address" cssClass="text-danger small"/>
                    </div>
                    <div class="mb-3">
                        <label for="employer.description" class="form-label">Mô tả công ty</label>
                        <form:textarea path="employer.description" cssClass="form-control" id="employer.description" placeholder="Mô tả ngắn về công ty (tối đa 500 ký tự)" rows="4"/>
                        <form:errors path="employer.description" cssClass="text-danger small"/>
                    </div>
                    <div class="mb-3">
                        <button type="submit" class="btn btn-register w-100">Đăng ký</button>
                    </div>
                    <div class="text-center">
                        <p class="mb-0">Đã có tài khoản? 
                            <a href="${pageContext.request.contextPath}/app/login" class="text-primary fw-bold">Đăng nhập</a>
                        </p>
                    </div>
                </form:form>
            </div>
        </div>
    </div>

    <script>
        document.getElementById('registerForm').addEventListener('submit', function(event) {
            let valid = true;
            const email = document.getElementById('user.email').value;
            const password = document.getElementById('user.password').value;
            const passwordConfirm = document.getElementById('passwordConfirm').value;
            const phone = document.getElementById('user.phone').value;
            const website = document.getElementById('employer.website').value;
            const companyName = document.getElementById('employer.companyName').value;
            const address = document.getElementById('employer.address').value;

            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
            const phoneRegex = /^\d{10}$/;
            const websiteRegex = /^(https?:\/\/)?([\w-]+(\.[\w-]+)+)(\/[\w-./?%&=]*)?$/;

            // Reset errors
            document.querySelectorAll('.text-danger.small').forEach(el => el.textContent = '');

            // Validate email
            if (!emailRegex.test(email)) {
                document.getElementById('user.email').nextElementSibling.textContent = 'Email không hợp lệ';
                valid = false;
            }

            // Validate password
            if (!passwordRegex.test(password)) {
                document.getElementById('user.password').nextElementSibling.textContent = 'Mật khẩu phải có ít nhất 8 ký tự, chứa chữ hoa, chữ thường và số';
                valid = false;
            } else if (password !== passwordConfirm) {
                document.getElementById('passwordConfirm').nextElementSibling.textContent = 'Mật khẩu và xác nhận mật khẩu không khớp';
                valid = false;
            }

            // Validate phone
            if (phone && !phoneRegex.test(phone)) {
                document.getElementById('user.phone').nextElementSibling.textContent = 'Số điện thoại phải có 10 chữ số';
                valid = false;
            }

            // Validate website
            if (website && !websiteRegex.test(website)) {
                document.getElementById('employer.website').nextElementSibling.textContent = 'Website không hợp lệ';
                valid = false;
            }

            // Validate company name
            if (!companyName) {
                document.getElementById('employer.companyName').nextElementSibling.textContent = 'Tên công ty không được để trống';
                valid = false;
            }

            // Validate address
            if (!address) {
                document.getElementById('employer.address').nextElementSibling.textContent = 'Địa chỉ không được để trống';
                valid = false;
            }

            if (!valid) {
                event.preventDefault();
            }
        });
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
