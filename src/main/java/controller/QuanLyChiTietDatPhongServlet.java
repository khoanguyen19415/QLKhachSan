package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.ChiTietDatPhong;
import model.ChiTietDatPhongDAO;
import model.DatPhong;
import model.DatPhongDAO;

@WebServlet(name = "QuanLyChiTietDatPhongServlet", urlPatterns = {"/QL-chitietdatphong"})
public class QuanLyChiTietDatPhongServlet extends HttpServlet {

    private ChiTietDatPhongDAO ctdpDAO;

    @Override
    public void init() throws ServletException {
        ctdpDAO = new ChiTietDatPhongDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        String action = request.getParameter("action");
        String idStr = request.getParameter("id");

        if (action == null || idStr == null || idStr.trim().isEmpty()) {
            session.setAttribute("error", "Không xác định được phòng cần thao tác!");
            response.sendRedirect(request.getContextPath() + "/QL-datphong");
            return;
        }

        try {
            int id = Integer.parseInt(idStr.trim());
            boolean success = false;

            switch (action) {
                case "approve":
                    // Cập nhật đơn này thành "Đã duyệt"
                    success = ctdpDAO.updateTrangThai(id, "Đã duyệt");

                    if (success) {
                        // Lấy thông tin chi tiết đơn vừa duyệt
                        ChiTietDatPhong ctdp = ctdpDAO.getById(id);

                        if (ctdp != null) {
                            // Lấy thông tin đơn gốc
                            DatPhongDAO dpDAO = new DatPhongDAO();
                            DatPhong dp = dpDAO.getById(ctdp.getMaDatPhong());

                            // ✅ Gọi hàm tự động hủy các đơn khác trùng phòng + thời gian
                            if (dp != null) {
                                ctdpDAO.huyDonTrungPhong(
                                        ctdp.getMaPhong(),
                                        new java.sql.Date(dp.getNgayNhan().getTime()),
                                        new java.sql.Date(dp.getNgayTra().getTime()),
                                        id
                                );

                            }
                        }

                        session.setAttribute("success", "Đã duyệt phòng thành công! Các đơn trùng phòng đã bị hủy tự động.");
                    } else {
                        session.setAttribute("error", "Không thể duyệt phòng!");
                    }
                    break;
                case "cancel":
                    success = ctdpDAO.updateTrangThai(id, "Hủy");
                    break;
                case "checkin":
                    success = ctdpDAO.updateTrangThai(id, "Đang ở");
                    break;
                case "checkout":
                    success = ctdpDAO.updateTrangThai(id, "Đã trả phòng");
                    break;
                default:
                    session.setAttribute("error", "Hành động không hợp lệ!");
                    response.sendRedirect(request.getContextPath() + "/QL-datphong");
                    return;
            }

            if (success) {
                session.setAttribute("success", "Cập nhật trạng thái phòng thành công!");
            } else {
                session.setAttribute("error", "Không thể cập nhật trạng thái phòng!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Lỗi xử lý dữ liệu: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/QL-datphong?action=list");
    }

    @Override
    public String getServletInfo() {
        return "Quản lý chi tiết đặt phòng (duyệt / hủy / nhận / trả)";
    }
}
