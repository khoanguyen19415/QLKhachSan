package model;

import java.sql.*;
import java.util.*;
import util.DBConnection;

public class DatPhongDAO {

    // Lấy tất cả đơn đặt (đơn giản, bạn có thể thêm join lấy tên khách/phòng nếu muốn)
    public List<DatPhong> getAll() {
        List<DatPhong> list = new ArrayList<>();
        String sql = "SELECT MaDatPhong, MaKH, MaPhong, NgayDat, NgayNhan, NgayTra, TrangThai FROM DatPhong ORDER BY MaDatPhong DESC";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DatPhong dp = new DatPhong();
                dp.setMaDatPhong(rs.getInt("MaDatPhong"));
                dp.setMaKH(rs.getInt("MaKH"));
                dp.setMaPhong(rs.getInt("MaPhong"));
                dp.setNgayDat(rs.getDate("NgayDat"));
                dp.setNgayNhan(rs.getDate("NgayNhan"));
                dp.setNgayTra(rs.getDate("NgayTra"));
                dp.setTrangThai(rs.getString("TrangThai"));
                list.add(dp);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // Cập nhật trạng thái theo MaDatPhong
    public boolean updateStatus(int maDatPhong, String trangThai) {
        String sql = "UPDATE DatPhong SET TrangThai = ? WHERE MaDatPhong = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, trangThai);
            ps.setInt(2, maDatPhong);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Tùy chọn: lấy 1 đơn theo id
    public DatPhong getById(int maDatPhong) {
        String sql = "SELECT MaDatPhong, MaKH, MaPhong, NgayDat, NgayNhan, NgayTra, TrangThai FROM DatPhong WHERE MaDatPhong = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maDatPhong);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    DatPhong dp = new DatPhong();
                    dp.setMaDatPhong(rs.getInt("MaDatPhong"));
                    dp.setMaKH(rs.getInt("MaKH"));
                    dp.setMaPhong(rs.getInt("MaPhong"));
                    dp.setNgayDat(rs.getDate("NgayDat"));
                    dp.setNgayNhan(rs.getDate("NgayNhan"));
                    dp.setNgayTra(rs.getDate("NgayTra"));
                    dp.setTrangThai(rs.getString("TrangThai"));
                    return dp;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    
    public int countAllBookings() {
        String sql = "SELECT COUNT(*) AS cnt FROM DatPhong";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt("cnt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    
    public int countCheckedIn() {
        String sql = "SELECT COUNT(*) AS cnt FROM DatPhong WHERE TrangThai LIKE N'%nhận%' OR TrangThai LIKE N'%Nhận%'";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt("cnt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int countCancelled() {
        String sql = "SELECT COUNT(*) AS cnt FROM DatPhong WHERE TrangThai LIKE N'%Hủy%' OR TrangThai LIKE N'%hủy%'";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt("cnt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public List<DatPhong> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAll();
        }
        List<DatPhong> list = new ArrayList<>();
        String k = keyword.trim();
        // thử parse thành số
        try (Connection conn = DBConnection.getConnection()) {
            try {
                int id = Integer.parseInt(k);
                String sql = "SELECT MaDatPhong, MaKH, MaPhong, NgayDat, NgayNhan, NgayTra, TrangThai FROM DatPhong WHERE MaDatPhong = ? OR MaKH = ? OR MaPhong = ? ORDER BY MaDatPhong DESC ";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ps.setInt(2, id);
                    ps.setInt(3, id);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        DatPhong dp = new DatPhong(
                            rs.getInt("MaDatPhong"),
                            rs.getInt("MaKH"),
                            rs.getInt("MaPhong"),
                            rs.getDate("NgayDat"),
                            rs.getDate("NgayNhan"),
                            rs.getDate("NgayTra"),
                            rs.getString("TrangThai")
                        );
                        list.add(dp);
                    }
                }
            } catch (NumberFormatException ex) {
                // nếu không phải số, tìm theo chuỗi trong trạng thái (ví dụ)
                String sql = "SELECT MaDatPhong, MaKH, MaPhong, NgayDat, NgayNhan, NgayTra, TrangThai FROM DatPhong WHERE TrangThai LIKE ? ORDER BY MaDatPhong DESC";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, "%" + k + "%");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        DatPhong dp = new DatPhong(
                            rs.getInt("MaDatPhong"),
                            rs.getInt("MaKH"),
                            rs.getInt("MaPhong"),
                            rs.getDate("NgayDat"),
                            rs.getDate("NgayNhan"),
                            rs.getDate("NgayTra"),
                            rs.getString("TrangThai")
                        );
                        list.add(dp);
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
