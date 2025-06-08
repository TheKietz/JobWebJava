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
                        <div class="card-title">Job Form</div>
                        <hr>                    
                        <form:form method="post" modelAttribute="job" action="${pageContext.request.contextPath}/admin/jobs/save">
                            <form:hidden path="jobID"/> 
                            <div class="form-group">
                                <label for="employerID">Employer ID <span class="text-danger">*</span></label>
                                <form:input path="employerID" cssClass="form-control" id="employerID" placeholder="Enter Employer ID" required="true"/>
                                <form:errors path="employerID" cssClass="text-danger"/>
                            </div>
                            <div class="form-group">
                                <label for="title">Title <span class="text-danger">*</span></label>
                                <form:input path="title" cssClass="form-control" id="title" placeholder="Enter Title" required="true"/>
                                <form:errors path="title" cssClass="text-danger"/>
                            </div>
                            <div class="form-group">
                                <label for="description">Description <span class="text-danger">*</span></label>
                                <form:textarea path="description" cssClass="form-control" id="description" placeholder="Enter Description" rows="4" required="true"/>
                                <form:errors path="description" cssClass="text-danger"/>
                            </div>                            
                            <div class="form-group">
                                <label for="location">Location</label>
                                <form:input path="location" cssClass="form-control" id="location" placeholder="Enter Location"/>
                                <form:errors path="location" cssClass="text-danger"/>
                            </div>
                            <div class="form-group">
                                <label for="jobType">Job Type</label>
                                <form:input path="jobType" cssClass="form-control" id="jobType" placeholder="Enter Job Type (e.g., Full-time)"/>
                                <form:errors path="jobType" cssClass="text-danger"/>
                            </div>
                            <div class="form-group">
                                <label for="salaryRange">Salary Range</label>
                                <form:input path="salaryRange" cssClass="form-control" id="salaryRange" placeholder="Enter Salary Range (e.g., 1000-2000 USD)"/>
                                <form:errors path="salaryRange" cssClass="text-danger"/>
                            </div>
                            <div class="form-group">
                                <label for="postedAt">Posted At <span class="text-danger">*</span></label>
                                <form:input path="postedAt" type="date" cssClass="form-control" id="postedAt" required="true"/>
                                <form:errors path="postedAt" cssClass="text-danger"/>
                            </div>
                            <div class="form-group">
                                <label for="expiryDate">Expiry Date <span class="text-danger">*</span></label>
                                <form:input path="expiryDate" type="date" cssClass="form-control" id="expiryDate" required="true"/>
                                <form:errors path="expiryDate" cssClass="text-danger"/>
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary">Lưu</button>
                                <a href="${pageContext.request.contextPath}/admin/jobs?page=1&size=${pageSize}&keyword=${fn:escapeXml(keyword)}" class="btn btn-secondary">Hủy</a>
                            </div>
                        </form:form>
                    </div>
                </div>   
            </div>
        </div>
        <div class="overlay toggle-menu"></div>
    </div>
</div>