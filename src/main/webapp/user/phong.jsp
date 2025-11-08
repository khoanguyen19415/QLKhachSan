<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Danh sách phòng - Sunshine Hotel</title>
        <%@ include file="layout/header.jsp" %>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user.css">
    </head>
    <body>
        <jsp:include page="layout/nav.jsp"/>

        <div class="container py-4">
            <h2 class="text-center text-primary mb-4">Danh sách phòng</h2>

            <div class="row g-4">
                <c:choose>
                    <c:when test="${empty dsPhong}">
                        <div class="col-12">
                            <div class="alert alert-info text-center">Hiện chưa có phòng nào trong hệ thống.</div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="p" items="${dsPhong}">
                            <div class="col-md-4">
                                <div class="card room-card shadow-sm h-100">
                                    <c:set var="imgSrc" value="${firstImages[p.maPhong]}" />
                                    <c:choose>
                                        <c:when test="${not empty imgSrc}">
                                            <img src="${imgSrc}" alt="Ảnh ${p.tenPhong}" class="room-img">
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

                                    <div class="card-body room-body text-center">
                                        <div>
                                            <h5 class="card-title mb-1">${p.tenPhong}</h5>
                                            <div class="text-muted small mb-2">${p.loaiPhong}</div>
                                            <p class="mb-2 text-truncate" title="${p.moTa}">${p.moTa}</p>
                                        </div>

                                        <div>
                                            <div class="room-price mb-2">
                                                <fmt:formatNumber value="${p.gia}" type="number" groupingUsed="true"/> đ / đêm
                                            </div>
                                            <div class="room-actions">
                                                <a href="${pageContext.request.contextPath}/chi-tiet-phong?maPhong=${p.maPhong}" class="btn btn-primary">Xem chi tiết</a>

                                                <a href="${pageContext.request.contextPath}/dat-phong?maPhong=${p.maPhong}" class="btn btn-sm btn-primary">
                                                    <i class="bi bi-calendar-check"></i> Đặt ngay
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- ======= Pagination ======= -->
            <div class="mt-4 d-flex flex-column align-items-center">
                <div class="text-muted mb-2">
                    Hiển thị trang ${currentPage} / ${totalPages} — Tổng <strong>${totalItems}</strong> phòng
                </div>

                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center mb-0">
                        <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/phong?page=${currentPage-1}&size=${pageSize}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>

                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/phong?page=${i}&size=${pageSize}">${i}</a>
                            </li>
                        </c:forEach>

                        <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/phong?page=${currentPage+1}&size=${pageSize}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>

        <jsp:include page="layout/footer.jsp"/>
    </body>
</html>
