<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Danh sách ứng viên</title>
    </head>
    <body>
        <div class="row mb-3">
            <div class="col-md-6">
                <form action="${pageContext.request.contextPath}/app/applications" method="get">
                    <div class="input-group">                        
                        <input type="text" name="keyword" class="form-control" placeholder="Nhập tiêu đề công việc" value="${fn:escapeXml(keyword)}" />
                        <input type="hidden" name="size" value="${pageSize}" />
                        <button type="submit" class="btn btn-primary"><i class="fa fa-search"></i></button>
                    </div>
                </form>
            </div>
            <div class="col-md-3 text-end">
                <a href="${pageContext.request.contextPath}/app/applications/add?size=${pageSize}&keyword=${fn:escapeXml(keyword)}" class="btn btn-success">Đăng tin</a>
            </div>
            <div class="col-md-3 text-right">
                <div >                    
                    <form action="${pageContext.request.contextPath}/app/applications" method="get" class="form-inline d-inline">
                        <div class="form-group mr-2 mb-0 d-flex align-items-center">
                            <label class="mr-2">Jobs per page:</label>
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
        <div class="table-responsive">
            <table class="table table-bordered bg-contrast">
                <thead class="table-light thead-success">
                    <tr>
                        <th class="col-1">Ảnh đại diện</th>
                        <th class="col-2">Họ & tên</th>  
                        <th class="col-6">Tiêu đề</th>
                        <th class="col-1">Link CV</th>   
                        <th class="col-2">Chức năng</th>
                    </tr>
                </thead>
                <tbody class="table table-bordered bg-white"> 
                    <c:choose>
                        <c:when test="${empty applications}">
                            <tr>
                                <td colspan="12" class="text-center">No applications found.</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="application" items="${applications}">
                                <tr>
                                    <td>
                                        <img src="${pageContext.request.contextPath}${application.avatarUrl}" style="width:40 height:40"/>
                                    </td> 
                                    <td>
                                        ${application.fullName}
                                    </td>
                                    <td>
                                        ${application.jobTitle}
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}${application.resumeUrl}" target="_blank" style="text-decoration: none;">Xem</a>

                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/app/applications/details/${job.id}" class="btn btn-sm btn-primary">Chi tiết</a>
                                        <a href="${pageContext.request.contextPath}/app/applications/delete/${job.id}" class="btn btn-sm btn-danger" onclick="return confirm('Bạn muốn xóa bài đăng này?')">Xóa</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
        <c:if test="${totalPages > 1}">
            <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                    <c:forEach begin="1" end="${totalPages}" var="page">
                        <li class="page-item ${page == currentPage ? 'active' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/app/applications?page=${page}&size=${pageSize}&keyword=${fn:escapeXml(keyword)}">${page}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </c:if>
    </body>
</html>
