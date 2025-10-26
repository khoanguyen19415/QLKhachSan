package model;

import java.sql.*;
import java.util.*;
import util.DBConnection;

public class KhachHangDAO {

    public List<KhachHang> getAll() {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                KhachHang kh = new KhachHang(
                        rs.getInt("MaKH"),
                        rs.getString("HoTen"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getString("DiaChi")
                );
                list.add(kh);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertKhachHang(KhachHang kh) {
        String sql = "INSERT INTO KhachHang(HoTen, SoDienThoai, Email, DiaChi) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getSoDienThoai());
            ps.setString(3, kh.getEmail());
            ps.setString(4, kh.getDiaChi());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteKhachHang(int maKH) {
        String sql = "DELETE FROM KhachHang WHERE MaKH=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maKH);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateKhachHang(KhachHang kh) {
        String sql = "UPDATE KhachHang SET HoTen=?, SoDienThoai=?, Email=?, DiaChi=? WHERE MaKH=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getSoDienThoai());
            ps.setString(3, kh.getEmail());
            ps.setString(4, kh.getDiaChi());
            ps.setInt(5, kh.getMaKH());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<KhachHang> searchKhachHang(String keyword) {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang WHERE HoTen LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                KhachHang kh = new KhachHang(
                        rs.getInt("MaKH"),
                        rs.getString("HoTen"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getString("DiaChi")
                );
                list.add(kh);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
