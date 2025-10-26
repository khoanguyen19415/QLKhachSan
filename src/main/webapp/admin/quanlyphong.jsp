<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý phòng - Sunshine Hotel</title>
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

            <!-- Nội dung chính -->
            <div class="col-md-10 mt-4">
                <h3 class="fw-bold text-primary mb-3">
                    <i class="bi bi-door-open me-2"></i>Quản lý phòng
                </h3>
                <hr>

                <!-- Thanh công cụ -->
                <div class="d-flex justify-content-between mb-3">
                    <!-- Nút mở modal thêm phòng -->
                    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalThemPhong">
                        <i class="bi bi-plus-circle me-1"></i> Thêm phòng
                    </button>
                    <input type="text" class="form-control w-25" placeholder="Tìm kiếm phòng...">
                </div>

                <!-- Bảng danh sách phòng -->
                <table class="table table-hover table-bordered align-middle">
                    <thead class="table-primary text-center">
                        <tr>
                            <th>Mã phòng</th>
                            <th>Tên phòng</th>
                            <th>Loại phòng</th>
                            <th>Giá / Đêm</th>
                            <th>Mô tả</th>
                            <th>Trạng thái</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="p" items="${dsPhong}">
                            <tr class="text-center">
                                <td>${p.maPhong}</td>
                                <td>${p.tenPhong}</td>
                                <td>${p.loaiPhong}</td>
                                <td>${p.gia}</td>
                                <td>${p.moTa}</td>
                                <td>
                                    <span class="${p.trangThai eq 'Còn trống' ? 'status-available' : 'status-unavailable'}"></span>
                                    ${p.trangThai}
                                </td>
                                <td>
                                    <button class="btn btn-sm btn-warning"><i class="bi bi-pencil-square"></i></button>
                                    <a class="btn btn-sm btn-danger" href="QL-Phong?action=delete&id=${p.maPhong}">
                                        <i class="bi bi-trash"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
<div class="modal fade" id="modalThemPhong" tabindex="-1" aria-labelledby="themPhongLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form action="QL-Phong" method="post">
                <input type="hidden" name="action" value="add">
                <div class="modal-header">
                    <h5 class="modal-title fw-bold text-primary" id="themPhongLabel">
                        <i class="bi bi-plus-circle me-2"></i>Thêm phòng mới
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>

                <div class="modal-body">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label fw-bold">Tên phòng</label>
                            <input type="text" name="tenPhong" class="form-control" required placeholder="VD: Phòng Deluxe Hướng Biển">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label fw-bold">Loại phòng</label>
                            <select name="loaiPhong" class="form-select" required>
                                <option value="">-- Chọn loại phòng --</option>
                                <option value="Standard">Standard</option>
                                <option value="Deluxe">Deluxe</option>
                                <option value="Suite">Suite</option>
                                <option value="VIP">VIP</option>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label fw-bold">Giá / Đêm</label>
                            <input type="number" name="gia" class="form-control" min="0" required placeholder="VD: 1500000">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label fw-bold">Trạng thái</label>
                            <select name="trangThai" class="form-select">
                                <option value="Còn trống">Còn trống</option>
                                <option value="Đã đặt">Đã đặt</option>
                                <option value="Bảo trì">Bảo trì</option>
                            </select>
                        </div>
                        <div class="col-12">
                            <label class="form-label fw-bold">Mô tả</label>
                            <textarea name="moTa" class="form-control" rows="3" placeholder="VD: Phòng có ban công hướng biển, 2 giường đôi..."></textarea>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        Hủy
                    </button>
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-save me-1"></i> Lưu phòng
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <%@ include file="layout/footer.jsp" %>
</body>
</html>
