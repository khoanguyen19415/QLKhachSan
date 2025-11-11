package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.DatPhong;
import model.DatPhongDAO;
import model.ChiTietDatPhong;
import model.ChiTietDatPhongDAO;
import model.PhongDAO;

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
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "approve":
                updateStatus(request, response, "Đã duyệt", "Đã đặt");
                break;
            case "checkin":
                updateStatus(request, response, "Đang ở", "Đang ở");
                break;
            case "checkout":
                updateStatus(request, response, "Đã trả phòng", "Trống");
                break;
            case "cancel":
                updateStatus(request, response, "Hủy", "Trống");
                break;
            case "search":
                search(request, response);
                break;
            default:
                list(request, response);
        }
    }

    private void list(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = 1, size = 6;
        try {
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
        } catch (NumberFormatException ignored) {
        }

        int total = dpDAO.countAll();
        int totalPages = (int) Math.ceil((double) total / size);
        int offset = (page - 1) * size;

        List<DatPhong> ds = dpDAO.getPaged(offset, size);

        request.setAttribute("dsDatPhong", ds);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", size);
        request.setAttribute("totalItems", total);

        request.getRequestDispatcher("/admin/quanlydatphong.jsp").forward(request, response);
    }

    private void search(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        // Implement search in DAO if needed; fallback: list all
        list(request, response);
    }

    private void updateStatus(HttpServletRequest request, HttpServletResponse response,
            String trangThaiDon, String trangThaiPhong)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            DatPhong dp = dpDAO.getById(id);
            if (dp == null) {
                request.getSession().setAttribute("error", "Đơn không tồn tại");
                list(request, response);
                return;
            }

            // 1) Cập nhật DatPhong + ChiTietDatPhong (đã đồng bộ trong DAO)
            boolean ok1 = dpDAO.updateStatus(id, trangThaiDon);

            // ✅ Gọi DAO cập nhật từng ChiTietDatPhong để đồng bộ trạng thái phòng
            ChiTietDatPhongDAO ctdpDAO = new ChiTietDatPhongDAO();

            // 2) Cập nhật status của bảng Phong cho từng phòng trong đơn
            boolean ok2 = true;
            List<ChiTietDatPhong> chiTietList = dp.getChiTiet();
            if (chiTietList != null && !chiTietList.isEmpty()) {
                for (ChiTietDatPhong c : chiTietList) {
                    // 🔥 Gọi đúng hàm này để trigger đồng bộ Phòng + Đơn
                    boolean updated = ctdpDAO.updateTrangThai(c.getMaCTDP(), trangThaiDon);
                    if (!updated) {
                        ok2 = false;
                    }
                }
            } else {
                ok2 = false;
            }

            if (ok1 && ok2) {
                request.getSession().setAttribute("success", "Cập nhật trạng thái thành công!");
            }  else {
                request.getSession().setAttribute("error", "Không thể cập nhật trạng thái.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Dữ liệu không hợp lệ");
        }
        list(request, response);

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
}
