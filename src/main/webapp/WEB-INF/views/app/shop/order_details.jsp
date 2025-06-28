<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="container mt-5">
    <h2 class="mb-4 text-center">Chi tiết Đơn hàng #${fn:escapeXml(order.id)}</h2>

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

    <div class="row">
        <div class="col-lg-6 mb-4">
            <div class="card shadow-sm h-100">
                <div class="card-header bg-light">
                    <h5 class="mb-0">Thông tin Đơn hàng</h5>
                </div>
                <div class="card-body">
                    <p><strong>Mã Đơn hàng:</strong> #${fn:escapeXml(order.id)}</p>
                    <p><strong>Ngày Đặt:</strong> <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm"/> </p>
                    <p><strong>Tổng tiền:</strong> <fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="₫"  /> </p>
                    <p><strong>Trạng thái:</strong>
                        <c:choose>
                            <c:when test="${order.status == 'PENDING'}"><span class="badge bg-warning text-dark">Đang chờ</span></c:when>
                            <c:when test="${order.status == 'COMPLETED'}"><span class="badge bg-success">Hoàn thành</span></c:when>
                            <c:when test="${order.status == 'CANCELLED'}"><span class="badge bg-danger">Đã hủy</span></c:when>
                            <c:when test="${order.status == 'FAILED'}"><span class="badge bg-danger">Thất bại</span></c:when>
                            <c:otherwise><span class="badge bg-secondary">${fn:escapeXml(order.status)}</span></c:otherwise>
                        </c:choose>
                    </p>
                    <p><strong>Phương thức Thanh toán:</strong> ${fn:escapeXml(order.paymentMethod)}</p>
                    <p><strong>Trạng thái Thanh toán:</strong>
                        <c:choose>
                            <c:when test="${order.paymentStatus == 'PAID'}"><span class="badge bg-success">Đã thanh toán</span></c:when>
                            <c:when test="${order.paymentStatus == 'UNPAID'}"><span class="badge bg-danger">Chưa thanh toán</span></c:when>
                            <c:when test="${order.paymentStatus == 'REFUNDED'}"><span class="badge bg-info text-dark">Đã hoàn tiền</span></c:when>
                            <c:otherwise><span class="badge bg-secondary">${fn:escapeXml(order.paymentStatus)}</span></c:otherwise>
                        </c:choose>
                    </p>
                </div>
            </div>
        </div>

        <div class="col-lg-6 mb-4">
            <div class="card shadow-sm h-100">
                <div class="card-header bg-light">
                    <h5 class="mb-0">Thông tin Khách hàng</h5>
                </div>
                <div class="card-body">
                    <c:if test="${not empty order.user}">
                        <p><strong>Tên:</strong> ${fn:escapeXml(order.user.fullName)}</p>
                        <p><strong>Email:</strong> ${fn:escapeXml(order.user.email)}</p>
                        <p><strong>Điện thoại:</strong> ${fn:escapeXml(order.user.phone)}</p>
                        <%-- Bạn có thể thêm các thông tin user khác nếu cần --%>
                    </c:if>
                    <c:if test="${empty order.user}">
                        <p class="text-muted">Không tìm thấy thông tin khách hàng.</p>
                    </c:if>
                </div>
            </div>
        </div>
    </div>

    <div class="card shadow-sm mt-4">
        <div class="card-header bg-light">
            <h5 class="mb-0">Các gói dịch vụ đã mua</h5>
        </div>
        <div class="card-body">
            <c:choose>
                <c:when test="${empty order.orderItems}">
                    <p class="text-muted text-center">Không có gói dịch vụ nào trong đơn hàng này.</p>
                </c:when>
                <c:otherwise>
                    <div class="table-responsive">
                        <table class="table table-hover align-middle">
                            <thead class="thead-light">
                                <tr>
                                    <th scope="col">Gói Dịch Vụ</th>
                                    <th scope="col" class="text-center">Số lượng</th>
                                    <th scope="col" class="text-center">Giá mỗi gói</th>
                                    <th scope="col" class="text-end">Thành tiền</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${order.orderItems}">
                                    <tr>
                                        <td>
                                            <c:if test="${not empty item.servicePackage}">
                                                <strong>${fn:escapeXml(item.servicePackage.name)}</strong>
                                                <br>
                                                <small class="text-muted">${fn:escapeXml(item.servicePackage.description)}</small>
                                            </c:if>
                                            <c:if test="${empty item.servicePackage}">
                                                <em>Gói không tồn tại (ID: ${item.packageId})</em>
                                            </c:if>
                                        </td>
                                        <td class="text-center">${item.quantity}</td>
                                        <td class="text-center">
                                            <fmt:formatNumber value="${item.pricePerItem}" type="currency" currencySymbol="₫"  />
                                        </td>
                                        <td class="text-end">
                                            <fmt:formatNumber value="${item.subtotal}" type="currency" currencySymbol="₫"  />
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <div class="text-center mt-4">
        <a href="${pageContext.request.contextPath}/app/shop/orders" class="btn btn-secondary btn-lg">Quay lại Lịch sử Đơn hàng <i class="fas fa-undo-alt ms-2"></i></a>
    </div>
</div>