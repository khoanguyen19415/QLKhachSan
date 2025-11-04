<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Đặt phòng</title>
        <jsp:include page="/user/layout/header.jsp" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </head>
    <body>
        <%@ include file="../thongbao.jsp" %>
        <jsp:include page="/user/layout/nav.jsp" />

        <div class="container py-5">
            <c:if test="${not empty success}">
                <div class="alert alert-success">${success}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <c:if test="${not empty phong}">
                <div class="row g-4 justify-content-center">
                    <div class="col-md-5">
                        <div class="card shadow-sm">
                            <!-- ảnh: nếu không có ảnh mặc định -->
                            <c:set var="imgSrc" value="${firstImages[phong.maPhong]}" />
                            <c:choose>
                                <c:when test="${not empty imgSrc}">
                                    <img src="${imgSrc}" alt="Ảnh ${phong.tenPhong}" class="room-img">
                                </c:when>
                                <c:otherwise>
                                    <div class="no-image">
                                        <div>
                                            <i class="bi bi-image fs-1"></i>
                                            <div class="small mt-1">Chưa có ảnh</div>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <div class="card-body text-center">
                                <h5 class="card-title text-primary">${phong.tenPhong}</h5>
                                <p><fmt:formatNumber value="${phong.gia}" type="number" groupingUsed="true"/> đ / đêm</p>                                                               
                                <p>${phong.moTa}</p>
                                <p>
                                    Trạng thái:
                                    <!-- Trạng thái -->
                                    <c:choose>
                                        <c:when test="${isAvailable}">
                                            <span class="badge badge-status bg-success">Còn trống</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-status bg-danger">${phong.trangThai}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </p>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <form class="shadow p-4 bg-light rounded" action="${pageContext.request.contextPath}/dat-phong" method="post">
                            <input type="hidden" name="action" value="book" />
                            <input type="hidden" name="maPhong" value="${phong.maPhong}" />
                            <!-- GIẢ SỬ: bạn có thể lấy MaKH từ session khi user login. Tạm dùng input -->
                            <div class="mb-3">
                                <label class="form-label">Mã khách (MaKH) - (vd: 1 nếu chưa login)</label>
                                <input type="number" name="maKH" class="form-control" required value="1">
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Ngày nhận</label>
                                <input type="date" name="ngayNhan" class="form-control" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Ngày trả</label>
                                <input type="date" name="ngayTra" class="form-control" required>
                            </div>

                            <div class="text-center">
                                <c:choose>
                                    <c:when test="${isAvailable}">
                                        <button type="submit" class="btn btn-primary px-4">Xác nhận đặt phòng</button>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="button" class="btn btn-secondary px-4" disabled>Không thể đặt (Đã đặt)</button>
                                    </c:otherwise>
                                </c:choose>

                                <div class="mt-3 text-center">
                                    <a href="${pageContext.request.contextPath}/phong" class="btn btn-outline-secondary">Quay lại danh sách</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </c:if>

        </div>

        <jsp:include page="/user/layout/footer.jsp" />
    </body>
</html>
