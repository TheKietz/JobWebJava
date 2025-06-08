<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="content-wrapper">
    <div class="container-fluid">
        <div class="card-body">
            <!-- Debug Info -->
            <div class="row mb-3">

                <div class="col-sm-3">
                    <h5 class="card-title">Employer List</h5>
                </div>                
                <div class="col-md-4">

                </div>
                <div class="col-md-5 text-right">                    
                    <form action="${pageContext.request.contextPath}/admin/employers" method="get" class="form-inline d-inline">
                        <div class="form-group mr-2 mb-0 d-flex align-items-center">
                            <label class="mr-2">Employers per page:</label>
                            <select name="size" class="form-control form-control-sm" onchange="this.form.submit()">
                                <option value="5" ${pageSize == 5 ? 'selected' : ''}>5</option>
                                <option value="10" ${pageSize == 10 ? 'selected' : ''}>10</option>
                                <option value="20" ${pageSize == 20 ? 'selected' : ''}>20</option>
                            </select>
                        </div>
                        <input type="hidden" name="page" value="1">
                        <input type="hidden" name="keyword" value="${fn:escapeXml(keyword)}">
                    </form>
                </div>
            </div>
            <div class="table-responsive">
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Company Name</th>
                            <th scope="col">Website</th>
                            <th scope="col">Description</th>
                            <th scope="col">Logo</th>
                            <th scope="col">Function</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="c" items="${employers}" varStatus="status">
                            <tr>
                                <th scope="row">${(currentPage - 1) * pageSize + status.index + 1}</th>
                                <td>${(c.companyName)}</td>
                                <td> ${(c.website)}</td>
                                <td>${c.description}</td>
                                <td>                                                                       
                                    <img src="${c.logoUrl}" alt="logo" style="max-width: 50px; max-height: 50px;">
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/employers/edit/${c.employerID}" class="btn btn-primary btn-sm">Sửa</a>
                                    <a href="${pageContext.request.contextPath}/admin/employers/delete/${c.employerID}" 
                                       class="btn btn-danger btn-sm"
                                       onclick="return confirm('Detele details of user name: \'', '\'', '\\\'')}' + '\'?')">
                                        Xóa
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty candidates}">
                            <tr>
                                <td colspan="8" class="text-center">No Employers Found.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
            <c:if test="${totalPages > 0}">
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <c:if test="${currentPage > 1}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/admin/employers?page=${currentPage - 1}&size=${pageSize}&keyword=${fn:escapeXml(keyword)}">Previous</a>
                            </li>
                        </c:if>
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <li class="page-item ${currentPage == i ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/admin/employers?page=${i}&size=${pageSize}&keyword=${fn:escapeXml(keyword)}">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/admin/employers?page=${currentPage + 1}&size=${pageSize}&keyword=${fn:escapeXml(keyword)}">Next</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </c:if>
        </div>
    </div>
</div>