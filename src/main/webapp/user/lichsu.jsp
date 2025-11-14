<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Lịch sử đặt phòng - Sunshine Hotel</title>
        <jsp:include page="layout/header.jsp"/>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/css/user.css">
    </head>

    <body>

        <%@ include file="../thongbao.jsp" %>
        <jsp:include page="layout/nav.jsp"/>

        <div class="container py-5">

            <h2 class="text-center text-primary mb-4">Lịch sử đặt phòng</h2>

            <!-- Nếu không có lịch sử -->
            <c:if test="${empty lichSu}">
                <div class="alert alert-info text-center">
                    Bạn chưa có đơn đặt phòng nào.
                </div>
            </c:if>

            <!-- Nếu có lịch sử -->
            <c:if test="${not empty lichSu}">
                <table class="table table-bordered text-center">
                    <thead class="table-dark">
                        <tr>
                            <th>Mã đặt phòng</th>
                            <th>Tên phòng</th>
                            <th>Ngày nhận</th>
                            <th>Ngày trả</th>
                            <th>Trạng thái</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="dp" items="${lichSu}">
                            <tr>
                                <td>DP${dp.maDatPhong}</td>
                                <td>${dp.tenPhong}</td>
                                <td>${dp.ngayNhan}</td>
                                <td>${dp.ngayTra}</td>

                                <td>
                                    <c:choose>

                                        <c:when test="${fn:contains(dp.trangThai, 'Đang ở')}">
                                            <span class="badge bg-success">Đang ở</span>
                                        </c:when>

                                        <c:when test="${fn:contains(dp.trangThai, 'Đang xử lý')}">
                                            <span class="badge bg-info">Đang xử lý</span>
                                        </c:when>

                                        <c:when test="${fn:contains(dp.trangThai, 'Chờ duyệt')}">
                                            <span class="badge bg-warning text-dark">Chờ duyệt</span>
                                        </c:when>

                                        <c:when test="${fn:contains(dp.trangThai, 'Hủy')}">
                                            <span class="badge bg-danger">Hủy</span>
                                        </c:when>

                                        <c:when test="${fn:contains(dp.trangThai, 'Đã trả phòng')}">
                                            <span class="badge bg-secondary">Đã trả phòng</span>
                                        </c:when>

                                        <c:otherwise>
                                            <span class="badge bg-secondary">${dp.trangThai}</span>
                                        </c:otherwise>

                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

        </div>

        <jsp:include page="layout/footer.jsp"/>

    </body>
</html>
