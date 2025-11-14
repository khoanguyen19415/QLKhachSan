package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.HoaDon;
import model.HoaDonChiTiet;
import model.HoaDonDAO;
import model.HoaDonChiTietDAO;

@WebServlet(name = "QuanLyHoaDonServlet", urlPatterns = {"/QL-HoaDon"})
public class QuanLyHoaDonServlet extends HttpServlet {

    private HoaDonDAO hoaDonDAO;
    private HoaDonChiTietDAO hdctDAO;

    @Override
    public void init() throws ServletException {
        hoaDonDAO = new HoaDonDAO();
        hdctDAO = new HoaDonChiTietDAO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "view";
        }

        switch (action) {

            // ===== XEM HÓA ĐƠN =====
            case "view":
                showHoaDon(request, response);
                break;
            case "xuatHoaDon":
                thanhToanHoaDon(request, response);
                break;
            default:
                showHoaDon(request, response);
                break;
        }
    }

    private void showHoaDon(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");

        if (idStr == null || idStr.trim().isEmpty()) {
            request.setAttribute("error", "Không tìm thấy mã hóa đơn");
            request.getRequestDispatcher("/admin/hoadon.jsp").forward(request, response);
            return;
        }

        int maDP = Integer.parseInt(idStr);

        HoaDon hd = hoaDonDAO.getByMaDatPhong(maDP);

        if (hd == null) {
            request.setAttribute("error", "Không tìm thấy hóa đơn");
            request.getRequestDispatcher("/admin/hoadon.jsp").forward(request, response);
            return;
        }

        List<HoaDonChiTiet> chiTiet = hdctDAO.getByHoaDon(hd.getMaHoaDon());

        request.setAttribute("hoaDon", hd);
        request.setAttribute("chiTiet", chiTiet);

        request.getRequestDispatcher("/admin/hoadon.jsp").forward(request, response);
    }

    private void thanhToanHoaDon(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("maDatPhong");

        if (id == null || id.trim().isEmpty()) {
            HttpSession session = request.getSession();
            session.setAttribute("error", "Không thể xuất hóa đơn: thiếu mã đặt phòng!");
            response.sendRedirect(request.getContextPath() + "/QL-HoaDon?action=view&id=0");
            return;
        }

        int maDP = Integer.parseInt(id);

        boolean ok = hoaDonDAO.updateThanhToan(maDP);
        HoaDon hd = hoaDonDAO.getByMaDatPhong(maDP);
        new HoaDonDAO().generateChiTietHoaDon(maDP, hd.getMaHoaDon());

        HttpSession session = request.getSession();

        if (ok) {
            session.setAttribute("success", "Xuất hóa đơn thành công!");
        } else {
            session.setAttribute("error", "Xuất hóa đơn thất bại!");
        }

        response.sendRedirect(request.getContextPath() + "/QL-HoaDon?action=view&id=" + maDP);
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
