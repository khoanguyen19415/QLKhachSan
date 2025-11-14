/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ThongKeDAO;

/**
 *
 * @author linhdhdi4
 */
@WebServlet(name = "ThongKeServlet", urlPatterns = {"/Thong-Ke"})
public class ThongKeServlet extends HttpServlet {

    private ThongKeDAO thongKeDAO;

    @Override
    public void init() throws ServletException {
        thongKeDAO = new ThongKeDAO();
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
        String action = request.getParameter("action");
        if (action == null) {
            action = "overview";
        }
        switch (action) {
            case "chart":
                handleOverview(request, response);
                break;

            case "overview":
            default:
                handleChartData(request, response);
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

    private void handleChartData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int tongPhong = thongKeDAO.getTongPhong();
        int phongHoatDong = thongKeDAO.getPhongHoatDong();
        int khachDangLuuTru = thongKeDAO.getKhachDangLuuTru();
        double doanhThuThang = thongKeDAO.getDoanhThuThang();
        int phongCanBaoTri = thongKeDAO.getPhongCanBaoTri();

        request.setAttribute("tongPhong", tongPhong);
        request.setAttribute("phongHoatDong", phongHoatDong);
        request.setAttribute("khachDangLuuTru", khachDangLuuTru);
        request.setAttribute("doanhThuThang", doanhThuThang);
        request.setAttribute("phongCanBaoTri", phongCanBaoTri);

        request.getRequestDispatcher("/admin/thongke.jsp").forward(request, response);
    }

    private void handleOverview(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        List<Double> doanhThu6Thang = thongKeDAO.getDoanhThu6ThangGanNhat();
        List<Integer> tyLeLoaiPhong = thongKeDAO.getTiLeDatPhongTheoLoai();
        List<Integer> months = thongKeDAO.get6MonthsLabels();

        StringBuilder json = new StringBuilder();
        json.append("{");

        json.append("\"months\": [");
        for (int i = 0; i < months.size(); i++) {
            json.append(months.get(i));
            if (i < months.size() - 1) {
                json.append(",");
            }
        }
        json.append("],");
        json.append("\"doanhThu\": [");
        for (int i = 0; i < doanhThu6Thang.size(); i++) {
            json.append(doanhThu6Thang.get(i));
            if (i < doanhThu6Thang.size() - 1) {
                json.append(",");
            }
        }
        json.append("],");

        json.append("\"tiLe\": [");
        for (int i = 0; i < tyLeLoaiPhong.size(); i++) {
            json.append(tyLeLoaiPhong.get(i));
            if (i < tyLeLoaiPhong.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");

        json.append("}");

        out.print(json.toString());
        out.flush();
    }
}
