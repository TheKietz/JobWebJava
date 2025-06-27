<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<head>
    <style>
        .btn-light-green {
            background-color: #6fcf97;
            color: white;
            border: none;
        }
        .btn-light-green:hover {
            background-color: #5ec488;
        }
        .avatar-container {
            position: relative;
            width: 150px;
            height: 150px;
            border-radius: 50%;
            overflow: hidden;
            margin: 0 auto 10px;
            border: 4px solid #f0f0f0;
            box-shadow: 0 2px 2px rgba(204, 197, 185, 0.5);
        }
        .avatar-container img.avatar {
            width: 100%;
            height: 100%;
            object-fit: cover;
            transition: opacity 0.3s ease;
        }
        .avatar-overlay {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            color: white;
            display: flex;
            justify-content: center;
            align-items: center;
            opacity: 0;
            transition: opacity 0.3s ease;
            cursor: pointer;
            font-weight: bold;
            text-align: center;
            text-decoration: none;
        }
        .avatar-container:hover .avatar-overlay {
            opacity: 1;
        }
        .avatar-container:hover img.avatar {
            opacity: 0.7;
        }
        .hidden-file-input {
            display: none;
        }
    </style>
</head>
<body>
    <div class="content">
        <form action="${pageContext.request.contextPath}/app/profile/update" method="post" enctype="multipart/form-data">
            <div class="row">
                <div class="col-md-4">
                    <div class="card card-user">
                        <div class="image">
                            <img src="${pageContext.request.contextPath}/template/app/assets/img/damir-bosnjak.jpg" alt="Background">
                        </div>

                        <div class="card-body">
                            <div class="author">
                                <div class="avatar-container" >
                                    <img class="avatar" src="${pageContext.request.contextPath}${employer.logoUrl}" alt="Company Logo" style="width: 100%; height: 100%; object-fit: cover;">

                                    <!-- Overlay hiện chữ Đổi ảnh -->
                                    <a href="#" class="avatar-overlay" id="changeAvatarOverlay">Đổi ảnh</a>

                                    <!-- Input ảnh nằm đè lên hình -->
                                    <input type="file" name="imageFile" id="avatarFileInput"
                                           accept="image/*"
                                           style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; opacity: 0; cursor: pointer;" />
                                </div>
                                <h5 class="title">${user.fullName}</h5>
                            </div>

                            <h5 class="description text-center"></h5>
                        </div>
                        <div class="card-footer">
                            <hr>
                            <div class="button-container">
                                <div class="row">
                                    <div class="col-lg-3 col-md-6 col-6 ml-auto">
                                        <h5>${countJob}<br><small>Bài đăng</small></h5>
                                    </div>
                                    <div class="col-lg-4 col-md-6 col-6 ml-auto mr-auto">
                                        <h5>32<br><small>Ứng viên</small></h5>
                                    </div>
                                    <div class="col-lg-3 mr-auto">
                                        <h5>24<br><small>Ngày</small></h5>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-8">
                    <div class="card card-user">
                        <div class="card-header">
                            <h5 class="card-title">Tùy chỉnh thông tin công ty</h5>
                            <c:if test="${not empty message}">
                                <div class="alert alert-success">${message}</div>
                            </c:if>
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger">${error}</div>
                            </c:if>
                        </div>
                        <div class="card-body">



                            <!-- Trường ẩn cho User -->
                            <input type="hidden" name="user.id" value="${user.id}">                           
                            <input type="hidden" name="user.password" value="${user.password}">
                            <input type="hidden" name="user.role" value="${user.role}">
                            <!-- Trường ẩn cho Employer -->
                            <input type="hidden" name="employer.id" value="${employer.id}">
                            <input type="hidden" name="employer.userId" value="${employer.userId}">
                            <input type="hidden" name="employer.logoUrl" value="${employer.logoUrl != null ? employer.logoUrl : ''}">

                            <div class="row">
                                <div class="col-md-7 pr-1">
                                    <div class="form-group">
                                        <label>Tên công ty</label>
                                        <input type="text" class="form-control" name="employer.companyName" value="${employer.companyName}" required>
                                    </div>
                                </div>
                                <div class="col-md-5 pl-1">
                                    <div class="form-group">
                                        <label>Tên đầy đủ (Giám đốc điều hành)</label>
                                        <input type="text" class="form-control" name="user.fullName" value="${user.fullName}" required>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-5 pr-1">
                                    <div class="form-group">
                                        <label>Địa chỉ email</label>
                                        <input type="email" class="form-control" name="user.email" value="${user.email}" required>
                                    </div>
                                </div>
                                <div class="col-md-3 pl-1">
                                    <div class="form-group">
                                        <label>Số thành viên</label>
                                        <input type="text" class="form-control" name="employer.companySize" value="${employer.companySize}">
                                    </div>
                                </div>
                                <div class="col-md-4 pl-1">
                                    <div class="form-group">
                                        <label>Đường dẫn web</label>
                                        <input type="url" class="form-control" name="employer.website" value="${employer.website}">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label>Địa chỉ</label>
                                        <input type="text" class="form-control" name="employer.address" value="${employer.address}" required>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label>Thông tin giới thiệu</label>
                                        <textarea class="form-control textarea" name="employer.description">${employer.description}</textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="update ml-auto mr-auto">
                                    <button type="submit" class="btn btn-light-green btn-round">Cập nhật thông tin</button>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <script>
        document.getElementById('changeAvatarOverlay').addEventListener('click', function (event) {
            event.preventDefault();
            document.getElementById('avatarFileInput').click();
        });

        document.getElementById('avatarFileInput').addEventListener('change', function () {
            if (this.files && this.files[0]) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    document.querySelector('.avatar-container img.avatar').src = e.target.result;
                };
                reader.readAsDataURL(this.files[0]);
            }
        });
    </script>
</body>
</html>