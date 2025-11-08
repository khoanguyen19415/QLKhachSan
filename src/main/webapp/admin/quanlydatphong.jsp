<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Quản lý đặt phòng - Sunshine Hotel</title>
        <%@ include file="layout/header.jsp" %>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/css/admin.css">
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

                    <!-- Thanh tìm kiếm -->
                    <form class="d-flex justify-content-end mb-3" 
                          action="${pageContext.request.contextPath}/QL-datphong" method="get">
                        <input type="hidden" name="action" value="search"/>
                        <div class="d-flex" style="min-width:350px;">
                            <input type="text" name="keyword" class="form-control me-2" 
                                   placeholder="Tìm mã đơn, mã khách, mã phòng hoặc trạng thái..." 
                                   value="${param.keyword}">
                            <button class="btn btn-outline-primary" type="submit"><i class="bi bi-search"></i></button>
                        </div>
                    </form>

                    <!-- Bảng dữ liệu -->
                    <table class="table table-hover table-bordered align-middle">
                        <thead class="table-primary text-center">
                            <tr>
                                <th>Mã đơn</th>
                                <th>Khách (ID)</th>
                                <th>Phòng (ID)</th>
                                <th>Tên Phòng</th>
                                <th>Ngày nhận</th>
                                <th>Ngày trả</th>
                                <th>Trạng thái</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="dp" items="${dsDatPhong}">
                                <tr>
                                    <td class="text-center">DP<c:out value="${dp.maDatPhong}" /></td>
                                    <td class="text-center"><c:out value="${dp.maKH}" /></td>
                                    <td class="text-center"><c:out value="${dp.maPhong}" /></td>
                                    <td class="text-center"><c:out value="${dp.tenPhong}" /></td>
                                    <td class="text-center"><c:out value="${dp.ngayNhan}" /></td>
                                    <td class="text-center"><c:out value="${dp.ngayTra}" /></td>
                                    <td class="text-center">
                                        <c:choose>
                                            <c:when test="${fn:contains(dp.trangThai, 'Chờ')}">
                                                <span class="badge bg-warning text-dark"><c:out value="${dp.trangThai}" /></span>
                                            </c:when>
                                            <c:when test="${fn:contains(dp.trangThai, 'hủy') || fn:contains(dp.trangThai, 'Hủy')}">
                                                <span class="badge bg-danger"><c:out value="${dp.trangThai}" /></span>
                                            </c:when>
                                            <c:when test="${fn:contains(dp.trangThai, 'xác nhận')}">
                                                <span class="badge bg-success"><c:out value="${dp.trangThai}" /></span>
                                            </c:when>
                                            <c:when test="${fn:contains(dp.trangThai, 'nhận')}">
                                                <span class="badge bg-primary"><c:out value="${dp.trangThai}" /></span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary"><c:out value="${dp.trangThai}" /></span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td class="text-center">
                                        <c:set var="st" value="${fn:trim(fn:toLowerCase(dp.trangThai))}" />
                                        <div class="d-flex justify-content-center gap-2">
                                            <c:choose>
                                                <c:when test="${st == 'chờ duyệt' || st == 'chờ xác nhận'}">
                                                    <form action="${pageContext.request.contextPath}/QL-datphong" method="post" style="display:inline-block;">
                                                        <input type="hidden" name="action" value="approve" />
                                                        <input type="hidden" name="id" value="${dp.maDatPhong}" />
                                                        <button class="btn btn-sm btn-success" type="submit" onclick="return confirm('Duyệt đơn này?')">
                                                            <i class="bi bi-check-circle"></i>
                                                        </button>
                                                    </form>

                                                    <form action="${pageContext.request.contextPath}/QL-datphong" method="post" style="display:inline-block;">
                                                        <input type="hidden" name="action" value="reject" />
                                                        <input type="hidden" name="id" value="${dp.maDatPhong}" />
                                                        <button class="btn btn-sm btn-danger" type="submit" onclick="return confirm('Từ chối đơn này?')">
                                                            <i class="bi bi-x-circle"></i>
                                                        </button>
                                                    </form>
                                                </c:when>

                                                <c:when test="${st == 'đã xác nhận' || st == 'xác nhận'}">
                                                    <form action="${pageContext.request.contextPath}/QL-datphong" method="post">
                                                        <input type="hidden" name="action" value="checkin" />
                                                        <input type="hidden" name="id" value="${dp.maDatPhong}" />
                                                        <button class="btn btn-sm btn-primary" type="submit" onclick="return confirm('Xác nhận khách nhận phòng?')">
                                                            <i class="bi bi-door-open"></i>
                                                        </button>
                                                    </form>
                                                </c:when>

                                                <c:when test="${st == 'đã nhận' || st == 'nhận'}">
                                                    <form action="${pageContext.request.contextPath}/QL-datphong" method="post">
                                                        <input type="hidden" name="action" value="checkout" />
                                                        <input type="hidden" name="id" value="${dp.maDatPhong}" />
                                                        <button class="btn btn-sm btn-secondary" type="submit" onclick="return confirm('Xác nhận trả phòng?')">
                                                            <i class="bi bi-arrow-clockwise"></i>
                                                        </button>
                                                    </form>
                                                </c:when>

                                                <c:otherwise>
                                                    <button class="btn btn-sm btn-secondary" disabled><i class="bi bi-lock"></i></button>
                                                    </c:otherwise>
                                                </c:choose>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <!-- ======= Pagination ======= -->
                    <div class="mt-4 d-flex flex-column align-items-center">
                        <div class="text-muted mb-2">
                            Trang ${currentPage} / ${totalPages} — Tổng <strong>${totalItems}</strong> đơn đặt phòng
                        </div>

                        <nav aria-label="Page navigation">
                            <ul class="pagination justify-content-center mb-0">
                                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                    <a class="page-link" href="${pageContext.request.contextPath}/QL-datphong?action=list&page=${currentPage-1}&size=${pageSize}" aria-label="Previous">
                                        &laquo;
                                    </a>
                                </li>

                                <c:forEach begin="1" end="${totalPages}" var="i">
                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                        <a class="page-link" href="${pageContext.request.contextPath}/QL-datphong?action=list&page=${i}&size=${pageSize}">${i}</a>
                                    </li>
                                </c:forEach>

                                <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                    <a class="page-link" href="${pageContext.request.contextPath}/QL-datphong?action=list&page=${currentPage+1}&size=${pageSize}" aria-label="Next">
                                        &raquo;
                                    </a>
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
