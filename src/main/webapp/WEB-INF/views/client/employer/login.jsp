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
                                    <a href="${pageContext.request.contextPath}/home">
                                        <img src="${pageContext.request.contextPath}/template/assets/images/logo-icon.png" alt="Logo"/>
                                    </a>

                                    <h2 class="fw-bold mb-2 text-uppercase">Login</h2>
                                    <p class="text-white-50 mb-5">Nhập email và mật khẩu để đăng nhập!</p>

                                    <c:if test="${not empty error}">
                                        <div class="error mb-4">${fn:escapeXml(error)}</div>
                                    </c:if>

                                    <form action="${pageContext.request.contextPath}/employers/login" method="post">
                                        <div class="form-outline form-white mb-4">
                                            <input type="text" name="email" placeholder="Email" />
                                        </div>

                                        <div class="form-outline form-white mb-4">
                                            <input type="password" name="passwordHash" placeholder="Password" />
                                        </div>

                                        <p class="small mb-5 pb-lg-2">
                                            <a class="text-white-50" href="#!">Quên mật khẩu?</a>
                                        </p>

                                        <button type="submit" class="btn btn-outline-light btn-lg px-5">Login</button>
                                    </form>


                                    <div class="d-flex justify-content-center text-center mt-4 pt-1">
                                        <a href="#!" class="text-white"><i class="fab fa-facebook-f fa-lg"></i></a>
                                        <a href="#!" class="text-white"><i class="fab fa-twitter fa-lg mx-4 px-2"></i></a>
                                        <a href="#!" class="text-white"><i class="fab fa-google fa-lg"></i></a>
                                    </div>
                                </div>

                                <div>
                                    <p class="mb-0">Chưa có tài khoản? 
                                        <a href="${pageContext.request.contextPath}/employers/register" class="text-white-50 fw-bold">Đăng ký</a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>