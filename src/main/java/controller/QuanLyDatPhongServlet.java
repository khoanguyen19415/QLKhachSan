package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.DatPhongDAO;
import model.DatPhong;
import model.PhongDAO;

/**
 * Servlet quản lý đặt phòng: list, duyệt, từ chối, nhận, trả
 */
@WebServlet(name = "QuanLyDatPhongServlet", urlPatterns = {"/QL-datphong"})
public class QuanLyDatPhongServlet extends HttpServlet {

    private DatPhongDAO dpDAO;
    private PhongDAO phongDAO;

    @Override
    public void init() throws ServletException {
        dpDAO = new DatPhongDAO();
        phongDAO = new PhongDAO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":
                list(request, response);
                break;
            case "approve":
                approve(request, response);
                break;
            case "reject":
                reject(request, response);
                break;
            case "checkin":
                checkin(request, response);
                break;
            case "checkout":
                checkout(request, response);
                break;
            default:
                list(request, response);
                break;
        }
    }

    // GET/POST forward to processRequest
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { processRequest(request, response); }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { processRequest(request, response); }

    private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var ds = dpDAO.getAll();
        request.setAttribute("dsDatPhong", ds);
        request.getRequestDispatcher("/admin/quanlydatphong.jsp").forward(request, response);
    }

    private void approve(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        try {
            int id = Integer.parseInt(idStr);
            DatPhong dp = dpDAO.getById(id);
            if (dp == null) {
                request.setAttribute("error", "Đơn không tồn tại");
                list(request, response);
                return;
            }

            boolean okDp = dpDAO.updateStatus(id, "Đã xác nhận");
            boolean okPhong = phongDAO.updateStatus(dp.getMaPhong(), "Đã đặt");

            if (okDp && okPhong) {
                request.setAttribute("success", "Duyệt đơn thành công (cập nhật trạng thái phòng)");
            } else if (okDp) {
                request.setAttribute("success", "Duyệt đơn thành công (chưa cập nhật trạng thái phòng)");
            } else {
                request.setAttribute("error", "Duyệt đơn thất bại");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Dữ liệu không hợp lệ");
        }
        list(request, response);
    }

    private void reject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        try {
            int id = Integer.parseInt(idStr);
            DatPhong dp = dpDAO.getById(id);
            if (dp == null) {
                request.setAttribute("error", "Đơn không tồn tại");
                list(request, response);
                return;
            }

            boolean okDp = dpDAO.updateStatus(id, "Đã hủy");
            // khi huỷ, ta trả trạng thái phòng về "Còn trống" (tuỳ bạn muốn hay giữ nguyên)
            boolean okPhong = phongDAO.updateStatus(dp.getMaPhong(), "Còn trống");

            if (okDp) {
                request.setAttribute("success", "Hủy đơn thành công");
            } else {
                request.setAttribute("error", "Hủy đơn thất bại");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Dữ liệu không hợp lệ");
        }
        list(request, response);
    }

    private void checkin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        try {
            int id = Integer.parseInt(idStr);
            DatPhong dp = dpDAO.getById(id);
            if (dp == null) {
                request.setAttribute("error", "Đơn không tồn tại");
                list(request, response);
                return;
            }

            boolean okDp = dpDAO.updateStatus(id, "Đã nhận");
            boolean okPhong = phongDAO.updateStatus(dp.getMaPhong(), "Đang ở");

            if (okDp && okPhong) {
                request.setAttribute("success", "Khách đã nhận phòng");
            } else if (okDp) {
                request.setAttribute("success", "Cập nhật đơn thành công (phòng chưa cập nhật)");
            } else {
                request.setAttribute("error", "Không thể cập nhật trạng thái nhận phòng");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Dữ liệu không hợp lệ");
        }
        list(request, response);
    }

    private void checkout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        try {
            int id = Integer.parseInt(idStr);
            DatPhong dp = dpDAO.getById(id);
            if (dp == null) {
                request.setAttribute("error", "Đơn không tồn tại");
                list(request, response);
                return;
            }

            boolean okDp = dpDAO.updateStatus(id, "Đã trả phòng");
            boolean okPhong = phongDAO.updateStatus(dp.getMaPhong(), "Còn trống");

            if (okDp && okPhong) {
                request.setAttribute("success", "Khách đã trả phòng và phòng được chuyển về trạng thái Còn trống");
            } else if (okDp) {
                request.setAttribute("success", "Cập nhật đơn thành công (phòng chưa cập nhật)");
            } else {
                request.setAttribute("error", "Không thể cập nhật trạng thái trả phòng");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Dữ liệu không hợp lệ");
        }
        list(request, response);
    }
}
