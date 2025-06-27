<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<main>
    <div class="container py-5">
        <h2 class="mb-4">Danh sách công ty đã lưu</h2>

        <c:choose>
            <c:when test="${not empty employers}">
                <div class="row">
                    <c:forEach var="employer" items="${employers}">
                        <div class="col-md-4 mb-4">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h5 class="card-title">
                                        <a href="<c:url value='/employers/detail/${employer.id}' />">${employer.companyName}</a>
                                    </h5>
                                    <p class="card-text">
                                        <i class="fa fa-building"></i> Quy mô: ${employer.companySize}<br />
                                        <i class="fa fa-map-marker-alt"></i> ${employer.address} <br />
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
