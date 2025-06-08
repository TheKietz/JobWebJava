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
                        <div class="card-title">Application Form</div>
                        <hr>                    
                        <form:form method="post" modelAttribute="application" action="${pageContext.request.contextPath}/admin/applications/save">
                            <form:hidden path="applicationID"/> 
                            <form:hidden path="candidateID"/>
                            <form:hidden path="jobID"/>
                            <div class="form-group">
                                <label for="coverLetter">Cover Letter <span class="text-danger">*</span></label>
                                <form:textarea path="coverLetter" cssClass="form-control" id="coverLetter" placeholder="Enter Cover Letter" required="true"/>
                                <form:errors path="coverLetter" cssClass="text-danger"/>
                            </div>
                            <div class="form-group">
                                <label for="resumeUrl">Resume Url <span class="text-danger">*</span></label>
                                <form:input path="resumeUrl" type="url" cssClass="form-control" id="resumeUrl" placeholder="Enter Resume Url (e.g., https://example.com)" required="true"/>
                                <form:errors path="resumeUrl" cssClass="text-danger"/>
                            </div>                            
                            <div class="form-group">
                                <label for="appliedAt">Applied At</label>
                                <form:input path="appliedAt" cssClass="form-control" id="appliedAt" placeholder="Enter Applied At" rows="4"/>
                                <form:errors path="appliedAt" cssClass="text-danger"/>
                            </div>
                            <div class="form-group">
                                <label for="status">Status</label>
                                <form:input path="status" type="url" cssClass="form-control" id="status" placeholder="Enter Status (e.g., https://example.com/logo.png)"/>
                                <form:errors path="status" cssClass="text-danger"/>
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary">Lưu</button>
                                <a href="${pageContext.request.contextPath}/admin/applications?page=1&size=${pageSize}&keyword=${fn:escapeXml(keyword)}" class="btn btn-secondary">Hủy</a>
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