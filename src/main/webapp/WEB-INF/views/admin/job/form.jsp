<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="content-wrapper">
    <div class="container-fluid">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">
                            <c:choose>
                                <c:when test="${job.id == null}">
                                    Add Job
                                </c:when>
                                <c:otherwise>
                                    Edit Job
                                </c:otherwise>
                            </c:choose>
                        </h5>

                        <hr>
                        <c:if test="${not empty result && result.hasErrors()}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                Please fix the errors below:
                                <ul>
                                    <c:forEach items="${result.allErrors}" var="error">
                                        <li>${fn:escapeXml(error.defaultMessage)}</li>
                                        </c:forEach>
                                </ul>
                            </div>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            ${fn.escapeXml(error)}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    <c:if test="${not empty success}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            ${fn.escape(success)}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    <form:form method="post" modelAttribute="job" action="${pageContext.request.contextPath}/admin/jobs/save">
                        <form:hidden path="id"/>
                        <input type="hidden" name="size" value="${pageSize}"/>
                        <input type="hidden" name="keyword" value="${fn:escapeXml(keyword)}"/>
                        <div class="mb-3">
                            <label for="employerId" class="form-label">Employer <span class="text-danger">*</span></label>
                            <form:select path="employerId" cssClass="form-control" id="employerId" required="true">
                                <form:option value="" label="-- Select Employer --"/>
                                <c:forEach var="employer" items="${employers}">
                                    <form:option value="${employer.id}" label="${employer.companyName} (${employer.id})"/>
                                </c:forEach>
                            </form:select>
                            <form:errors path="employerId" cssClass="text-danger small"/>
                        </div>
                        <div class="mb-3">
                            <label for="title" class="form-label">Title <span class="text-danger">*</span></label>
                            <form:input path="title" cssClass="form-control" id="title" placeholder="Enter Job Title" required="true"/>
                            <form:errors path="title" cssClass="text-danger small"/>
                        </div>
                        <div class="mb-3">
                            <label for="description" class="form-label">Description <span class="text-danger">*</span></label>
                            <form:textarea path="description" cssClass="form-control" id="description" placeholder="Enter Job Description" rows="5" required="true"/>
                            <form:errors path="description" cssClass="text-danger small"/>
                        </div>
                        <div class="mb-3">
                            <label for="location" class="form-label">Location <span class="text-danger">*</span></label>
                            <form:input path="location" cssClass="form-control" id="location" placeholder="Enter Location (e.g., Hanoi)" required="true"/>
                            <form:errors path="location" cssClass="text-danger small"/>
                        </div>
                        <div class="mb-3">
                            <label for="salaryMin" class="form-label">Salary Min ($)</label>
                            <form:input path="salaryMin" type="number" step="0.1" cssClass="form-control" id="salaryMin" placeholder="Enter Minimum Salary"/>
                            <form:errors path="salaryMin" cssClass="text-danger small"/>
                        </div>
                        <div class="mb-3">
                            <label for="salaryMax" class="form-label">Salary Max ($)</label>
                            <form:input path="salaryMax" type="number" step="0.1" cssClass="form-control" id="salaryMax" placeholder="Enter Maximum Salary"/>
                            <form:errors path="salaryMax" cssClass="text-danger small"/>
                        </div>
                        <div class="mb-3">
                            <label for="jobType" class="form-label">Job Type</label>
                            <form:input path="jobType" cssClass="form-control" id="jobType" placeholder="Enter Job Type (e.g., Full-time, Remote)"/>
                            <form:errors path="jobType" cssClass="text-danger small"/>
                        </div>
                        <div class="mb-3">
                            <label for="status" class="form-label">Status</label>
                            <form:select path="status" cssClass="form-control" id="status">
                                <form:option value="OPEN" label="Open"/>
                                <form:option value="CLOSED" label="Closed"/>
                                <form:option value="DRAFT" label="Draft"/>
                            </form:select>
                            <form:errors path="status" cssClass="text-danger small"/>
                        </div>
                        <div class="mb-3">
                            <label for="category" class="form-label">Category <span class="text-danger">*</span></label>
                            <form:input path="category" cssClass="form-control" id="category" placeholder="Enter Category (e.g., Software Development)" required="true"/>
                            <form:errors path="category" cssClass="text-danger small"/>
                        </div>
                        <div class="mb-3">
                            <label for="expiredAt" class="form-label">Expiry Date</label>
                            <form:input path="expiredAt" type="date" cssClass="form-control" id="expiredAt" />
                            <form:errors path="expiredAt" cssClass="text-danger small"/>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary">Save</button>
                            <a href="${pageContext.request.contextPath}/admin/jobs?page=1&size=${pageSize}&keyword=${fn:escapeXml(keyword)}" class="btn btn-secondary">Cancel</a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
    <div class="overlay toggle-menu"></div>
</div>
