<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Chi tiết phòng</title>
        <jsp:include page="layout/header.jsp" />
        <link rel="stylesheet" href="<%= request.getContextPath()%>/css/user.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    </head>
    <body>
        <jsp:include page="layout/nav.jsp" />

        <div class="container py-4">
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <c:if test="${not empty phong}">
                <div class="row g-4">
                    <div class="col-md-7">
                        <div class="card">
                            <div class="card-body">
                                <h3 class="card-title">${phong.tenPhong}</h3>
                                <p class="mb-1">Loại phòng: <strong>${phong.loaiPhong}</strong></p>
                                <p class="mb-1">Giá / đêm: <strong><fmt:formatNumber value="${phong.gia}" type="number" /></strong></p>

                                <!-- Trạng thái -->
                                <c:choose>
                                    <c:when test="${isAvailable}">
                                        <span class="badge badge-status bg-success">Còn trống</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge badge-status bg-danger">${phong.trangThai}</span>
                                    </c:otherwise>
                                </c:choose>

                                <hr/>

                                <h5>Mô tả</h5>
                                <p>${phong.moTa}</p>

                                <h5 class="mt-3">Hình ảnh</h5>
                                <div class="row room-images">
                                    <c:if test="${empty dsAnh}">
                                        <div class="col-12 text-center text-muted p-4">
                                            <img src="${pageContext.request.contextPath}/images/no-image.png" class="img-fluid" alt="chưa có ảnh">
                                        </div>
                                    </c:if>
                                    <c:forEach var="a" items="${dsAnh}">
                                        <div class="col-md-6 mb-3">
                                            <img src="${a.duongDanAnh}" alt="ảnh phòng" class="img-thumb img-fluid rounded">
                                        </div>
                                    </c:forEach>
                                </div>

                                <h5 class="mt-3">Tiện nghi</h5>
                                <ul>
                                    <c:forEach var="ct" items="${dsChiTiet}">
                                        <li><strong>${ct.tienNghi}</strong> - ${ct.moTa}</li>
                                            </c:forEach>
                                            <c:if test="${empty dsChiTiet}">
                                        <li class="text-muted">Chưa có tiện nghi được cấu hình.</li>
                                        </c:if>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-5">
                        <div class="card p-3">
                            <h5 class="mb-3">Đặt phòng</h5>

                            <!-- Nếu không còn trống sẽ disable form -->
                            <c:choose>
                                <c:when test="${isAvailable}">
                                    <a href="${pageContext.request.contextPath}/dat-phong?action=view&maPhong=${phong.maPhong}"
                                       class="btn btn-lg btn-primary w-100">
                                        <i class="bi bi-calendar3-check me-1"></i> Đặt phòng ngay
                                    </a>

                                    <div class="mt-3 text-center">
                                        <a href="${pageContext.request.contextPath}/phong" class="btn btn-outline-secondary">Quay lại danh sách</a>
                                    </div>
                                </c:when>

                                <c:otherwise>
                                    <div class="alert alert-warning">
                                        Phòng hiện tại <strong>không thể đặt</strong> vì đã hết phòng trống: <em>${phong.trangThai}</em>.
                                    </div>
                                    <div class="d-grid">
                                        <button class="btn btn-secondary" disabled>Không thể đặt</button>
                                    </div>
                                    <div class="mt-3 text-center">
                                        <a href="${pageContext.request.contextPath}/phong" class="btn btn-outline-secondary">Quay lại danh sách</a>
                                    </div>
                                </c:otherwise>
                            </c:choose>

                        </div>
                    </div>
                </div>
            </c:if>
        </div>

        <jsp:include page="layout/footer.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
