<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Chi tiết phòng</title>
        <%@ include file="layout/header.jsp" %>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/css/admin.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>  
    </head>
    <body>
        <%@ include file="../thongbao.jsp" %>
        <%@ include file="layout/nav.jsp" %>

        <div class="container mt-4">
            <div class="card">
                <div class="card-header">
                    <h4>Chi tiết phòng số ${maPhong}</h4>
                </div>

                <div class="card-body">
                    <!-- Danh sách tiện nghi: nút Sửa/Thêm đẩy sát phải -->
                    <div class="title-row mb-3">
                        <h3 class="mb-0 bi bi-door-open"> Danh sách tiện nghi</h3>
                        <div class="title-actions">
                            <button id="btnShowEdit" class="btn btn-sm btn-primary">
                                <i class="bi bi-pencil-square"></i> Sửa / Thêm chi tiết phòng
                            </button>
                        </div>
                    </div>

                    <table class="table table-bordered table-striped table-hover mt-3">
                        <thead class="text-center">
                            <tr>
                                <th>Mã CTP</th>
                                <th>Tiện nghi</th>
                                <th>Mô tả</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="ct" items="${dsChiTiet}">
                                <tr>
                                    <td class="text-center">${ct.maCTP}</td>
                                    <td>${ct.tienNghi}</td>
                                    <td>${ct.moTa}</td>
                                    <td class="text-center">
                                        <button type="button"
                                                class="btn btn-sm btn-primary btn-edit"
                                                data-mactp="${ct.maCTP}"
                                                data-tiennghi="${fn:escapeXml(ct.tienNghi)}"
                                                data-mota="${fn:escapeXml(ct.moTa)}">
                                            <i class="bi bi-pencil-square"></i> Sửa
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <!-- Hình ảnh phòng -->
                    <h5 class="mt-4"><i class="bi bi-images"></i> Hình ảnh phòng</h5>
                    <div class="row mt-3">
                        <c:if test="${empty dsAnh}">
                            <div class="col-12 text-center text-muted p-4">
                                <img src="https://via.placeholder.com/300x200?text=Chưa+có+ảnh"
                                     alt="Chưa có ảnh"
                                     class="no-image img-fluid mb-2">
                                <p>Hiện chưa có ảnh cho phòng này</p>
                            </div>
                        </c:if>

                        <c:forEach var="a" items="${dsAnh}">
                            <div class="col-md-3 text-center mb-3">
                                <img src="${a.duongDanAnh}" alt="Ảnh phòng" class="img-fluid rounded shadow-sm">
                            </div>
                        </c:forEach>
                    </div>

                    <!-- Nút quay lại -->
                    <a href="${pageContext.request.contextPath}/QL-Phong" class="btn btn-secondary mt-3">
                        <i class="bi bi-arrow-left"></i> Quay lại danh sách
                    </a>
                </div>
            </div>
        </div>

        <!-- Popup sửa/ thêm chi tiết phòng -->
        <div class="overlay" id="overlay">
            <div class="edit-popup">
                <span class="close-btn" id="btnClose">&times;</span>
                <h5><i class="bi bi-pencil-square"></i> Sửa / Thêm chi tiết phòng</h5>

                <form id="formEditCTP" enctype="multipart/form-data" method="post"
                      action="${pageContext.request.contextPath}/QL-CTPhong?action=update"
                      accept-charset="UTF-8">
                    <!-- nếu maCTP rỗng => sẽ thực hiện action=add (JS set) -->
                    <input type="hidden" name="maCTP" id="maCTP" value="">
                    <input type="hidden" name="maPhong" id="maPhong" value="${maPhong}">
                    <input type="hidden" name="deletedAnh" id="deletedAnh" value="">

                    <div class="mb-3">
                        <label class="form-label">Tiện nghi</label>
                        <input type="text" name="tienNghi" id="tienNghi" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Mô tả</label>
                        <textarea name="moTa" id="moTa" class="form-control" rows="3"></textarea>
                    </div>

                    <!-- Hiện các ảnh hiện có (có thể đánh dấu xóa) -->
                    <div class="mb-3">
                        <label class="form-label">Ảnh hiện có</label>
                        <div id="current-images" class="d-flex gap-2 flex-wrap">
                            <c:forEach var="a" items="${dsAnh}">
                                <div class="img-card" style="position:relative; width:120px;">
                                    <img src="${a.duongDanAnh}" data-maanh="${a.maAnh}" class="img-fluid rounded existing-img"
                                         style="width:120px; height:80px; object-fit:cover; border:1px solid #ddd;">
                                    <button type="button" class="btn btn-sm btn-outline-danger btn-delete-img"
                                            data-maanh="${a.maAnh}" style="position:absolute; top:6px; right:6px;">
                                        <i class="bi bi-trash"></i>
                                    </button>
                                    <div class="badge bg-danger text-white d-none deleted-badge" style="position:absolute; left:6px; top:6px;">Đã xóa</div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Ảnh phòng (tùy chọn)</label>
                        <input type="file" name="anh" class="form-control" accept="image/*">
                        <img id="preview" class="img-preview d-none" alt="Preview">
                    </div>

                    <button type="submit" class="btn btn-success w-100">
                        <i class="bi bi-save"></i> Lưu thay đổi
                    </button>
                </form>
            </div>
        </div>

        <%@ include file="../thongbao.jsp" %>
        <%@ include file="layout/footer.jsp" %>

        <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
        <script>
            $(function () {
                $("#btnShowEdit").click(function () {
                    $("#maCTP").val("");
                    $("#tienNghi").val("");
                    $("#moTa").val("");
                    $("#deletedAnh").val("");
                    $("#current-images .img-card").removeClass("marked-delete").find(".deleted-badge").addClass("d-none");
                    $("#current-images .btn-delete-img").removeClass("btn-danger").addClass("btn-outline-danger");
                    $("#preview").addClass("d-none").attr("src", "");
                    // mặc định form sẽ gửi "add" (JS sẽ set khi submit)
                    $("#overlay").fadeIn(180).css("display", "flex");
                });
                $(document).on("click", ".btn-edit", function () {
                    const mactp = $(this).data("mactp");
                    const tiennghi = $(this).data("tiennghi") || "";
                    const mota = $(this).data("mota") || "";

                    $("#maCTP").val(mactp);
                    $("#tienNghi").val(tiennghi);
                    $("#moTa").val(mota);
                    $("#deletedAnh").val("");
                    $("#current-images .img-card").removeClass("marked-delete").find(".deleted-badge").addClass("d-none");
                    $("#current-images .btn-delete-img").removeClass("btn-danger").addClass("btn-outline-danger");

                    $("#overlay").fadeIn(180).css("display", "flex");
                });

                $("#btnClose, #overlay").click(function (e) {
                    if (e.target.id === "overlay" || e.target.id === "btnClose") {
                        $("#overlay").fadeOut(180);
                    }
                });


                $("input[name='anh']").on("change", function (e) {
                    const f = e.target.files[0];
                    if (f) {
                        const rd = new FileReader();
                        rd.onload = function (ev) {
                            $("#preview").removeClass("d-none").attr("src", ev.target.result);
                        };
                        rd.readAsDataURL(f);
                    } else {
                        $("#preview").addClass("d-none").attr("src", "");
                    }
                });

 
                let deletedIds = [];
                $(document).on("click", ".btn-delete-img", function (e) {
                    e.stopPropagation();
                    const btn = $(this);
                    const id = String(btn.data("maanh"));
                    const card = btn.closest(".img-card");
                    const badge = card.find(".deleted-badge");
                    if (card.hasClass("marked-delete")) {
                        card.removeClass("marked-delete");
                        badge.addClass("d-none");
                        deletedIds = deletedIds.filter(x => x !== id);
                        btn.removeClass("btn-danger").addClass("btn-outline-danger");
                    } else {
                        card.addClass("marked-delete");
                        badge.removeClass("d-none");
                        if (!deletedIds.includes(id))
                            deletedIds.push(id);
                        btn.removeClass("btn-outline-danger").addClass("btn-danger");
                    }
                    $("#deletedAnh").val(deletedIds.join(","));
                });

                $("#formEditCTP").submit(function (e) {
                    e.preventDefault();
                    const form = this;
                    const fd = new FormData(form);
                    const mactp = $("#maCTP").val();
                    let url;
                    if (!mactp || mactp.trim() === "") {
                        url = "${pageContext.request.contextPath}/QL-CTPhong?action=add";
                    } else {
                        url = "${pageContext.request.contextPath}/QL-CTPhong?action=update";
                    }

                    $.ajax({
                        url: url,
                        type: "POST",
                        data: fd,
                        contentType: false,
                        processData: false,
                        success: function (res) {
                            let msg = "Cập nhật thành công!";
                            try {
                                var json = (typeof res === "string") ? JSON.parse(res) : res;
                                if (json) {
                                    if (json.message && json.message.trim() !== "")
                                        msg = json.message;
                                    if (json.status && json.status !== "success") {
                                        Swal.fire({
                                            position: "center",
                                            icon: "error",
                                            title: json.message || "Cập nhật thất bại!",
                                            showConfirmButton: false,
                                            timer: 2000
                                        });
                                        return;
                                    }
                                }
                            } catch (err) {
                                console.warn("Response is not JSON, fallback to reload.");
                            }

                            Swal.fire({
                                position: "center",
                                icon: "success",
                                title: msg,
                                showConfirmButton: false,
                                timer: 1600
                            });

                            setTimeout(function () {
                                location.reload();
                            }, 1700);
                        }
                    });
                });
            });
        </script>
    </body>
</html>
