<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Job Board - Login</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        .gradient-custom {
            background: linear-gradient(to right, #1a1a2e, #162447, #0f3460);
        }
        .error {
            color: #ff4d4d;
            font-size: 0.9em;
        }
    </style>
</head>

<body>
<section class="vh-100 gradient-custom">
    <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                <div class="card bg-dark text-white" style="border-radius: 1rem;">
                    <div class="card-body p-5 text-center">
                        <div class="mb-md-5 mt-md-4 pb-5">
                            <h2 class="fw-bold mb-2 text-uppercase">Register</h2>
                            <p class="text-white-50 mb-5">Welcome to <strong>JobFinder</strong></p>

                            <c:if test="${not empty error}">
                                <div class="error">${fn:escapeXml(error)}</div>
                            </c:if>
                            <c:if test="${param.registered == 'true'}">
                                <div class="success">Đăng ký thành công! Vui lòng đăng nhập.</div>
                            </c:if>

                            <form:form method="post" action="${pageContext.request.contextPath}/signup" modelAttribute="registerForm">
                                <div class="mb-4">
                                    <form:input path="fullName" type="text" id="fullName" cssClass="form-control form-control-lg" placeholder="Họ và tên" required="true"/>
                                    <form:errors path="fullName" cssClass="error" />
                                </div>

                                <div class="mb-4">
                                    <form:input path="email" type="email" id="email" cssClass="form-control form-control-lg" placeholder="Email" required="true"/>
                                    <form:errors path="email" cssClass="error"/>
                                </div>

                                <div class="mb-4">
                                    <form:input path="phone" type="text" id="phone" cssClass="form-control form-control-lg" placeholder="Số điện thoại"/>
                                    <form:errors path="phone" cssClass="error"/>
                                </div>

                                <div class="mb-4">
                                    <form:input path="password" type="password" id="password" cssClass="form-control form-control-lg" placeholder="Mật khẩu" required="true"/>
                                    <form:errors path="password" cssClass="error"/>
                                </div>

                                <div class="mb-4">
                                    <form:input path="passwordConfirm" type="password" id="passwordConfirm" cssClass="form-control form-control-lg" placeholder="Xác nhận mật khẩu" required="true"/>
                                    <form:errors path="passwordConfirm" cssClass="error"/>
                                </div>

                                <button type="submit" class="btn btn-primary btn-lg px-5">Đăng ký</button>
                            </form:form>

                            <div class="mt-4">
                                <p class="mb-0">Đã có tài khoản? 
                                    <a href="${pageContext.request.contextPath}/login" class="text-primary fw-bold">Đăng nhập</a>
                                </p>
                            </div>
                       </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const allowedPattern = /[^a-zA-Z0-9!@#$%^&*()_+=-]/g;

        const passwordFields = ["password", "passwordConfirm"];
        passwordFields.forEach(function (id) {
            const input = document.getElementById(id);
            if (input) {
                input.addEventListener("input", function () {
                    this.value = this.value.replace(allowedPattern, '');
                });
            }
        });
    });
</script>

</html>
