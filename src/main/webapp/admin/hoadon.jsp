<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Hóa đơn - Sunshine Hotel</title>

        <%@ include file="layout/header.jsp" %>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/css/admin.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </head>

    <body>

        <%@ include file="../thongbao.jsp" %>
        <%@ include file="layout/nav.jsp" %>

        <div class="container-fluid">
            <div class="row">

                <!-- Sidebar -->
                <div class="col-md-2 bg-light border-end p-0">
                    <%@ include file="layout/sidebar.jsp" %>
                </div>

                <!-- Main -->
                <div class="col-md-10 mt-4">

                    <h3 class="fw-bold text-primary mb-3">
                        <i class="bi bi-receipt-cutoff me-2"></i>Hóa đơn đặt phòng
                    </h3>

                    <hr>

                    <a href="${pageContext.request.contextPath}/QL-datphong" class="btn btn-secondary mb-3">
                        <i class="bi bi-arrow-left"></i> Quay lại danh sách
                    </a>

                    <div class="card shadow">
                        <div class="card-header bg-primary text-white">
                            <h5 class="mb-0"><i class="bi bi-journal-text"></i> Thông tin đơn đặt phòng</h5>
                        </div>

                        <div class="card-body">

                            <!-- Thông tin chung -->
                            <div class="row mb-4">
                                <div class="col-md-6">
                                    <p><strong>Mã đơn:</strong> DP${hoaDon.maDatPhong}</p>
                                    <p><strong>Mã khách hàng:</strong> KH${hoaDon.maKH}</p>
                                    <p><strong>Khách:</strong> ${hoaDon.tenKhach}</p>
                                    <p><strong>Số điện thoại:</strong> ${hoaDon.sdt}</p>
                                </div>

                                <div class="col-md-6">
                                    <p><strong>Ngày nhận:</strong> 
                                        <fmt:formatDate value="${hoaDon.ngayNhan}" pattern="yyyy-MM-dd" />
                                    </p>
                                    <p><strong>Ngày trả:</strong> 
                                        <fmt:formatDate value="${hoaDon.ngayTra}" pattern="yyyy-MM-dd" />
                                    </p>
                                    <p><strong>Ngày lập hóa đơn:</strong> 
                                        <fmt:formatDate value="${hoaDon.ngayLap}" pattern="yyyy-MM-dd HH:mm" />
                                    </p>
                                    <p><strong>Trạng thái thanh toán:</strong>
                                        <span class="badge ${hoaDon.daThanhToan ? 'bg-success' : 'bg-warning text-dark'}">
                                            ${hoaDon.daThanhToan ? 'Đã thanh toán' : 'Chưa thanh toán'}
                                        </span>
                                    </p>
                                </div>
                            </div>

                            <hr>

                            <!-- Chi tiết hóa đơn -->
                            <h5 class="fw-bold mb-3">Chi tiết hóa đơn</h5>

                            <table class="table table-bordered table-hover align-middle">
                                <thead class="table-light text-center">
                                    <tr>
                                        <th>Phòng</th>
                                        <th>Đơn giá</th>
                                        <th>Số đêm</th>
                                        <th>Thành tiền</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="ct" items="${chiTiet}">
                                        <tr>
                                            <td class="text-center fw-bold">${ct.tenPhong}</td>

                                            <td class="text-end">
                                                <fmt:formatNumber value="${ct.donGia}" type="number" groupingUsed="true"/> ₫
                                            </td>

                                            <td class="text-center">${ct.soDem}</td>

                                            <td class="text-end text-danger fw-bold">
                                                <fmt:formatNumber value="${ct.thanhTien}" type="number" groupingUsed="true"/> ₫
                                            </td>
                                        </tr>
                                    </c:forEach>

                                    <c:if test="${empty chiTiet}">
                                        <tr>
                                            <td colspan="4" class="text-center fst-italic text-muted">
                                                Không có chi tiết hóa đơn
                                            </td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>

                            <div class="text-end mt-3">
                                <h4 class="fw-bold text-danger">
                                    Tổng cộng:
                                    <fmt:formatNumber value="${hoaDon.tongTien}" type="number" groupingUsed="true"/> ₫
                                </h4>

                                <form action="${pageContext.request.contextPath}/QL-HoaDon" method="post">
                                    <input type="hidden" name="action" value="xuatHoaDon">
                                    <input type="hidden" name="maDatPhong" value="${hoaDon.maDatPhong}">
                                    <button class="btn btn-success">
                                        <i class="bi bi-printer-fill"></i> Xuất hóa đơn
                                    </button>
                                </form>

                            </div>

                        </div>
                    </div>

                </div>
            </div>
        </div>

        <%@ include file="layout/footer.jsp" %>

        <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>
