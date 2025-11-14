package model;

import java.sql.*;
import java.util.*;
import util.DBConnection;

public class ChiTietPhongDAO {

    public List<ChiTietPhong> getByPhong(int maPhong) {
        List<ChiTietPhong> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietPhong WHERE MaPhong = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maPhong);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChiTietPhong ct = new ChiTietPhong();
                ct.setMaCTP(rs.getInt("MaCTP"));
                ct.setMaPhong(rs.getInt("MaPhong"));
                ct.setTienNghi(rs.getString("TienNghi"));
                ct.setMoTa(rs.getString("MoTa"));
                list.add(ct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean updateChiTietPhong(ChiTietPhong ctp) {
        String sql = "UPDATE ChiTietPhong SET TienNghi = ?, MoTa = ? WHERE MaCTP = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ctp.getTienNghi());
            ps.setString(2, ctp.getMoTa());
            ps.setInt(3, ctp.getMaCTP());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int insertChiTietPhong(ChiTietPhong ctp) {
        String sql = "INSERT INTO ChiTietPhong (MaPhong, TienNghi, MoTa) VALUES (?, ?, ?)";
        ResultSet rs = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, ctp.getMaPhong());
            ps.setString(2, ctp.getTienNghi());
            ps.setString(3, ctp.getMoTa());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (Exception ex) {}
        }
        return -1;
    }
}
