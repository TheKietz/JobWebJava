<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="container mt-5">
    <h2 class="mb-4 text-center">Theo dõi Đơn hàng của Nhà tuyển dụng</h2>

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

    <%-- Tabs lọc theo trạng thái --%>
    <ul class="nav nav-pills nav-fill mb-4">
        <li class="nav-item">
            <a class="nav-link ${statusFilter == 'all' ? 'active bg-success text-white' : 'text-dark'}" href="${pageContext.request.contextPath}/app/shop/employer/orders?status=all">
                Tất cả <span class="badge badge-light ms-1">0</span> <%-- Số lượng động sẽ được thêm sau --%>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${statusFilter == 'PENDING' ? 'active bg-info text-white' : 'text-dark'}" href="${pageContext.request.contextPath}/app/shop/employer/orders?status=PENDING">
                Đang chờ duyệt <span class="badge badge-light ms-1">0</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${statusFilter == 'COMPLETED' ? 'active bg-success text-white' : 'text-dark'}" href="${pageContext.request.contextPath}/app/shop/employer/orders?status=COMPLETED">
                Hoàn thành <span class="badge badge-light ms-1">0</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${statusFilter == 'EXPIRED' ? 'active bg-warning text-white' : 'text-dark'}" href="${pageContext.request.contextPath}/app/shop/employer/orders?status=EXPIRED">
                Hết hạn <span class="badge badge-light ms-1">0</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${statusFilter == 'CANCELLED' ? 'active bg-danger text-white' : 'text-dark'}" href="${pageContext.request.contextPath}/app/shop/employer/orders?status=CANCELLED">
                Bị hủy <span class="badge badge-light ms-1">0</span>
            </a>
        </li>
        <%-- Bạn có thể thêm các trạng thái khác như 'FAILED' nếu có --%>
    </ul>

    <c:choose>
        <c:when test="${empty orders}">
            <div class="alert alert-info text-center" role="alert">
                <img src="https://placehold.co/300x200/e0f2f7/2196f3?text=Không+có+đơn+hàng+nào" alt="Không có đơn hàng" class="img-fluid mb-3">
                <p>Bạn chưa có đơn hàng nào với trạng thái này.</p>
            </div>
            <div class="text-center">
                <a href="${pageContext.request.contextPath}/app/service_packages" class="btn btn-primary btn-lg mt-3">Mua dịch vụ</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="card shadow-sm">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-hover align-middle">
                            <thead class="thead-light">
                                <tr>
                                    <th scope="col">Mã Đơn</th>
                                    <th scope="col">Khách hàng</th>
                                    <th scope="col">Ngày Đặt</th>
                                    <th scope="col" class="text-end">Tổng tiền</th>
                                    <th scope="col">Trạng thái</th>
                                    <th scope="col">Thanh toán</th>
                                    <th scope="col" class="text-center">Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="order" items="${orders}">
                                    <tr>
                                        <td><strong>#${fn:escapeXml(order.id)}</strong></td>
                                        <td>
                                            <c:if test="${not empty order.user}">
                                                ${fn:escapeXml(order.user.fullName)} <br>
                                                <small class="text-muted">${fn:escapeXml(order.user.email)}</small>
                                            </c:if>
                                            <c:if test="${empty order.user}">
                                                <em>Người dùng không tồn tại</em>
                                            </c:if>
                                        </td>
                                        <td><fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                                        <td class="text-end"><fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="₫" maxFractionDigits="0" /></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${order.status == 'PENDING'}"><span class="badge bg-info text-dark badge-pill">Đang chờ duyệt</span></c:when>
                                                <c:when test="${order.status == 'COMPLETED'}"><span class="badge bg-success badge-pill">Hoàn thành</span></c:when>
                                                <c:when test="${order.status == 'CANCELLED'}"><span class="badge bg-danger badge-pill">Đã hủy</span></c:when>
                                                <c:when test="${order.status == 'EXPIRED'}"><span class="badge bg-warning text-dark badge-pill">Hết hạn</span></c:when>
                                                <c:otherwise><span class="badge bg-secondary text-dark badge-pill">${fn:escapeXml(order.status)}</span></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${order.paymentStatus == 'PAID'}"><span class="badge bg-success badge-pill">Đã thanh toán</span></c:when>
                                                <c:when test="${order.paymentStatus == 'UNPAID'}"><span class="badge bg-danger badge-pill">Chưa thanh toán</span></c:when>
                                                <c:when test="${order.paymentStatus == 'REFUNDED'}"><span class="badge bg-info text-dark badge-pill">Đã hoàn tiền</span></c:when>
                                                <c:otherwise><span class="badge bg-secondary text-dark badge-pill">${fn:escapeXml(order.paymentStatus)}</span></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="text-center">
                                            <a href="${pageContext.request.contextPath}/app/shop/orders/${order.id}" class="btn btn-info btn-sm">Xem chi tiết <i class="fas fa-eye ms-1"></i></a>
                                            <%-- Thêm các nút hành động khác nếu cần (ví dụ: Cập nhật trạng thái) --%>
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