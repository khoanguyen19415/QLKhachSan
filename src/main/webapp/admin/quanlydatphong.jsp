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

                    <!-- Bộ lọc / tìm kiếm -->
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <div class="d-flex gap-2">
                            <select class="form-select">
                                <option>Tất cả trạng thái</option>
                                <option>Chờ duyệt</option>
                                <option>Đã xác nhận</option>
                                <option>Đã hủy</option>
                                <option>Đang ở</option>
                            </select>
                        </div>
                        <input type="text" class="form-control w-25" placeholder="Tìm mã đơn hoặc khách hàng...">
                    </div>

                    <table class="table table-hover table-bordered align-middle">
                        <thead class="table-primary text-center">
                            <tr>
                                <th>Mã đơn</th>
                                <th>Khách (ID)</th>
                                <th>Phòng (ID)</th>
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
                                    <td class="text-center"><c:out value="${dp.ngayNhan}" /></td>
                                    <td class="text-center"><c:out value="${dp.ngayTra}" /></td>
                                    <td class="text-center">
                                        <c:choose>
                                            <c:when test="${fn:contains(dp.trangThai, 'Chờ')}">
                                                <span class="badge bg-warning text-dark"><c:out value="${dp.trangThai}" /></span>
                                            </c:when>
                                            <c:when test="${fn:contains(dp.trangThai, 'hủy') || fn:contains(dp.trangThai, 'Hủy') || dp.trangThai == 'Đã hủy'}">
                                                <span class="badge bg-danger"><c:out value="${dp.trangThai}" /></span>
                                            </c:when>
                                            <c:when test="${fn:contains(dp.trangThai, 'xác nhận') || fn:contains(dp.trangThai, 'Đã xác nhận')}">
                                                <span class="badge bg-success"><c:out value="${dp.trangThai}" /></span>
                                            </c:when>
                                            <c:when test="${fn:contains(dp.trangThai, 'nhận') || fn:contains(dp.trangThai, 'Đã nhận')}">
                                                <span class="badge bg-primary"><c:out value="${dp.trangThai}" /></span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary"><c:out value="${dp.trangThai}" /></span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td class="text-center">
                                        <%-- chuẩn hóa giá trị trạng thái xuống chữ thường và bỏ khoảng trắng --%>
                                        <c:set var="st" value="${fn:trim(fn:toLowerCase(dp.trangThai))}" />
                                        <%-- Buttons: chỉ xuất đúng các nút ứng với trạng thái --%>
                                        <div class="action-group" style="justify-content:center;">
                                            <c:choose>
                                                <c:when test="${st == 'chờ duyệt' || st == 'chờ xác nhận'}">
                                                    <form action="${pageContext.request.contextPath}/QL-datphong" method="post" class="action-item" style="display:inline-block; margin-right:6px;">
                                                        <input type="hidden" name="action" value="approve" />
                                                        <input type="hidden" name="id" value="${dp.maDatPhong}" />
                                                        <button class="btn btn-sm btn-success" type="submit" onclick="return confirm('Duyệt đơn này?')">
                                                            <i class="bi bi-check-circle"></i> Duyệt
                                                        </button>
                                                    </form>

                                                    <form action="${pageContext.request.contextPath}/QL-datphong" method="post" class="action-item" style="display:inline-block;">
                                                        <input type="hidden" name="action" value="reject" />
                                                        <input type="hidden" name="id" value="${dp.maDatPhong}" />
                                                        <button class="btn btn-sm btn-danger" type="submit" onclick="return confirm('Từ chối đơn này?')">
                                                            <i class="bi bi-x-circle"></i> Từ chối
                                                        </button>
                                                    </form>
                                                </c:when>

                                                <c:when test="${st == 'đã xác nhận' || st == 'xác nhận'}">
                                                    <form action="${pageContext.request.contextPath}/QL-datphong" method="post" class="action-item">
                                                        <input type="hidden" name="action" value="checkin" />
                                                        <input type="hidden" name="id" value="${dp.maDatPhong}" />
                                                        <button class="btn btn-sm btn-primary" type="submit" onclick="return confirm('Xác nhận khách nhận phòng?')">
                                                            <i class="bi bi-door-open"></i> Nhận phòng
                                                        </button>
                                                    </form>
                                                </c:when>

                                                <c:when test="${st == 'đã nhận' || st == 'nhận'}">
                                                    <form action="${pageContext.request.contextPath}/QL-datphong" method="post" class="action-item">
                                                        <input type="hidden" name="action" value="checkout" />
                                                        <input type="hidden" name="id" value="${dp.maDatPhong}" />
                                                        <button class="btn btn-sm btn-secondary" type="submit" onclick="return confirm('Xác nhận trả phòng?')">
                                                            <i class="bi bi-arrow-clockwise"></i> Trả phòng
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
                </div>
            </div>
        </div>

        <%@ include file="layout/footer.jsp" %>
    </body>
</html>
