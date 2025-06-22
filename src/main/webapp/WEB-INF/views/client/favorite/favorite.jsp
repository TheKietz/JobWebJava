<%@page contentType="text/html" pageEncoding="UTF-8"%>
<main>
    <div class="container py-5">
        <h2 class="mb-4">Danh sách việc làm đã lưu</h2>

        <c:choose>
            <c:when test="${not empty jobs}">
                <div class="row">
                    <c:forEach var="job" items="${jobs}">
                        <div class="col-md-4 mb-4">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h5 class="card-title">
                                        <a href="<c:url value='/jobs/detail/${job.id}' />">${job.title}</a>
                                    </h5>
                                    <p class="card-text">
                                        <i class="fa fa-map-marker-alt"></i> ${job.location} <br />
                                        Hình thức: ${job.jobType} <br />
                                        <fmt:formatNumber value="${job.salaryMin}" type="currency" currencySymbol="₫"/> -
                                        <fmt:formatNumber value="${job.salaryMax}" type="currency" currencySymbol="₫"/>
                                    </p>
                                    <form method="post" action="<c:url value='/favorites/remove/${job.id}'/>?redirect=/favorites">
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
                    Bạn chưa lưu công việc nào.
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</main>
