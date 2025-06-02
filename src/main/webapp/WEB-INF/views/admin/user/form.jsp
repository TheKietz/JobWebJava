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

                        <form:form method="post" modelAttribute="user" action="${pageContext.request.contextPath}/users/save">
                            <form:hidden path="userID"/> 
                            <div class="form-group">
                                <label for="fullName">Name</label>
                                <form:input path="fullName" cssClass="form-control" id="fullName" placeholder="Enter Your Name"/>
                            </div>

                            <div class="form-group">
                                <label for="email">Email</label>
                                <form:input path="email" cssClass="form-control" id="email" placeholder="Enter Your Email Address"/>
                            </div>                            

                            <div class="form-group">
                                <label for="role">Role</label>
                                <form:input path="role" cssClass="form-control" id="role" placeholder="Enter Role"/>
                            </div>

                            <div class="form-group">
                                <label for="createdAt">Created At</label>
                                <form:input path="createdAt" cssClass="form-control" id="createdAt" placeholder="Enter Created At" readonly="true"/>
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