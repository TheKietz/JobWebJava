<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="content-wrapper">
    <div class="container-fluid">
        <h2>Lịch sử giao dịch</h2>
        <hr>
        <form method="get" action="${pageContext.request.contextPath}/admin/transactions">
            <div class="row g-2 mb-3">
                <div class="col-md-3">
                    <label>Từ ngày:</label>
                    <input type="date" name="from" class="form-control" value="${param.from}">
                </div>
                <div class="col-md-3">
                    <label>Đến ngày:</label>
                    <input type="date" name="to" class="form-control" value="${param.to}">
                </div>
                <div class="col-md-2">                    
                    <button type="submit" class="btn btn-primary">Lọc</button>
                </div>
            </div>
        </form>
        <c:if test="${not empty totalRevenue}">
            <h5 class="mt-3">
                Tổng doanh thu: 
                <strong><fmt:formatNumber value="${totalRevenue}" /></strong> VNĐ
            </h5>
        </c:if>

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
        <div class="card mb-3">
            <div class="card-body">
                <div class="row mb-3">
                    <div class="col-md-6">
                        <form action="${pageContext.request.contextPath}/admin/transactions" method="get">
                            <div class="input-group">
                                <input type="text" name="keyword" class="form-control" placeholder="Search by user ID, package ID, method" value="${fn:escapeXml(keyword)}"/>
                                <button type="submit" class="btn btn-primary">Search</button>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-3 text-end">
                        <a href="${pageContext.request.contextPath}/admin/transactions/add" class="btn btn-success">Add Transaction</a>
                    </div>
                    <div class="col-md-3 text-right">
                        <div >                    
                            <form action="${pageContext.request.contextPath}/admin/transactions" method="get" class="form-inline d-inline">
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
                    <table class="table table-bordered bg-info-light2">
                        <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>User</th>
                                <th>Package</th>
                                <th>Amount</th>
                                <th>Payment Method</th>
                                <th>Status</th>
                                <th>Created At</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty transactions}">
                                    <tr>
                                        <td colspan="8" class="text-center">No transactions found.</td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="t" items="${transactions}">
                                        <tr>
                                            <td>${t.id}</td>
                                            <td>${fn:escapeXml(t.userName)}</td>
                                            <td>${fn:escapeXml(t.packageName)}</td>
                                            <td><fmt:formatNumber value="${t.amount}"/> VNĐ</td>
                                            <td>${fn:escapeXml(t.paymentMethod)}</td>
                                            <td>${t.status}</td>
                                            <td>${fn:escapeXml(t.createdAtStr)}</td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/admin/transactions/edit/${t.id}" class="btn btn-sm btn-primary">Edit</a>
                                                <a href="${pageContext.request.contextPath}/admin/transactions/delete/${t.id}" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this transaction?')">Delete</a>
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
    </div>
</div>
