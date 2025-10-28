<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Chi tiết phòng</title>
        <link rel="stylesheet" href="admin/css/admin.css">
        <%@ include file="layout/header.jsp" %>

        <!-- CSS riêng cho admin -->
        <link rel="stylesheet" href="<%= request.getContextPath()%>/css/admin.css">
    </head>
    <div class="container">
        <div class="card">
            <div class="card-header">
                <h4>Chi tiết phòng số ${maPhong}</h4>
            </div>

            <div class="card-body">
                <!-- Danh sách tiện nghi -->
                <h5><i class="bi bi-door-open"></i> Danh sách tiện nghi</h5>
                <table class="table table-bordered table-striped table-hover mt-3">
                    <thead class="text-center">
                        <tr>
                            <th>Mã CTP</th>
                            <th>Tiện nghi</th>
                            <th>Mô tả</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="ct" items="${dsChiTiet}">
                            <tr>
                                <td class="text-center">${ct.maCTP}</td>
                                <td>${ct.tienNghi}</td>
                                <td>${ct.moTa}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <!-- Hình ảnh phòng -->
                <h5 class="mt-4"><i class="bi bi-images"></i> Hình ảnh phòng</h5>
                <div class="row mt-3">
                    <c:if test="${empty listAnh}">
                        <div class="col-12 text-center text-muted p-4">
                            <img src="https://via.placeholder.com/300x200?text=Chưa+có+ảnh" 
                                 alt="Chưa có ảnh" 
                                 class="no-image img-fluid mb-2">
                            <p>Hiện chưa có ảnh cho phòng này</p>
                        </div>
                    </c:if>

                    <c:forEach var="a" items="${listAnh}">
                        <div class="col-md-3 text-center mb-3">
                            <img src="${a.url}" alt="Ảnh phòng" class="img-fluid rounded shadow-sm">
                            <p class="mt-2">${a.moTa}</p>
                        </div>
                    </c:forEach>
                </div>

                <!-- Nút quay lại -->
                <a href="QL-Phong" class="btn btn-secondary mt-3">
                    <i class="bi bi-arrow-left"></i> Quay lại danh sách
                </a>
            </div>
        </div>
    </div>
</body>
</html>
