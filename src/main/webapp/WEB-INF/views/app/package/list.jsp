<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style>
    /* ... (Các CSS hiện có của bạn, bao gồm các style cho .btn-light-green) ... */
    .card.h-100 {
        /* Đảm bảo các card có cùng chiều cao */
        display: flex;
        flex-direction: column;
        justify-content: space-between;

        /* THÊM HOẶC CHỈNH SỬA ĐOẠN NÀY ĐỂ CÓ VIỀN ĐẬM HƠN */
        border: 2px solid #ccc; /* Ví dụ: viền 2px màu xám nhạt */
        border-radius: 0.5rem; /* Giữ bo tròn nếu muốn */
        /* Hoặc nếu bạn muốn màu xanh lá cây đậm hơn cho viền */
        /* border: 2px solid #02ba5a; */

        /* Box-shadow này sẽ kết hợp với viền, bạn có thể điều chỉnh */
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        /* Nếu bạn muốn viền nổi bật hơn, có thể giảm shadow hoặc bỏ đi nếu chỉ muốn viền */
    }

    /* Nếu bạn muốn viền đổi màu khi hover */
    .card.h-100:hover {
        border-color: #02ba5a; /* Đổi màu viền sang xanh lá khi di chuột qua */
        box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15); /* Tăng bóng đổ nhẹ khi hover */
    }
    .card-body .card-title {
        font-size: 1.6rem; /* Tăng kích thước tiêu đề gói */
        font-weight: 700;
        margin-bottom: 1.25rem; /* Tăng khoảng cách dưới tên gói */
        color: #333; /* Đảm bảo màu sắc hiển thị rõ */
    }

    .card-body p.card-text.fs-4.fw-bold.text-success {
        /* Đây là phần giá */
        font-size: 1.8rem; /* Tăng kích thước giá */
        font-weight: bold;
        color: #02ba5a !important; /* Đảm bảo màu xanh lá cho giá */
        margin-top: 0.5rem; /* Khoảng cách trên giá */
        margin-bottom: 1.25rem; /* Khoảng cách dưới giá */
    }

    .card-body p.card-text.text-muted.flex-grow-1 {
        /* Đây là phần mô tả */
        font-size: 0.95rem; /* Kích thước cho mô tả */
        line-height: 1.5;
        flex-grow: 1; /* Cho phép mô tả kéo giãn để đẩy nút xuống dưới */
        margin-top: 0.5rem; /* Khoảng cách trên mô tả */
        margin-bottom: 1.5rem; /* Khoảng cách dưới mô tả (có thể tăng thêm để đẩy nút ra xa hơn) */
    }

    /* Các CSS khác của bạn cho .btn-light-green, .card.h-100, .card-body ... */
    .card-body {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        height: 100%;
        padding: 1.5rem; /* Tăng padding của card-body để tạo không gian xung quanh nội dung */
    }

    /* Đảm bảo nút "Mua ngay" được căn chỉnh tốt */
    .card-body .mt-auto {
        margin-top: auto; /* Giữ nguyên để đẩy nút xuống dưới cùng */
        text-align: center; /* Căn giữa nút */
    }
</style>
<div class="container-fluid p-0">
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
    <div class="card mt-4 shadow-sm"> <%-- Thêm shadow-sm cho card bao ngoài --%>
        <div class="card-body">
            <h4 class="card-title text-center mb-4">Các Gói Dịch Vụ Của Chúng Tôi</h4> <%-- Tiêu đề cho phần gói dịch vụ --%>
            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-4"> <%-- Dùng Bootstrap 5: 1 cột trên xs, 2 cột trên sm, 3 cột trên md trở lên. g-4 tạo gutter. --%>

                <c:choose>
                    <c:when test="${empty packages}">
                        <div class="col-12">
                            <div class="alert alert-info text-center" role="alert">
                                Không có gói dịch vụ nào được tìm thấy.
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="pack" items="${packages}">
                            <div class="col mb-4"> <%-- Mỗi gói là một cột trong hàng --%>
                                <div class="card h-100 shadow-sm d-flex flex-column"> <%-- card: đảm bảo kích thước bằng nhau, shadow-sm: thêm đổ bóng nhẹ, d-flex flex-column: dùng flexbox để nội dung kéo giãn --%>
                                    <div class="card-body d-flex flex-column text-center">
                                        <%-- Tên gói --%>
                                        <h3 class="card-title text-primary mb-2">${fn:escapeXml(pack.name)}</h3>

                                        <%-- Giá gói --%>
                                        <p class="card-text fs-4 fw-bold text-success mb-2">
                                            <fmt:formatNumber value="${pack.price}" type="currency" currencySymbol="₫" />
                                        </p>

                                        <%-- Mô tả gói --%>
                                        <p class="card-text text-muted flex-grow-1">${fn:escapeXml(pack.description)}</p> <%-- flex-grow-1 để mô tả kéo giãn, đẩy nút xuống dưới --%>

                                        <%-- Nút mua ngay - luôn nằm ở dưới cùng --%>
                                        <div class="mt-auto"> <%-- mt-auto đẩy nút xuống dưới cùng của card-body --%>
                                            <div class="row">
                                                <div class="col-6">
                                                    <form action="${pageContext.request.contextPath}/app/shop/add-to-cart" method="post">
                                                        <input type="hidden" name="packageId" value="${pack.id}">
                                                        <input type="hidden" name="quantity" value="1">
                                                        <button type="submit" class="btn btn-outline-success  w-100 rounded-pill text-nowrap">Thêm vào giỏ</button>
                                                    </form>
                                                </div>
                                                <div class="col-6">
                                                     <a href="${pageContext.request.contextPath}/app/shop/checkout?packageId=${pack.id}" class="btn btn-light-green w-100 rounded-pill text-nowrap">Mua ngay</a>
                                                </div>
                                            </div>                                            
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>
    </div>
</div>