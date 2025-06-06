<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="utf-8"/>
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
  <meta name="description" content="Đăng nhập quản trị"/>
  <meta name="author" content="Codervent"/>
  <title>Quản Trị - Đăng Nhập</title>
  <!-- loader-->
  <link href="<c:url value='/template/assets/css/pace.min.css'/>" rel="stylesheet"/>
  <script src="<c:url value='/template/assets/js/pace.min.js'/>"></script>
  <!--favicon-->
  <link rel="icon" href="<c:url value='/template/assets/images/favicon.ico'/>" type="image/x-icon">
  <!-- Bootstrap core CSS-->
  <link href="<c:url value='/template/assets/css/bootstrap.min.css'/>" rel="stylesheet"/>
  <!-- animate CSS-->
  <link href="<c:url value='/template/assets/css/animate.css'/>" rel="stylesheet" type="text/css"/>
  <!-- Icons CSS-->
  <link href="<c:url value='/template/assets/css/icons.css'/>" rel="stylesheet" type="text/css"/>
  <!-- Custom Style-->
  <link href="<c:url value='/template/assets/css/app-style.css'/>" rel="stylesheet"/>
</head>

<body class="bg-theme bg-theme1">
<!-- Start wrapper-->
<div id="wrapper">
  <div class="loader-wrapper"><div class="lds-ring"><div></div><div></div><div></div><div></div></div></div>
  <div class="card card-authentication1 mx-auto my-5">
    <div class="card-body">
      <div class="card-content p-2">
        <div class="text-center">
          <img src="<c:url value='/template/assets/images/logo-icon.png'/>" alt="logo icon">
        </div>
        <div class="card-title text-uppercase text-center py-3">Đăng Nhập</div>
        <c:if test="${not empty error}">
          <div class="alert alert-danger">${fn:escapeXml(error)}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/admin/login" method="post" class="needs-validation" novalidate>
          <div class="form-group">
            <label for="email" class="sr-only">Email</label>
            <div class="position-relative has-icon-right">
              <input type="email" id="email" name="email" class="form-control input-shadow"
                     value="${fn:escapeXml(loginForm.email)}" placeholder="Nhập Email" required>
              <div class="form-control-position">
                <i class="icon-user"></i>
              </div>
              <c:if test="${not empty result.getFieldError('email')}">
                <small class="text-danger">${fn:escapeXml(result.getFieldError('email').defaultMessage)}</small>
              </c:if>
            </div>
          </div>
          <div class="form-group">
            <label for="password" class="sr-only">Mật Khẩu</label>
            <div class="position-relative has-icon-right">
              <input type="password" id="password" name="password" class="form-control input-shadow"
                     placeholder="Nhập Mật Khẩu" required>
              <div class="form-control-position">
                <i class="icon-lock"></i>
              </div>
              <c:if test="${not empty result.getFieldError('password')}">
                <small class="text-danger">${fn:escapeXml(result.getFieldError('password').defaultMessage)}</small>
              </c:if>
            </div>
          </div>
          <button type="submit" class="btn btn-light btn-block">Đăng Nhập</button>
        </form>
      </div>
    </div>
  </div>
</div><!--wrapper-->

<!-- Bootstrap core JavaScript-->
<script src="<c:url value='/template/assets/js/jquery.min.js'/>"></script>
<script src="<c:url value='/template/assets/js/popper.min.js'/>"></script>
<script src="<c:url value='/template/assets/js/bootstrap.min.js'/>"></script>

<!-- Custom scripts -->
<script src="<c:url value='/template/assets/js/app-script.js'/>"></script>

<!-- Client-side validation -->
<script>
  (function () {
    'use strict';
    var forms = document.querySelectorAll('.needs-validation');
    Array.prototype.slice.call(forms).forEach(function (form) {
      form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
          event.preventDefault();
          event.stopPropagation();
        }
        form.classList.add('was-validated');
      }, false);
    });
  })();
</script>
</body>
</html>