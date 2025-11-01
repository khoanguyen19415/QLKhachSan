package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.PhongDAO;
import model.DatPhongDAO;

@WebServlet(name = "DashBoardServlet", urlPatterns = {"/quan-tri"})
public class DashBoardServlet extends HttpServlet {

    private PhongDAO phongDAO;
    private DatPhongDAO datPhongDAO;

    @Override
    public void init() throws ServletException {
        phongDAO = new PhongDAO();
        datPhongDAO = new DatPhongDAO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy số liệu từ DB
        int totalRooms = phongDAO.countRooms();
        int guestsStaying = datPhongDAO.countCheckedIn();      // số đơn đang 'Đã nhận'
        int bookingsCount = datPhongDAO.countAllBookings();    // tổng số đơn
        int cancelledCount = datPhongDAO.countCancelled();     // số đơn hủy

        // set attributes cho JSP
        request.setAttribute("totalRooms", totalRooms);
        request.setAttribute("guestsStaying", guestsStaying);
        request.setAttribute("bookingsCount", bookingsCount);
        request.setAttribute("cancelledCount", cancelledCount);

        // forward tới dashboard.jsp
        request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
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
