package model;

import java.sql.*;
import java.util.*;
import util.DBConnection;

public class PhongAnhDAO {

    public List<PhongAnh> getAnhTheoPhong(int maPhong) {
        List<PhongAnh> list = new ArrayList<>();
        String sql = "SELECT * FROM PhongAnh WHERE MaPhong = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maPhong);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PhongAnh pa = new PhongAnh();
                pa.setMaAnh(rs.getInt("MaAnh"));
                pa.setMaPhong(rs.getInt("MaPhong"));
                pa.setDuongDanAnh(rs.getString("DuongDanAnh"));
                list.add(pa);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void themAnhChoPhong(int maPhong, String duongDanAnh) {
        String sql = "INSERT INTO PhongAnh (MaPhong, DuongDanAnh) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maPhong);
            ps.setString(2, duongDanAnh);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Trả về object PhongAnh theo id
    public PhongAnh getById(int maAnh) {
        String sql = "SELECT * FROM PhongAnh WHERE MaAnh = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maAnh);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PhongAnh pa = new PhongAnh();
                pa.setMaAnh(rs.getInt("MaAnh"));
                pa.setMaPhong(rs.getInt("MaPhong"));
                pa.setDuongDanAnh(rs.getString("DuongDanAnh"));
                return pa;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Xóa bản ghi ảnh theo id
    public boolean delete(int maAnh) {
        String sql = "DELETE FROM PhongAnh WHERE MaAnh = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maAnh);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
