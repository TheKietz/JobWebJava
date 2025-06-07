<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="content-wrapper">
    <div class="container-fluid">            
        <div class="row justify-content-center"> 
            <div class="col-md-6"> 
                <div class="card">
                    <div class="card-body">
                        <div class="card-title">Employer Form</div>
                        <hr>                    
                        <form:form method="post" modelAttribute="candidate" action="${pageContext.request.contextPath}/admin/candidates/save">
                            <form:hidden path="candidateID"/> 
                            <form:hidden path="userID"/>
                            <div class="form-group">
                                <label for="resumeUrl">Company Name <span class="text-danger">*</span></label>
                                <form:input path="resumeUrl" cssClass="form-control" id="resumeUrl" placeholder="Enter Resume Url (e.g., https://example.com)" required="true"/>
                                <form:errors path="resumeUrl" cssClass="text-danger"/>
                            </div>
                            <div class="form-group">
                                <label for="bio">bio <span class="text-danger">*</span></label>
                                <form:textarea path="bio" type="url" cssClass="form-control" id="bio" placeholder="Enter Bio" required="true"/>
                                <form:errors path="bio" cssClass="text-danger"/>
                            </div>                            
                            <div class="form-group">
                                <label for="skills">Description</label>
                                <form:textarea path="skills" cssClass="form-control" id="skills" placeholder="Enter Skills (optional)" rows="4"/>
                                <form:errors path="skills" cssClass="text-danger"/>
                            </div>
                            
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary">Lưu</button>
                                <a href="${pageContext.request.contextPath}/admin/candidates?page=1&size=${pageSize}&keyword=${fn:escapeXml(keyword)}" class="btn btn-secondary">Hủy</a>
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