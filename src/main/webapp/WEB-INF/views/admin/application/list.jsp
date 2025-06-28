<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<div class="content-wrapper">
    <div class="container-fluid"><h2>Quản lý CV</h2>
        <div class="card mb-3">
            <div class="card-body">
                <div class="row mb-3">
                    <div class="col-md-6">
                        <form action="${pageContext.request.contextPath}/admin/applications" method="get">
                            <div class="input-group">
                                <input type="text" name="keyword" class="form-control" placeholder="Search by title or category" value="${fn:escapeXml(keyword)}"/>
                                <input type="hidden" name="size" value="${pageSize}"/>
                                <button type="submit" class="btn btn-primary">Search</button>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-3 text-end">
                        <a href="${pageContext.request.contextPath}/admin/applications/add?size=${pageSize}&keyword=${fn:escapeXml(keyword)}" class="btn btn-success">Thêm CV</a>
                    </div>
                    <div class="col-md-3 text-right">
                        <div >                    
                            <form action="${pageContext.request.contextPath}/admin/applications" method="get" class="form-inline d-inline">
                                <div class="form-group mr-2 mb-0 d-flex align-items-center">
                                    <label class="mr-2">Applications per page:</label>
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
                </div>

            </div>

            <div class="table-responsive">
                <table class="table table-bordered table-hover bg-info-light2">
                    <thead class="table-active">
                        <tr>
                            <th scope="col">#</th>                            
                            <th scope="col">Job Category</th>
                            <th scope="col">Status</th>
                            <th scope="col">Resume URL</th>
                            <th scope="col">Score</th>
                            <th scope="col">Applied At</th>                            
                            <th scope="col">Function</th>
                        </tr>
                    </thead>
                    <tbody> 
                       <c:choose>
                            <c:when test="${empty applications}">
                                <tr>
                                    <td colspan="12" class="text-center">No jobs found.</td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="c" items="${applications}" varStatus="status">
                                    <tr>
                                        <th scope="row">${(currentPage - 1) * pageSize + status.index + 1}</th>                                        
                                        <td>${fn:escapeXml(category[c.jobId])}</td>
                                        <td>${fn:escapeXml(c.status)}</td>
                                        <td>${fn:escapeXml(c.resumeUrl)}</td>
                                        <td>${fn:escapeXml(c.score)}</td>
                                        <td>${c.appliedAt}</td>                                
                                        <td>
                                            <a href="${pageContext.request.contextPath}/admin/applications/edit/${c.id}" class="btn btn-primary btn-sm">Sửa</a>
                                            <a href="${pageContext.request.contextPath}/admin/applications/delete/${c.id}" 
                                               class="btn btn-danger btn-sm"
                                               onclick="return confirm('Bạn có chắc muốn xóa ứng tuyển với trạng thái \'' + '${fn:replace(fn:escapeXml(c.status), '\'', '\\\'')}' + '\'?')">
                                                Xóa
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>                        
                    </tbody>
                </table>
            </div>
            <c:if test="${totalPages > 0}">
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <c:if test="${currentPage > 1}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/admin/applications?page=${currentPage - 1}&size=${pageSize}&keyword=${fn:escapeXml(keyword)}">Previous</a>
                            </li>
                        </c:if>
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <li class="page-item ${currentPage == i ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/admin/applications?page=${i}&size=${pageSize}&keyword=${fn:escapeXml(keyword)}">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/admin/applications?page=${currentPage + 1}&size=${pageSize}&keyword=${fn:escapeXml(keyword)}">Next</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </c:if>
        </div>
    </div>
</div>
