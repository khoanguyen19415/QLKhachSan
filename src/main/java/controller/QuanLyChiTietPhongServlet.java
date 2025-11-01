package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.ChiTietPhong;
import model.ChiTietPhongDAO;
import model.PhongAnh;
import model.PhongAnhDAO;

@WebServlet(name = "QuanLyChiTietPhongServlet", urlPatterns = {"/QL-CTPhong"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class QuanLyChiTietPhongServlet extends HttpServlet {

    private ChiTietPhongDAO ctDAO;
    private PhongAnhDAO anhDAO;

    @Override
    public void init() throws ServletException {
        ctDAO = new ChiTietPhongDAO();
        anhDAO = new PhongAnhDAO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Thiết lập charset
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "view";

        switch (action) {
            case "view":
                xemChiTietPhong(request, response);
                break;
            case "add":
                xuLyThemChiTiet(request, response);
                break;
            case "update":
                capNhatChiTietPhong(request, response);
                break;
            default:
                // nếu không hợp lệ, quay về danh sách phòng chính
                response.sendRedirect("QL-Phong");
                break;
        }
    }

    // ---------------- Http method ----------------
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
    @Override
    public String getServletInfo() {
        return "Servlet quản lý chi tiết phòng";
    }

    // ---------------- Các hành động ----------------

    /**
     * Hiển thị chi tiết phòng (tiện nghi + ảnh)
     */
    private void xemChiTietPhong(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        int maPhong = 0;
        try {
            maPhong = Integer.parseInt(idParam);
        } catch (Exception e) {
            // nếu id không hợp lệ, redirect về danh sách phòng
            response.sendRedirect("QL-Phong");
            return;
        }

        List<ChiTietPhong> dsChiTiet = ctDAO.getByPhong(maPhong);
        List<PhongAnh> dsAnh = anhDAO.getAnhTheoPhong(maPhong);

        request.setAttribute("dsChiTiet", dsChiTiet);
        request.setAttribute("dsAnh", dsAnh);
        request.setAttribute("maPhong", maPhong);

        request.getRequestDispatcher("/admin/quanlychitietphong.jsp").forward(request, response);
    }

    /**
     * Xử lý thêm tiện nghi (KHÔNG xử lý ảnh) — style giống QuanLyPhongServlet:
     * - Nếu thành công set request attribute "success"
     * - Nếu thất bại set request attribute "error"
     * - Forward về "/QL-CTPhong?action=view&id=<maPhong>"
     */
    private void xuLyThemChiTiet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String maPhongStr = request.getParameter("maPhong");
            String tienNghi = request.getParameter("tienNghi");
            String moTa = request.getParameter("moTa");

            if (maPhongStr == null || maPhongStr.trim().isEmpty()) {
                request.setAttribute("error", "Không xác định mã phòng.");
                request.getRequestDispatcher("/QL-CTPhong?action=view&id=" + maPhongStr).forward(request, response);
                return;
            }

            int maPhong = Integer.parseInt(maPhongStr);

            // validate tối thiểu (bạn có thể mở rộng)
            if (tienNghi == null || tienNghi.trim().isEmpty()) {
                request.setAttribute("error", "Tiện nghi không được để trống.");
                request.getRequestDispatcher("/QL-CTPhong?action=view&id=" + maPhong).forward(request, response);
                return;
            }

            ChiTietPhong ct = new ChiTietPhong();
            ct.setMaPhong(maPhong);
            ct.setTienNghi(tienNghi);
            ct.setMoTa(moTa);

            int newId = ctDAO.insertChiTietPhong(ct); // trả về id mới hoặc -1
            if (newId > 0) {
                request.setAttribute("success", "Thêm chi tiết phòng thành công");
            } else {
                request.setAttribute("error", "Thêm chi tiết phòng thất bại");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Lỗi khi thêm: " + ex.getMessage());
        }

        // forward về trang xem để hiển thị thông báo (giống style của bạn)
        String maPhongForw = request.getParameter("maPhong");
        if (maPhongForw == null) maPhongForw = "0";
        request.getRequestDispatcher("/QL-CTPhong?action=view&id=" + maPhongForw).forward(request, response);
    }

    private void capNhatChiTietPhong(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // giữ nguyên: dùng UTF-8 và trả JSON
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        try {
            String maCTPStr = request.getParameter("maCTP");
            String maPhongStr = request.getParameter("maPhong");
            int maCTP = (maCTPStr != null && !maCTPStr.isEmpty()) ? Integer.parseInt(maCTPStr) : 0;
            int maPhong = Integer.parseInt(maPhongStr);

            String tienNghi = request.getParameter("tienNghi");
            String moTa = request.getParameter("moTa");

            // 1) Xử lý delete các ảnh được đánh dấu (hidden input deletedAnh = "3,7,9")
            String deletedAnhParam = request.getParameter("deletedAnh");
            if (deletedAnhParam != null && !deletedAnhParam.trim().isEmpty()) {
                String[] ids = deletedAnhParam.split(",");
                for (String sId : ids) {
                    try {
                        int id = Integer.parseInt(sId.trim());
                        PhongAnh pa = anhDAO.getById(id);
                        if (pa != null) {
                            // xóa file trên filesystem (nếu đường dẫn là relative như "img/xxx.jpg")
                            try {
                                String appPath = request.getServletContext().getRealPath("");
                                String filePath = appPath + File.separator + pa.getDuongDanAnh().replace("/", File.separator);
                                File f = new File(filePath);
                                if (f.exists()) {
                                    f.delete();
                                }
                            } catch (Exception exFile) {
                                exFile.printStackTrace();
                            }
                            // xóa bản ghi DB
                            anhDAO.delete(id);
                        }
                    } catch (NumberFormatException nfe) {
                        // ignore invalid id
                    }
                }
            }

            // 2) Xử lý upload ảnh mới (nếu có)
            Part filePart = request.getPart("anh");
            if (filePart != null && filePart.getSize() > 0) {
                String appPath = request.getServletContext().getRealPath("");
                String savePath = appPath + File.separator + "img" + File.separator;
                File fileSaveDir = new File(savePath);
                if (!fileSaveDir.exists()) {
                    fileSaveDir.mkdirs();
                }
                String fileName = System.currentTimeMillis() + "_" + Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                filePart.write(savePath + fileName);
                // lưu đường dẫn (định dạng giống project của bạn): "img/filename"
                anhDAO.themAnhChoPhong(maPhong, "img/" + fileName);
            }

            // 3) Cập nhật tiện nghi + mô tả (cần MaCTP để update)
            if (maCTP > 0) {
                ChiTietPhong ctp = new ChiTietPhong();
                ctp.setMaCTP(maCTP);
                ctp.setMaPhong(maPhong);
                ctp.setTienNghi(tienNghi);
                ctp.setMoTa(moTa);
                ctDAO.updateChiTietPhong(ctp);
            } else {
                // Nếu không có maCTP (trường hợp người dùng mở modal "Thêm" nhưng gửi action=update),
                // fallback: thêm mới
                ChiTietPhong ctp = new ChiTietPhong();
                ctp.setMaPhong(maPhong);
                ctp.setTienNghi(tienNghi);
                ctp.setMoTa(moTa);
                ctDAO.insertChiTietPhong(ctp);
            }

            response.getWriter().write("{\"status\":\"success\"}");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.getWriter().write("{\"status\":\"error\"}");
        }
    }
}
