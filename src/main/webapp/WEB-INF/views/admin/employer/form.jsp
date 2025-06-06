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
                        <div class="card-title">Employer Form</div>
                        <hr>                    
                        <form:form method="post" modelAttribute="employer" action="${pageContext.request.contextPath}/admin/employers/save">
                            <form:hidden path="employerID"/> 
                            <form:hidden path="userID"/>
                            <div class="form-group">
                                <label for="companyName">Company Name <span class="text-danger">*</span></label>
                                <form:input path="companyName" cssClass="form-control" id="companyName" placeholder="Enter Company Name" required="true"/>
                                <form:errors path="companyName" cssClass="text-danger"/>
                            </div>
                            <div class="form-group">
                                <label for="website">Website <span class="text-danger">*</span></label>
                                <form:input path="website" type="url" cssClass="form-control" id="website" placeholder="Enter Website (e.g., https://example.com)" required="true"/>
                                <form:errors path="website" cssClass="text-danger"/>
                            </div>                            
                            <div class="form-group">
                                <label for="description">Description</label>
                                <form:textarea path="description" cssClass="form-control" id="description" placeholder="Enter Description (optional)" rows="4"/>
                                <form:errors path="description" cssClass="text-danger"/>
                            </div>
                            <div class="form-group">
                                <label for="logoUrl">Logo URL</label>
                                <form:input path="logoUrl" type="url" cssClass="form-control" id="logoUrl" placeholder="Enter Logo URL (e.g., https://example.com/logo.png)"/>
                                <form:errors path="logoUrl" cssClass="text-danger"/>
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary">Lưu</button>
                                <a href="${pageContext.request.contextPath}/admin/employers?page=1&size=${pageSize}&keyword=${fn:escapeXml(keyword)}" class="btn btn-secondary">Hủy</a>
                            </div>
                        </form:form>
                    </div>
                </div>   
            </div>
        </div>
        <!-- End Row -->
        <!-- Start overlay -->
        <div class="overlay toggle-menu"></div>
        <!-- End overlay -->
    </div>
    <!-- End container-fluid -->
</div>