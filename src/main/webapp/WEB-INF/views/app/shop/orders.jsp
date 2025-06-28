<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="container mt-5">
    <h2 class="mb-4 text-center">Lịch sử Đơn hàng của bạn</h2>

    <%-- Hiển thị thông báo (nếu có) --%>
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${fn:escapeXml(successMessage)}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${fn:escapeXml(errorMessage)}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:if>

    <c:choose>
        <c:when test="${empty orders}">
            <div class="alert alert-info text-center" role="alert">
                Bạn chưa có đơn hàng nào.
            </div>
            <div class="text-center">
                <a href="${pageContext.request.contextPath}/app/service_packages" class="btn btn-primary btn-lg mt-3">Khám phá các gói dịch vụ</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="card shadow-sm">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-hover align-middle">
                            <thead class="thead-light">
                                <tr>
                                    <th scope="col">Mã Đơn hàng</th>
                                    <th scope="col">Ngày Đặt</th>
                                    <th scope="col" class="text-end">Tổng tiền</th>
                                    <th scope="col">Trạng thái</th>
                                    <th scope="col">Thanh toán</th>
                                    <th scope="col" class="text-center">Chi tiết</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="order" items="${orders}">
                                    <tr>
                                        <td><strong>#${fn:escapeXml(order.id)}</strong></td>
                                        <td><fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                                        <td class="text-end"><fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="₫"  /></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${order.status == 'PENDING'}"><span class="badge bg-warning text-dark">Đang chờ</span></c:when>
                                                <c:when test="${order.status == 'COMPLETED'}"><span class="badge bg-success">Hoàn thành</span></c:when>
                                                <c:when test="${order.status == 'CANCELLED'}"><span class="badge bg-danger">Đã hủy</span></c:when>
                                                <c:when test="${order.status == 'FAILED'}"><span class="badge bg-danger">Thất bại</span></c:when>
                                                <c:otherwise><span class="badge bg-secondary">${fn:escapeXml(order.status)}</span></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${order.paymentStatus == 'PAID'}"><span class="badge bg-success">Đã thanh toán</span></c:when>
                                                <c:when test="${order.paymentStatus == 'UNPAID'}"><span class="badge bg-danger">Chưa thanh toán</span></c:when>
                                                <c:when test="${order.paymentStatus == 'REFUNDED'}"><span class="badge bg-info text-dark">Đã hoàn tiền</span></c:when>
                                                <c:otherwise><span class="badge bg-secondary">${fn:escapeXml(order.paymentStatus)}</span></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="text-center">
                                            <a href="${pageContext.request.contextPath}/app/shop/orders/${order.id}" class="btn btn-info btn-sm">Xem chi tiết <i class="fas fa-eye ms-1"></i></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>