```jsp
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
                        <h5 class="card-title">${candidate.id == null ? 'Add Candidate' : 'Edit Candidate'}</h5>
                        <hr>
                        <c:if test="${not empty result && result.hasErrors()}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                Please fix the errors below:
                                <ul>
                                    <c:forEach items="${result.allErrors}" var="error">
                                        <li>${fn:escapeXml(error.defaultMessage)}</li>
                                    </c:forEach>
                                </ul>
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                ${fn:escapeXml(error)}
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>
                        <c:if test="${not empty success}">
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                                ${fn:escapeXml(success)}
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>
                        <form:form method="post" modelAttribute="candidate" action="${pageContext.request.contextPath}/admin/candidates/save">
                            <form:hidden path="id"/>
                            <input type="hidden" name="size" value="${pageSize}"/>
                            <input type="hidden" name="keyword" value="${fn:escapeXml(keyword)}"/>
                            <div class="mb-3">
                                <label for="userId" class="form-label">Candidate User <span class="text-danger">*</span></label>
                                <form:select path="userId" cssClass="form-control" id="userId" required="true">
                                    <form:option value="" label="-- Select User --"/>
                                    <c:forEach var="user" items="${users}">
                                        <form:option value="${user.id}" label="${user.fullName} (${user.email})"/>
                                    </c:forEach>
                                </form:select>
                                <form:errors path="userId" cssClass="text-danger small"/>
                            </div>
                            <div class="mb-3">
                                <label for="resumeUrl" class="form-label">Resume URL <span class="text-danger">*</span></label>
                                <form:input path="resumeUrl" type="url" cssClass="form-control" id="resumeUrl" placeholder="Enter Resume URL (e.g., https://example.com/resume.pdf)" required="true"/>
                                <form:errors path="resumeUrl" cssClass="text-danger small"/>
                            </div>
                            <div class="mb-3">
                                <label for="skills" class="form-label">Skills <span class="text-danger">*</span></label>
                                <form:input path="skills" cssClass="form-control" id="skills" placeholder="Enter Skills (e.g., Java, Python)" required="true"/>
                                <form:errors path="skills" cssClass="text-danger small"/>
                            </div>
                            <div class="mb-3">
                                <label for="location" class="form-label">Location <span class="text-danger">*</span></label>
                                <form:input path="location" cssClass="form-control" id="location" placeholder="Enter Location (e.g., Hanoi)" required="true"/>
                                <form:errors path="location" cssClass="text-danger small"/>
                            </div>
                            <div class="mb-3">
                                <label for="experienceLevel" class="form-label">Experience Level</label>
                                <form:input path="experienceLevel" cssClass="form-control" id="experienceLevel" placeholder="Enter Experience Level (e.g., Junior, Senior)"/>
                                <form:errors path="experienceLevel" cssClass="text-danger small"/>
                            </div>
                            <div class="mb-3">
                                <button type="submit" class="btn btn-primary">Save</button>
                                <a href="${pageContext.request.contextPath}/admin/candidates?page=1&size=${pageSize}&keyword=${fn:escapeXml(keyword)}" class="btn btn-secondary">Cancel</a>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
        <div class="overlay toggle-menu"></div>
    </div>
</div>
```