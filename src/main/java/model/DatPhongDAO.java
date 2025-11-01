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
}
