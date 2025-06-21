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
                            <h2 class="fw-bold mb-2 text-uppercase">Login</h2>
                            <p class="text-white-50 mb-5">Please enter your email and password!</p>

                            <c:if test="${not empty error}">
                                <div class="error mb-4">${fn:escapeXml(error)}</div>
                            </c:if>

                            <form:form method="post" action="${pageContext.request.contextPath}/login" modelAttribute="loginForm">
                                <div class="form-outline form-white mb-4">
                                    <form:input path="email" type="email" id="typeEmailX" cssClass="form-control form-control-lg" placeholder="Email" required="true"/>
                                    
                                    <form:errors path="email" cssClass="error"/>
                                </div>

                                <div class="form-outline form-white mb-4">
                                    <form:input path="passwordHash" type="password" placeholder="Password" id="typePasswordX" cssClass="form-control form-control-lg" required="true"/>
                                   
                                    <form:errors path="passwordHash" cssClass="error"/>
                                </div>

                                <p class="small mb-5 pb-lg-2">
                                    <a class="text-white-50" href="#!">Forgot password?</a>
                                </p>

                                <button type="submit" class="btn btn-outline-light btn-lg px-5">Login</button>
                            </form:form>

                            <div class="d-flex justify-content-center text-center mt-4 pt-1">
                                <a href="#!" class="text-white"><i class="fab fa-facebook-f fa-lg"></i></a>
                                <a href="#!" class="text-white"><i class="fab fa-twitter fa-lg mx-4 px-2"></i></a>
                                <a href="#!" class="text-white"><i class="fab fa-google fa-lg"></i></a>
                            </div>
                        </div>

                        <div>
                            <p class="mb-0">Don't have an account? 
                                <a href="${pageContext.request.contextPath}/signup" class="text-white-50 fw-bold">Sign Up</a>
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