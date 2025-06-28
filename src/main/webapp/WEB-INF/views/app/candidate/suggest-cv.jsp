<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Tin tuyển dụng</title>

</head>

<div class="row mb-3">
    <div class="col-md-6">
        <form action="${pageContext.request.contextPath}/app/suggest-cv" method="get">
            <div class="input-group">                        
                <input type="text" name="keyword" class="form-control" placeholder="Nhập tiêu đề công việc" value="${fn:escapeXml(keyword)}" />
                <input type="hidden" name="size" value="${pageSize}" />
                <button type="submit" class="btn btn-primary"><i class="fa fa-search"></i></button>
            </div>
        </form>
    </div>
    <div class="col-md-1 text-end">

    </div>
    <div class="col-md-5 text-left">
        <div >                    
            <form action="${pageContext.request.contextPath}/app/suggest-cv" method="get" class="form-inline d-inline">
                <div class="form-group mr-2 mb-0 d-flex align-items-center">
                    <label class="mr-2">Số lượng mỗi trang:</label>
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
<div class="content-wrapper"> 
    <div class="container-fluid">
        <c:if test="${not empty success}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${fn:escapeXml(success)}                        
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${fn:escapeXml(error)}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>

            </div>
        </c:if>                        
        <div class="row mb-3">

        </div>

    </div>
</div>
<div class="card">
    <div class="card-body">
        <h5 class="card-title">Danh sách ứng viên đề xuất</h5>
        <div class="table-responsive">
            <table class=" table table-hover">
                <thead class="table-light table-bordered thead-success">
                    <tr>
                        <th class="col-1">#</th>  
                        <th class="col-1">Ảnh đại diện</th>
                        <th class="col-2">Họ&Tên</th>  
                        <th class="col-2">Ngành</th> 
                        <th class="col-1">Hồ sơ</th>
                        <th class="col-1">Trình độ</th>  
                        <th class="col-2">Kỹ năng</th>  
                        <th class="col-1">Địa chỉ</th>  
                        <th class="col-4">Chức năng</th>
                    </tr>
                </thead>
                <tbody class="table table-bordered bg-white"> 
                    <c:choose>
                        <c:when test="${empty candidates}">
                            <tr>
                                <td colspan="12" class="text-center">Không tìm thấy ứng viên phù hợp</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="candidate" items="${candidates}" varStatus="loop">
                                <tr>
                                    <td>${loop.count}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${empty application.avatarUrl}">                                                   
                                                <img src="${pageContext.request.contextPath}/template/app/assets/img/default_avatar.png" style="width:100px; height:100px" alt="Ảnh đại diện"/>
                                            </c:when>
                                            <c:otherwise> 
                                                <img src="${pageContext.request.contextPath}${application.avatarUrl}" style="width:100px; height:100px" alt="Ảnh đại diện"/>
                                            </c:otherwise>    
                                        </c:choose>
                                    </td> 
                                    <td>${candidate.fullName}</td>
                                    <td>${candidate.category}</td>
                                    <td><a href="${pageContext.request.contextPath}${candidate.resumeUrl}" target="_blank" style="text-decoration: none;">Xem</a></td> 
                                    <td>${candidate.experienceLevel}</td> 
                                    <td>${candidate.skills}</td> 
                                    <td>${candidate.location}</td> 
                                    <td>                                                
                                        <a href="${pageContext.request.contextPath}/app/suggest-cv/add/${candidate.id}" class="btn btn-sm btn-success" onclick="return confirm('Bạn muốn mmời phỏng vấn ${candidate.fullName}?')">
                                            <i class="fa-solid fa-check"></i>
                                        </a>
                                    </td>                                   

                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</div>
<c:if test="${totalPages > 1}">
    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center">
            <c:forEach begin="1" end="${totalPages}" var="page">
                <li class="page-item ${page == currentPage ? 'active' : ''}">
                    <a class="page-link" href="${pageContext.request.contextPath}/app/suggest-cv?page=${page}&size=${pageSize}&keyword=${fn:escapeXml(keyword)}">${page}</a>
                </li>
            </c:forEach>
        </ul>
    </nav>
</c:if>
