package model;

import java.sql.*;
import util.DBConnection;

public class TaiKhoanDAO {

    public TaiKhoan findByUsername(String username) {
        String sql = "SELECT * FROM TaiKhoan WHERE TenDangNhap = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    TaiKhoan tk = new TaiKhoan();
                    tk.setMaTK(rs.getInt("MaTK"));
                    tk.setTenDangNhap(rs.getString("TenDangNhap"));
                    tk.setMatKhau(rs.getString("MatKhau"));
                    tk.setChucVu(rs.getString("ChucVu"));
                    return tk;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int insertAndGetId(TaiKhoan tk) {
        String sql = "INSERT INTO TaiKhoan (TenDangNhap, MatKhau, ChucVu) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, tk.getTenDangNhap());
            ps.setString(2, tk.getMatKhau());
            ps.setString(3, tk.getChucVu());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean deleteById(int maTK) {
        String sql = "DELETE FROM TaiKhoan WHERE MaTK = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maTK);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
