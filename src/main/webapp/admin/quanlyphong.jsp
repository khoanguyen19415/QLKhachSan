<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Quản lý phòng - Sunshine Hotel</title>
        <%@ include file="layout/header.jsp" %>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/css/admin.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
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
                        <i class="bi bi-door-open me-2"></i>Quản lý phòng
                    </h3>
                    <hr>
                <div class="d-flex justify-content-between mb-3">
                    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalThemPhong">
                        <i class="bi bi-plus-circle me-1"></i> Thêm phòng
                    </button>

                    <!-- FORM TÌM KIẾM (gửi GET đến servlet) -->
                    <form class="d-flex" action="${pageContext.request.contextPath}/QL-Phong" method="get">
                        <input type="hidden" name="action" value="search"/>
                        <input type="text" name="keyword" class="form-control me-2" placeholder="Tìm tên hoặc loại phòng..." value="${param.keyword}">
                        <button class="btn btn-outline-primary" type="submit"><i class="bi bi-search"></i></button>
                    </form>
                </div>

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
                                    <td ><fmt:formatNumber value="${p.gia}" type="number" groupingUsed="true"/> đ / đêm</td>
                                    <td>${p.moTa}</td>
                                    <td>
                                        <span class="${p.trangThai eq 'trống' ? 'status-available' : 'status-unavailable'}"></span>
                                        ${p.trangThai}
                                    </td>
                                    <td>
                                        <a class="btn btn-sm btn-primary" href="QL-CTPhong?action=view&id=${p.maPhong}">
                                            <i class="bi bi-eye"></i>
                                        </a>
                                        <button class="btn btn-sm btn-warning btn-open-edit"
                                                data-id="${p.maPhong}"
                                                data-ten="<c:out value='${p.tenPhong}'/>"
                                                data-loai="<c:out value='${p.loaiPhong}'/>"
                                                data-gia="<c:out value='${p.gia}'/>"
                                                data-mota="<c:out value='${p.moTa}'/>"
                                                data-trangthai="<c:out value='${p.trangThai}'/>"
                                                >
                                            <i class="bi bi-pencil-square"></i>
                                        </button>

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

        <!--thêm-->
        <div class="modal fade" id="modalThemPhong" tabindex="-1" aria-labelledby="themPhongLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <form action="QL-Phong" method="post" id="formAddPhong">
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
                                        <option value="trống">Trống</option>
                                        <option value="Đã đặt">Đã đặt</option>
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

        <!--sửa-->
        <div class="modal fade" id="modalSuaPhong" tabindex="-1" aria-labelledby="suaPhongLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <form id="formEditPhong">
                        <!-- hidden action và maPhong -->
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="maPhong" id="editMaPhong">

                        <div class="modal-header">
                            <h5 class="modal-title fw-bold text-primary" id="suaPhongLabel">
                                <i class="bi bi-pencil-square me-2"></i>Sửa phòng
                            </h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>

                        <div class="modal-body">
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <label class="form-label fw-bold">Tên phòng</label>
                                    <input type="text" name="tenPhong" id="editTenPhong" class="form-control" required>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label fw-bold">Loại phòng</label>
                                    <select name="loaiPhong" id="editLoaiPhong" class="form-select" required>
                                        <option value="">-- Chọn loại phòng --</option>
                                        <option value="Standard">Standard</option>
                                        <option value="Deluxe">Deluxe</option>
                                        <option value="Suite">Suite</option>
                                        <option value="VIP">VIP</option>
                                    </select>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label fw-bold">Giá / Đêm</label>
                                    <input type="number" name="gia" id="editGia" class="form-control" min="0" required>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label fw-bold">Trạng thái</label>
                                    <select name="trangThai" id="editTrangThai" class="form-select">
                                        <option value="trống">trống</option>
                                        <option value="Đã đặt">Đã đặt</option>
                                        <option value="Bảo trì">Bảo trì</option>
                                    </select>
                                </div>
                                <div class="col-12">
                                    <label class="form-label fw-bold">Mô tả</label>
                                    <textarea name="moTa" id="editMoTa" class="form-control" rows="3"></textarea>
                                </div>
                            </div>
                        </div>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                            <button type="submit" class="btn btn-success" id="btnSaveEdit">
                                <i class="bi bi-save"></i> Lưu thay đổi
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <%@ include file="layout/footer.jsp" %>

        <script>
            $(document).ready(function () {


                $(document).on('click', '.btn-open-edit', function () {
                    const btn = $(this);
                    const id = btn.data('id');
                    const ten = btn.data('ten') || '';
                    const loai = btn.data('loai') || '';
                    const gia = btn.data('gia') || '';
                    const mota = btn.data('mota') || '';
                    const trangthai = btn.data('trangthai') || '';

                    $('#editMaPhong').val(id);
                    $('#editTenPhong').val(ten);
                    $('#editLoaiPhong').val(loai);
                    $('#editGia').val(gia);
                    $('#editMoTa').val(mota);
                    $('#editTrangThai').val(trangthai);

                 
                    var modal = new bootstrap.Modal(document.getElementById('modalSuaPhong'));
                    modal.show();
                });

              
                $('#formEditPhong').submit(function (e) {
                    e.preventDefault();

                
                    const formData = $(this).serialize(); 

                    
                    $('#btnSaveEdit').prop('disabled', true);

                    $.ajax({
                        url: '${pageContext.request.contextPath}/QL-Phong',
                        method: 'POST',
                        data: formData,
                        success: function (resp) {
                            Swal.fire({
                                position: 'center',
                                icon: 'success',
                                title: 'Cập nhật phòng thành công',
                                showConfirmButton: false,
                                timer: 1400
                            }).then(() => {
                                location.href = '${pageContext.request.contextPath}/QL-Phong?action=list';
                            });
                        },
                        error: function (xhr, status, err) {
                            console.error(status, err);
                            Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Cập nhật thất bại. Kiểm tra console.',
                                showConfirmButton: true
                            });
                        },
                        complete: function () {
                            $('#btnSaveEdit').prop('disabled', false);
                        }
                    });
                });
            });
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
