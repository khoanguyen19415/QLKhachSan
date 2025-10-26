<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý đặt phòng - Sunshine Hotel</title>
    <%@ include file="layout/header.jsp" %>

    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/admin.css">
</head>
<body>
<%@ include file="layout/nav.jsp" %>

<div class="container-fluid">
  <div class="row">
    <!-- Sidebar -->
    <div class="col-md-2 bg-light border-end p-0">
      <%@ include file="layout/sidebar.jsp" %>
    </div>

    <!-- Nội dung -->
    <div class="col-md-10 mt-4">
      <h3 class="fw-bold text-primary mb-3">
        <i class="bi bi-journal-check me-2"></i>Quản lý đặt phòng
      </h3>
      <hr>

      <!-- Bộ lọc -->
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

      <!-- Bảng -->
      <table class="table table-hover table-bordered align-middle">
        <thead class="table-primary text-center">
          <tr>
            <th>Mã đơn</th>
            <th>Tên khách hàng</th>
            <th>Phòng</th>
            <th>Ngày nhận</th>
            <th>Ngày trả</th>
            <th>Trạng thái</th>
            <th>Thao tác</th>
          </tr>
        </thead>
        <tbody>
          <!-- Đơn đang chờ -->
          <tr>
            <td>DP001</td>
            <td>Nguyễn Văn A</td>
            <td>P002</td>
            <td>22/10/2025</td>
            <td>24/10/2025</td>
            <td><span class="badge bg-warning text-dark">Chờ duyệt</span></td>
            <td class="text-center">
              <button class="btn btn-sm btn-success"><i class="bi bi-check-circle"></i> Duyệt</button>
              <button class="btn btn-sm btn-danger"><i class="bi bi-x-circle"></i> Từ chối</button>
            </td>
          </tr>

          <!-- Đơn đã xác nhận -->
          <tr>
            <td>DP002</td>
            <td>Trần Thị B</td>
            <td>P010</td>
            <td>20/10/2025</td>
            <td>22/10/2025</td>
            <td><span class="badge bg-success">Đã xác nhận</span></td>
            <td class="text-center">
              <button class="btn btn-sm btn-secondary" disabled><i class="bi bi-lock"></i></button>
            </td>
          </tr>

          <!-- Đơn đã hủy -->
          <tr>
            <td>DP003</td>
            <td>Phạm Văn C</td>
            <td>P008</td>
            <td>15/10/2025</td>
            <td>17/10/2025</td>
            <td><span class="badge bg-danger">Đã hủy</span></td>
            <td class="text-center">
              <button class="btn btn-sm btn-secondary" disabled><i class="bi bi-lock"></i></button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>

<%@ include file="layout/footer.jsp" %>
</body>
</html>
