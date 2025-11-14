<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Quản lý đặt phòng - Sunshine Hotel</title>
        <%@ include file="layout/header.jsp" %>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/css/admin.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </head>

    <body>
        <%@ include file="../thongbao.jsp" %>
        <%@ include file="layout/nav.jsp" %>

        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2 bg-light border-end p-0">
                    <%@ include file="layout/sidebar.jsp" %>
                </div>

                <div class="col-md-10 mt-4">
                    <h3 class="fw-bold text-primary mb-3">
                        <i class="bi bi-journal-check me-2"></i>Quản lý đặt phòng
                    </h3>
                    <hr>

                    <form class="d-flex justify-content-end mb-3"
                          action="${pageContext.request.contextPath}/QL-datphong" method="get">
                        <input type="hidden" name="action" value="search"/>
                        <div class="d-flex" style="min-width:350px;">
                            <input type="text" name="keyword" class="form-control me-2"
                                   placeholder="Tìm mã đơn, mã khách hoặc trạng thái..."
                                   value="${param.keyword}">
                            <button class="btn btn-outline-primary" type="submit">
                                <i class="bi bi-search"></i>
                            </button>
                        </div>
                    </form>

                    <table class="table table-hover table-bordered align-middle">
                        <thead class="table-primary text-center">
                            <tr>
                                <th>Mã đơn</th>
                                <th>Khách (ID)</th>
                                <th>Danh sách phòng</th>
                                <th>Ngày nhận</th>
                                <th>Ngày trả</th>
                                <th>Thao tác</th>

                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="dp" items="${dsDatPhong}">
                                <tr>
                                    <td class="text-center">DP${dp.maDatPhong}</td>
                                    <td class="text-center">${dp.maKH}</td>

                                    <td>
                                        <c:forEach var="ct" items="${dp.chiTiet}">
                                            <div class="room-box mb-2 p-2 border rounded d-flex justify-content-between align-items-center">
                                                <div>
                                                    <strong class="text-primary">${ct.tenPhong}</strong>
                                                    <div class="small text-muted">
                                                        Giá: <fmt:formatNumber value="${ct.gia}" type="number" groupingUsed="true"/>₫
                                                    </div>
                                                    <span class="badge
                                                          <c:choose>
                                                              <c:when test="${fn:toLowerCase(ct.trangThai) eq 'đã duyệt'}">bg-success</c:when>
                                                              <c:when test="${fn:toLowerCase(ct.trangThai) eq 'chờ duyệt'}">bg-warning text-dark</c:when>
                                                              <c:when test="${fn:toLowerCase(ct.trangThai) eq 'hủy'}">bg-danger</c:when>
                                                              <c:when test="${fn:toLowerCase(ct.trangThai) eq 'đang ở'}">bg-primary</c:when>
                                                              <c:otherwise>bg-secondary</c:otherwise>
                                                          </c:choose>">
                                                        ${ct.trangThai}
                                                    </span>

                                                </div>

                                                <div class="btn-group">
                                                    <c:choose>
                                                        <c:when test="${ct.trangThai eq 'Chờ duyệt'}">
                                                            <form action="${pageContext.request.contextPath}/QL-chitietdatphong" method="post" style="display:inline;">
                                                                <input type="hidden" name="action" value="approve"/>
                                                                <input type="hidden" name="id" value="${ct.maCTDP}"/>
                                                                <button class="btn btn-sm btn-success" title="Duyệt phòng này"
                                                                        onclick="return confirm('Xác nhận duyệt ${ct.tenPhong}?')">
                                                                    <i class="bi bi-check-circle"></i>
                                                                </button>
                                                            </form>
                                                            <form action="${pageContext.request.contextPath}/QL-chitietdatphong" method="post" style="display:inline;">
                                                                <input type="hidden" name="action" value="cancel"/>
                                                                <input type="hidden" name="id" value="${ct.maCTDP}"/>
                                                                <button class="btn btn-sm btn-danger" title="Hủy phòng này"
                                                                        onclick="return confirm('Xác nhận hủy ${ct.tenPhong}?')">
                                                                    <i class="bi bi-x-circle"></i>
                                                                </button>
                                                            </form>
                                                        </c:when>

                                                        <c:when test="${ct.trangThai eq 'Đã duyệt'}">
                                                            <form action="${pageContext.request.contextPath}/QL-chitietdatphong" method="post" style="display:inline;">
                                                                <input type="hidden" name="action" value="checkin"/>
                                                                <input type="hidden" name="id" value="${ct.maCTDP}"/>
                                                                <button class="btn btn-sm btn-primary" title="Nhận phòng"
                                                                        onclick="return confirm('Xác nhận khách nhận phòng ${ct.tenPhong}?')">
                                                                    <i class="bi bi-door-open"></i>
                                                                </button>
                                                            </form>
                                                        </c:when>

                                                        <c:when test="${ct.trangThai eq 'Đang ở'}">
                                                            <form action="${pageContext.request.contextPath}/QL-chitietdatphong" method="post" style="display:inline;">
                                                                <input type="hidden" name="action" value="checkout"/>
                                                                <input type="hidden" name="id" value="${ct.maCTDP}"/>
                                                                <button class="btn btn-sm btn-secondary" title="Trả phòng"
                                                                        onclick="return confirm('Xác nhận trả phòng ${ct.tenPhong}?')">
                                                                    <i class="bi bi-arrow-clockwise"></i>
                                                                </button>
                                                            </form>
                                                        </c:when>

                                                        <c:otherwise>
                                                            <button class="btn btn-sm btn-outline-secondary" disabled>
                                                                <i class="bi bi-lock"></i>
                                                            </button>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                        </c:forEach>

                                        <c:if test="${empty dp.chiTiet}">
                                            <span class="text-muted fst-italic">Không có phòng</span>
                                        </c:if>
                                    </td>

                                    <td class="text-center">
                                        <fmt:formatDate value="${dp.ngayNhan}" pattern="yyyy-MM-dd" />
                                    </td>
                                    <td class="text-center">
                                        <fmt:formatDate value="${dp.ngayTra}" pattern="yyyy-MM-dd" />
                                    </td>
                                    <td class="text-center">
                                        <a href="${pageContext.request.contextPath}/QL-HoaDon?action=view&id=${dp.maDatPhong}" 
                                           class="btn btn-sm btn-dark">
                                            <i class="bi bi-receipt-cutoff"></i> Hóa đơn
                                        </a>
                                    </td>


                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <div class="mt-4 d-flex flex-column align-items-center">
                        <div class="text-muted mb-2">
                            Trang ${currentPage} / ${totalPages} — Tổng <strong>${totalItems}</strong> đơn đặt phòng
                        </div>
                        <nav aria-label="Page navigation">
                            <ul class="pagination justify-content-center mb-0">
                                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                    <a class="page-link" href="?action=list&page=${currentPage-1}&size=${pageSize}" aria-label="Previous">&laquo;</a>
                                </li>
                                <c:forEach begin="1" end="${totalPages}" var="i">
                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                        <a class="page-link" href="?action=list&page=${i}&size=${pageSize}">${i}</a>
                                    </li>
                                </c:forEach>
                                <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                    <a class="page-link" href="?action=list&page=${currentPage+1}&size=${pageSize}" aria-label="Next">&raquo;</a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </div>

        <%@ include file="layout/footer.jsp" %>
        <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
