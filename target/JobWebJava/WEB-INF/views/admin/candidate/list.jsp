<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="content-wrapper">
    <div class="container-fluid">
        <h2>Candidates Management</h2>
        <hr>
        <c:if test="${not empty success}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${fn:escapeXml(success)}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${fn:escapeXml(error)}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <c:if test="${not empty message}">
            <div class="alert alert-info alert-dismissible fade show" role="alert">
                ${fn:escapeXml(message)}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <div class="card mb-3">
            <div class="card-body">
                <div class="row mb-3">
                    <div class="col-md-6">
                        <form action="${pageContext.request.contextPath}/admin/candidates" method="get">
                            <div class="input-group">
                                <input type="text" name="keyword" class="form-control" placeholder="Search by skills or location" value="${fn:escapeXml(keyword)}"/>
                                <input type="hidden" name="size" value="${pageSize}"/>
                                <button type="submit" class="btn btn-primary">Search</button>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-6 text-end">
                        <a href="${pageContext.request.contextPath}/admin/candidates/add?size=${pageSize}&keyword=${fn:escapeXml(keyword)}" class="btn btn-success">Add Candidate</a>
                    </div>
                </div>
                <div class="table-responsive" style="overflow-x: auto;">
                    <table class="table table-bordered" style="min-width: 1200px;" >

                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>User ID</th>
                                <th>Resume URL</th>
                                <th>Skills</th>
                                <th>Location</th>
                                <th>Experience Level</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="candidate" items="${candidates}">
                                <tr>
                                    <td>${candidate.id}</td>
                                    <td>${candidate.userId}</td>
                                    <td>${fn:escapeXml(candidate.resumeUrl)}</td>
                                    <td>${fn:escapeXml(candidate.skills)}</td>
                                    <td>${fn:escapeXml(candidate.location)}</td>
                                    <td>${candidate.experienceLevel}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/admin/candidates/edit/${candidate.id}?size=${pageSize}&keyword=${fn:escapeXml(keyword)}" class="btn btn-sm btn-primary">Edit</a>
                                        <a href="${pageContext.request.contextPath}/admin/candidates/delete/${candidate.id}?size=${pageSize}&keyword=${fn:escapeXml(keyword)}" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this candidate?')">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <c:if test="${totalPages > 1}">
                    <nav aria-label="Page navigation">
                        <ul class="pagination">
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="${pageContext.request.contextPath}/admin/candidates?page=${i}&size=${pageSize}&keyword=${fn:escapeXml(keyword)}">${i}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </c:if>
            </div>
        </div>
    </div>
</div>
```