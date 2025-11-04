<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <title>Lịch sử đặt phòng - Sunshine Hotel</title>
        <jsp:include page="layout/header.jsp"/>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/css/user.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </head>
    <body>
        <%@ include file="../thongbao.jsp" %>
        <jsp:include page="layout/nav.jsp"/>

        <div class="container py-5">
            <h2 class="text-center text-primary mb-4">Lịch sử đặt phòng</h2>

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
                    <tr>
                        <td>DP001</td>
                        <td>Phòng Đôi</td>
                        <td>2025-10-20</td>
                        <td>2025-10-23</td>
                        <td><span class="badge bg-success">Đã nhận</span></td>
                    </tr>
                    <tr>
                        <td>DP002</td>
                        <td>Phòng VIP</td>
                        <td>2025-08-10</td>
                        <td>2025-08-12</td>
                        <td><span class="badge bg-secondary">Đã trả</span></td>
                    </tr>
                </tbody>
            </table>
        </div>

        <jsp:include page="layout/footer.jsp"/>
    </body>
</html>
