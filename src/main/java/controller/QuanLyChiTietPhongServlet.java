/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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
import model.PhongAnhDAO;
import model.PhongAnh;

/**
 *
 * @author ADMIN
 */
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

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "view";
        }

        switch (action) {
            case "view":
                xemChiTietPhong(request, response);
                break;
            case "update":
                capNhatChiTietPhong(request, response);
                break;
            case "add":
                themChiTietPhong(request, response);
            default:
                response.sendRedirect("QL-Phong");
                break;
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void xemChiTietPhong(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int maPhong = Integer.parseInt(request.getParameter("id"));

        // Lấy danh sách tiện nghi của phòng
        List<ChiTietPhong> dsChiTiet = ctDAO.getByPhong(maPhong);

        // Lấy danh sách ảnh của phòng
        List<PhongAnh> dsAnh = anhDAO.getAnhTheoPhong(maPhong);

        // Gửi sang JSP
        request.setAttribute("dsChiTiet", dsChiTiet);
        request.setAttribute("dsAnh", dsAnh);
        request.setAttribute("maPhong", maPhong);

        request.getRequestDispatcher("/admin/quanlychitietphong.jsp").forward(request, response);

    }

    private void capNhatChiTietPhong(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        try {
            int maCTP = Integer.parseInt(request.getParameter("maCTP"));
            int maPhong = Integer.parseInt(request.getParameter("maPhong"));
            String tienNghi = request.getParameter("tienNghi");
            String moTa = request.getParameter("moTa");

            // 1) Xử lý delete các ảnh được đánh dấu
            String deletedAnhParam = request.getParameter("deletedAnh"); // "3,7,9" hoặc ""
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
                                // không quan trọng nếu xóa file thất bại, nhưng log
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
                // lưu đường dẫn (đúng format bạn đang dùng: "img/filename")
                anhDAO.themAnhChoPhong(maPhong, "img/" + fileName);
            }

            // 3) Cập nhật tiện nghi + mô tả (bắt buộc set MaCTP)
            ChiTietPhong ctp = new ChiTietPhong();
            ctp.setMaCTP(maCTP);
            ctp.setMaPhong(maPhong);
            ctp.setTienNghi(tienNghi);
            ctp.setMoTa(moTa);
            ctDAO.updateChiTietPhong(ctp);

            response.getWriter().write("{\"status\":\"success\"}");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.getWriter().write("{\"status\":\"error\"}");
        }
    }

    private void themChiTietPhong(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int maPhong = Integer.parseInt(request.getParameter("maPhong"));
        String tienNghi = request.getParameter("tienNghi");
        String moTa = request.getParameter("moTa");

        ChiTietPhong newCtp = new ChiTietPhong();
        newCtp.setMaPhong(maPhong);
        newCtp.setTienNghi(tienNghi);
        newCtp.setMoTa(moTa);
        int newId = ctDAO.insertChiTietPhong(newCtp);

        // nếu upload ảnh (Part "anh") xử lý lưu file và anhDAO.themAnhChoPhong(maPhong, "img/..")
        // trả JSON:
        if (newId > 0) {
            response.getWriter().write("{\"status\":\"success\"}");
        } else {
            response.getWriter().write("{\"status\":\"error\"}");
        }
    }

}
