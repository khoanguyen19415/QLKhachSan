package model;

import java.sql.*;
import java.util.*;
import model.PhongAnh;
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
}
