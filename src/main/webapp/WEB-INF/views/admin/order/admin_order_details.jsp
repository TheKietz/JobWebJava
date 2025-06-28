<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- 
    Đây là trang chi tiết đơn hàng dành riêng cho Admin.
    Nó kế thừa layout admin/layout/main.jsp và tuân thủ cấu trúc của bạn.
--%>
<div class="content-wrapper">
    <div class="container-fluid">
        <h2>Chi tiết Đơn hàng (Admin) #${fn:escapeXml(order.id)}</h2>
        <hr>

        <%-- Hiển thị thông báo (success, error, message) theo mẫu của bạn --%>
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
        <c:if test="${not empty message}">
            <div class="alert alert-info alert-dismissible fade show" role="alert">
                ${fn:escapeXml(message)}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        
        <%-- LƯU Ý: nếu các thông báo vẫn dùng successMessage/errorMessage/message thay vì success/error/message --%>
        <%-- bạn cần điều chỉnh trong Controller để truyền đúng tên thuộc tính, HOẶC sửa các dòng trên thành: --%>
        <%-- <c:if test="${not empty successMessage}"> ... ${fn:escapeXml(successMessage)} ... </c:if> --%>
        <%-- <c:if test="${not empty errorMessage}"> ... ${fn:escapeXml(errorMessage)} ... </c:if> --%>


        <div class="card mb-3">
            <div class="card-body">
                <div class="row">
                    <div class="col-lg-6 mb-4">
                        <div class="card shadow-sm h-100">
                            <div class="card-header bg-light">
                                <h5 class="mb-0">Thông tin Đơn hàng</h5>
                            </div>
                            <div class="card-body">
                                <p><strong>Mã Đơn hàng:</strong> #${fn:escapeXml(order.id)}</p>
                                <p><strong>Ngày Đặt:</strong> <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm"/> </p>
                                <p><strong>Tổng tiền:</strong> <fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="₫" maxFractionDigits="0" /> </p>
                                <p><strong>Trạng thái:</strong>
                                    <c:choose>
                                        <c:when test="${order.status == 'PENDING'}"><span class="badge bg-info text-dark badge-pill">Đang chờ</span></c:when>
                                        <c:when test="${order.status == 'COMPLETED'}"><span class="badge bg-success badge-pill">Hoàn thành</span></c:when>
                                        <c:when test="${order.status == 'CANCELLED'}"><span class="badge bg-danger badge-pill">Đã hủy</span></c:when>
                                        <c:when test="${order.status == 'EXPIRED'}"><span class="badge bg-warning text-dark badge-pill">Hết hạn</span></c:when>
                                        <c:when test="${order.status == 'FAILED'}"><span class="badge bg-dark badge-pill">Thất bại</span></c:when>
                                        <c:otherwise><span class="badge bg-secondary text-dark badge-pill">${fn:escapeXml(order.status)}</span></c:otherwise>
                                    </c:choose>
                                </p>
                                <p><strong>Phương thức Thanh toán:</strong> ${fn:escapeXml(order.paymentMethod)}</p>
                                <p><strong>Trạng thái Thanh toán:</strong>
                                    <c:choose>
                                        <c:when test="${order.paymentStatus == 'PAID'}"><span class="badge bg-success badge-pill">Đã thanh toán</span></c:when>
                                        <c:when test="${order.paymentStatus == 'UNPAID'}"><span class="badge bg-danger badge-pill">Chưa thanh toán</span></c:when>
                                        <c:when test="${order.paymentStatus == 'REFUNDED'}"><span class="badge bg-info text-dark badge-pill">Đã hoàn tiền</span></c:when>
                                        <c:otherwise><span class="badge bg-secondary text-dark badge-pill">${fn:escapeXml(order.paymentStatus)}</span></c:otherwise>
                                    </c:choose>
                                </p>
                                <%-- Form cập nhật trạng thái đơn hàng --%>
                                <form action="${pageContext.request.contextPath}/admin/orders/update-status" method="post" class="mt-3">
                                    <input type="hidden" name="orderId" value="${order.id}">
                                    <%-- Thêm hidden input cho currentStatusFilter nếu muốn sau khi update redirect về đúng tab --%>
                                    <%-- <input type="hidden" name="currentStatusFilter" value="${order.status.name()}"> --%>
                                    <div class="mb-3">
                                        <label for="adminUpdateStatus" class="form-label">Cập nhật trạng thái đơn hàng:</label>
                                        <select name="newStatus" id="adminUpdateStatus" class="form-select" onchange="this.form.submit()">
                                            <c:forEach var="statusOption" items="${allStatuses}">
                                                <option value="${statusOption.name()}" ${order.status == statusOption ? 'selected' : ''}>
                                                    <c:choose>
                                                        <c:when test="${statusOption == 'PENDING'}">Đang chờ</c:when>
                                                        <c:when test="${statusOption == 'COMPLETED'}">Hoàn thành</c:when>
                                                        <c:when test="${statusOption == 'CANCELLED'}">Đã hủy</c:when>
                                                        <c:when test="${statusOption == 'EXPIRED'}">Hết hạn</c:when>
                                                        <c:when test="${statusOption == 'FAILED'}">Thất bại</c:when>
                                                        <c:otherwise>${fn:escapeXml(statusOption.name())}</c:otherwise>
                                                    </c:choose>
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </form>
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
                                                        <fmt:formatNumber value="${item.pricePerItem}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
                                                    </td>
                                                    <td class="text-end">
                                                        <fmt:formatNumber value="${item.subtotal}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
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
                    <a href="${pageContext.request.contextPath}/admin/orders" class="btn btn-secondary btn-lg">Quay lại Quản lý Đơn hàng <i class="fas fa-undo-alt ms-2"></i></a>
                </div>
            </div>
        </div>
    </div>
</div>
