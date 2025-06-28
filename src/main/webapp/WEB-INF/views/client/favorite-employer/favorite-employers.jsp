<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<main>
    <div class="container py-5">
        <h2 class="mb-4">Danh sách công ty đã lưu</h2>

        <c:choose>
            <c:when test="${not empty employers}">
                <div class="row">
                    <c:forEach var="employer" items="${employers}">
                        <div class="col-md-6 mb-6">
                            <div class="card h-100">
                                <c:if test="${not empty employer.logoUrl}">
                                    <img src="${pageContext.request.contextPath}/uploads/${employer.logoUrl}" alt="Logo ${employer.companyName}"
                                         class="card-img-top p-3" style="height: 120px; object-fit: contain;">
                                </c:if>
                                    
                                <div class="card-body">
                                    <h5 class="card-title">
                                        <a href="<c:url value='/employers/detail/${employer.id}' />">${employer.companyName}</a>
                                    </h5>
                                    <p class="card-text">
                                        <i class="fa fa-building"></i> Quy mô: ${employer.companySize} nhân viên<br />
                                        <i class="fa fa-map-marker-alt"></i>Địa chỉ: ${employer.address} <br />
                                        <i class="fa fa-globe"></i> 
                                        <a href="${employer.website}" target="_blank">${employer.website}</a>
                                    </p>
                                    <form method="post" action="<c:url value='/favorite-employers/remove/${employer.id}'/>?redirect=/favorite-employers">
                                        <button class="btn btn-danger btn-sm" type="submit">
                                            Bỏ lưu
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>

                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-info">
                    Bạn chưa lưu công ty nào.
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</main>
