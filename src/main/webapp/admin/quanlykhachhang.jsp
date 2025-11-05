<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý khách hàng - Sunshine Hotel</title>
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
                    <i class="bi bi-people me-2"></i>Quản lý khách hàng
                </h3>
                <hr>

                <!-- Thanh công cụ -->
                <div class="d-flex justify-content-between mb-3">
                    <!-- Nút mở form -->
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#formThemKhachHang">
                        <i class="bi bi-person-plus me-1"></i> Thêm khách hàng
                    </button>

                    <form class="d-flex" action="QL-Khachhang" method="post">
                        <input type="hidden" name="action" value="search" />
                        <input type="text" name="hoTen" value="${param.hoTen}" class="form-control" placeholder="Nhập tên khách hàng ...">
                        <button class="btn btn-outline-primary ms-2" type="submit"><i class="bi bi-search"></i></button>
                    </form>
                </div>

                <!-- Bảng danh sách khách hàng -->
                <table class="table table-hover table-bordered align-middle">
                    <thead class="table-primary text-center">
                        <tr>
                            <th>Mã KH</th>
                            <th>Họ tên</th>
                            <th>Số điện thoại</th>
                            <th>Email</th>
                            <th>Địa chỉ</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="kh" items="${dsKH}">
                            <tr>
                                <td class="text-center">${kh.maKH}</td>
                                <td>${kh.hoTen}</td>
                                <td class="text-center">${kh.soDienThoai}</td>
                                <td>${kh.email}</td>
                                <td>${kh.diaChi}</td>
                                <td class="text-center">
                                    <button class="btn btn-sm btn-warning btn-edit" 
                                            data-makh="${kh.maKH}"
                                            data-hotenkh="${kh.hoTen}"
                                            data-sdt="${kh.soDienThoai}"
                                            data-email="${kh.email}"
                                            data-diachi="${kh.diaChi}"
                                            title="Sửa">
                                        <i class="bi bi-pencil-square"></i>
                                    </button>

                                    <a class="btn btn-sm btn-danger" href="QL-Khachhang?action=delete&id=${kh.maKH}" onclick="return confirm('Xóa khách hàng này?')">
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

    <!-- Modal: Thêm khách hàng (giữ nguyên) -->
    <div class="modal fade" id="formThemKhachHang" tabindex="-1" aria-labelledby="formThemKhachHangLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <form action="<%= request.getContextPath()%>/QL-Khachhang?action=add" method="post">
                    <div class="modal-header bg-primary text-white">
                        <h5 class="modal-title" id="formThemKhachHangLabel">
                            <i class="bi bi-person-plus me-2"></i>Thêm khách hàng mới
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
                    </div>

                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label">Họ tên</label>
                            <input type="text" name="hoTen" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Số điện thoại</label>
                            <input type="text" name="soDienThoai" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" name="email" class="form-control">
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Địa chỉ</label>
                            <input type="text" name="diaChi" class="form-control">
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                        <button type="submit" class="btn btn-success">Lưu</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Modal: Sửa khách hàng (AJAX) -->
    <div class="modal fade" id="formSuaKhachHang" tabindex="-1" aria-labelledby="formSuaKhachHangLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <form id="formSuaKh" method="post">
                    <div class="modal-header bg-warning text-dark">
                        <h5 class="modal-title" id="formSuaKhachHangLabel">
                            <i class="bi bi-pencil-square me-2"></i>Sửa thông tin khách hàng
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
                    </div>

                    <div class="modal-body">
                        <!-- mã KH dùng để gửi -->
                        <input type="hidden" name="maKH" id="edit_maKH">

                        <div class="mb-3">
                            <label class="form-label">Họ tên</label>
                            <input type="text" name="hoTen" id="edit_hoTen" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Số điện thoại</label>
                            <input type="text" name="soDienThoai" id="edit_soDienThoai" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" name="email" id="edit_email" class="form-control">
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Địa chỉ</label>
                            <input type="text" name="diaChi" id="edit_diaChi" class="form-control">
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                        <button type="submit" class="btn btn-warning">Lưu thay đổi</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <%@ include file="layout/footer.jsp" %>

    <!-- jQuery + Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        $(function() {
            $(document).on('click', '.btn-edit', function() {
                const btn = $(this);
                $('#edit_maKH').val(btn.data('makh'));
                $('#edit_hoTen').val(btn.data('hotenkh'));
                $('#edit_soDienThoai').val(btn.data('sdt'));
                $('#edit_email').val(btn.data('email'));
                $('#edit_diaChi').val(btn.data('diachi'));

                // show modal
                var myModal = new bootstrap.Modal(document.getElementById('formSuaKhachHang'), {backdrop: 'static'});
                myModal.show();
            });

            $('#formSuaKh').submit(function(e) {
                e.preventDefault();
                var $form = $(this);
                var data = $form.serialize(); 

                $.ajax({
                    url: '<%= request.getContextPath() %>/QL-Khachhang?action=update',
                    type: 'POST',
                    data: data,
                    dataType: 'json',
                    success: function(res) {
                        if (res.status === 'success') {
                            $('#formSuaKhachHang').modal('hide'); 
                            Swal.fire({
                                position: 'center',
                                icon: 'success',
                                title: res.message || 'Đã lưu',
                                showConfirmButton: false,
                                timer: 1400
                            }).then(() => {
                                location.reload();
                            });
                        } else {
                            Swal.fire({
                                icon: 'error',
                                title: 'Lỗi',
                                text: res.message || 'Cập nhật thất bại'
                            });
                        }
                    },
                    error: function(xhr, status, err) {
                        console.error(err);
                        Swal.fire({
                            icon: 'error',
                            title: 'Lỗi',
                            text: 'Có lỗi khi gửi yêu cầu, xem console'
                        });
                    }
                });
            });
        });
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
