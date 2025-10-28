package model;

import java.sql.*;
import java.util.*;
import model.ChiTietPhong;
import util.DBConnection;

public class ChiTietPhongDAO {

    public List<ChiTietPhong> getByPhong(int maPhong) {
        List<ChiTietPhong> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietPhong WHERE MaPhong = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

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
}
