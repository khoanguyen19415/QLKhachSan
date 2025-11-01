package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.DBConnection;

@WebServlet(name = "DashBoardServlet", urlPatterns = {"/quan-tri"})
public class DashBoardServlet extends HttpServlet {

    private int countSingleValue(String sql, Object... params) {
        int result = 0;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // ensure proper encoding
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 1) Tổng số phòng
        String sqlTotalRooms = "SELECT COUNT(*) FROM Phong";
        int totalRooms = countSingleValue(sqlTotalRooms);

        // 2) Khách đang ở (đếm đơn có trạng thái 'Đã nhận' / chứa 'nhận')
        // dùng LOWER để tránh vấn đề viết hoa
        String sqlGuests = "SELECT COUNT(*) FROM DatPhong WHERE LOWER(TrangThai) LIKE ?";
        int guests = countSingleValue(sqlGuests, "%nhận%");

        // 3) Đơn đặt phòng (tính là tổng đơn KHÔNG bị hủy) — bạn có thể điều chỉnh logic nếu muốn chỉ tính 'Chờ'
        String sqlBookings = "SELECT COUNT(*) FROM DatPhong WHERE LOWER(TrangThai) NOT LIKE ?";
        int bookings = countSingleValue(sqlBookings, "%hủy%");

        // 4) Hủy phòng (đếm các đơn có trạng thái chứa 'hủy')
        String sqlCancellations = "SELECT COUNT(*) FROM DatPhong WHERE LOWER(TrangThai) LIKE ?";
        int cancellations = countSingleValue(sqlCancellations, "%hủy%");

        // Gán vào request attribute (JSP dùng ${totalRooms}, ${guests}, ${bookings}, ${cancellations})
        request.setAttribute("totalRooms", totalRooms);
        request.setAttribute("guests", guests);
        request.setAttribute("bookings", bookings);
        request.setAttribute("cancellations", cancellations);

        // forward tới trang dashboard
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
