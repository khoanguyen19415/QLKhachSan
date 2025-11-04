package model;

import java.sql.*;
import java.util.*;
import util.DBConnection;

public class PhongDAO {

    public List<Phong> getAllPhong() {
        List<Phong> list = new ArrayList<>();
        String sql = "SELECT MaPhong, TenPhong, LoaiPhong, Gia, MoTa, TrangThai FROM Phong";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Phong p = new Phong(
                        rs.getInt("MaPhong"),
                        rs.getString("TenPhong"),
                        rs.getString("LoaiPhong"),
                        rs.getDouble("Gia"),
                        rs.getString("MoTa"),
                        rs.getString("TrangThai")
                );
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ Hàm thêm phòng
    public void insert(Phong p) {
        String sql = "INSERT INTO Phong (TenPhong, LoaiPhong, Gia, MoTa, TrangThai) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getTenPhong());
            ps.setString(2, p.getLoaiPhong());
            ps.setDouble(3, p.getGia());
            ps.setString(4, p.getMoTa());
            ps.setString(5, p.getTrangThai());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean update(Phong p) {
    String sql = "UPDATE Phong SET TenPhong = ?, LoaiPhong = ?, Gia = ?, MoTa = ?, TrangThai = ? WHERE MaPhong = ?";
    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, p.getTenPhong());
        ps.setString(2, p.getLoaiPhong());
        ps.setDouble(3, p.getGia());
        ps.setString(4, p.getMoTa());
        ps.setString(5, p.getTrangThai());
        ps.setInt(6, p.getMaPhong());
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

    // ✅ Hàm xóa phòng theo mã
    public void delete(int maPhong) {
        String sql = "DELETE FROM Phong WHERE MaPhong=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, maPhong);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<Phong> search(String keyword) {
        List<Phong> list = new ArrayList<>();
        String sql = "SELECT MaPhong, TenPhong, LoaiPhong, Gia, MoTa, TrangThai FROM Phong WHERE TenPhong LIKE ? OR LoaiPhong LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String k = "%" + (keyword == null ? "" : keyword.trim()) + "%";
            ps.setString(1, k);
            ps.setString(2, k);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Phong p = new Phong(
                        rs.getInt("MaPhong"),
                        rs.getString("TenPhong"),
                        rs.getString("LoaiPhong"),
                        rs.getDouble("Gia"),
                        rs.getString("MoTa"),
                        rs.getString("TrangThai")
                );
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== MỚI: cập nhật trạng thái phòng =====
    public boolean updateStatus(int maPhong, String trangThai) {
        String sql = "UPDATE Phong SET TrangThai = ? WHERE MaPhong = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, trangThai);
            ps.setInt(2, maPhong);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tuỳ: lấy 1 phòng theo id (nếu cần)
    public Phong getById(int maPhong) {
        String sql = "SELECT MaPhong, TenPhong, LoaiPhong, Gia, MoTa, TrangThai FROM Phong WHERE MaPhong = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, maPhong);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Phong(
                        rs.getInt("MaPhong"),
                        rs.getString("TenPhong"),
                        rs.getString("LoaiPhong"),
                        rs.getDouble("Gia"),
                        rs.getString("MoTa"),
                        rs.getString("TrangThai")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int countRooms() {
        String sql = "SELECT COUNT(*) AS cnt FROM Phong";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("cnt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    
    public String normalizeStatus(String raw) {
        if (raw == null) return "Đã đặt";
        String s = raw.trim().toLowerCase();
        if (s.contains("trống")) return "Trống";
        // nếu có từ "hủy" hay "đã trả" v.v. => phòng hiện không đang bị đặt (ở đây mình coi là Trống)
        if (s.contains("hủy") || s.contains("trả")) return "Trống";
        // nếu có "đặt" hoặc "đang" hoặc "nhận" => coi là đã đặt/không trống
        if (s.contains("đặt") || s.contains("đang") || s.contains("nhận")) return "Đã đặt";
        // mặc định nếu ko rõ, giữ "Đã đặt" an toàn (không cho đặt trùng)
        return "Đã đặt";
    }
}
