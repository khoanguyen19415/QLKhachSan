package model;

import util.DBConnection;
import java.sql.*;
import java.util.*;

public class HoaDonChiTietDAO {

    public List<HoaDonChiTiet> getByHoaDon(int maHoaDon) {
        List<HoaDonChiTiet> list = new ArrayList<>();

        String sql
                = "SELECT ct.*, p.TenPhong "
                + "FROM HoaDonChiTiet ct "
                + "JOIN Phong p ON ct.MaPhong = p.MaPhong "
                + "WHERE MaHoaDon = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maHoaDon);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HoaDonChiTiet ct = new HoaDonChiTiet();
                ct.setMaHDCT(rs.getInt("MaHDCT"));
                ct.setMaHoaDon(rs.getInt("MaHoaDon"));
                ct.setMaPhong(rs.getInt("MaPhong"));
                ct.setDonGia(rs.getDouble("DonGia"));
                ct.setSoDem(rs.getInt("SoDem"));
                ct.setThanhTien(rs.getDouble("ThanhTien"));
                ct.setTenPhong(rs.getString("TenPhong"));
                list.add(ct);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insert(HoaDonChiTiet ct) {

        String sql
                = "INSERT INTO HoaDonChiTiet (MaHoaDon, MaPhong, DonGia, SoDem, ThanhTien) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ct.getMaHoaDon());
            ps.setInt(2, ct.getMaPhong());
            ps.setDouble(3, ct.getDonGia());
            ps.setInt(4, ct.getSoDem());
            ps.setDouble(5, ct.getThanhTien());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
