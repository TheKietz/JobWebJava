<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="content-wrapper">
    <div class="container-fluid">            
        <div class="row justify-content-center"> 
            <div class="col-md-6"> 
                <div class="card">
                    <div class="card-body">
                        <div class="card-title">Vertical Form</div>
                        <hr>                    
                        <form:form method="post" modelAttribute="user" action="${pageContext.request.contextPath}/admin/users/save">
                            <form:hidden path="userID"/> 
                            <form:hidden path="createdAt"/>
                            <div class="form-group">
                                <label for="fullName">Name</label>
                                <form:input path="fullName" cssClass="form-control" id="fullName" placeholder="Enter Your Name"/>
                                <form:errors path="fullName" cssClass="text-danger"/>
                            </div>
                            <div class="form-group">
                                <label for="email">Email</label>
                                <form:input path="email" cssClass="form-control" id="email" placeholder="Enter Your Email Address"/>
                                <form:errors path="email" cssClass="text-danger"/>
                            </div>                            
                            <div class="form-group">
                                <label for="password">Password</label>
                                <form:password path="password" cssClass="form-control" id="password" placeholder="Enter Your Password"/>
                                <form:errors path="password" cssClass="text-danger"/>
                            </div>
                            <div class="form-group">
                                <label for="role">Role</label>
                                <form:input path="role" cssClass="form-control" id="role" value="ADMIN" readonly="true"/>
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary">Lưu</button>
                            </div>
                        </form:form>
                    </div>
                </div>   
            </div>
        </div>
        <!--End Row-->
        <!--start overlay-->
        <div class="overlay toggle-menu"></div>
        <!--end overlay-->
    </div>
    <!-- End container-fluid-->
</div>