package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.DBConnection;

public class ThongKeDAO {

    private Connection conn;

    public ThongKeDAO() {
        try {
            conn = DBConnection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTongPhong() {
        String sql = "SELECT COUNT(*) FROM Phong";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getPhongHoatDong() {
        String sql = "SELECT COUNT(*) FROM Phong WHERE TrangThai = N'Trống'";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getKhachDangLuuTru() {
        String sql = "SELECT COUNT(*) FROM Phong WHERE TrangThai = N'Đang ở'";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getDoanhThuThang() {
        String sql = "SELECT SUM(DATEDIFF(day, dp.NgayNhan, dp.NgayTra) * p.Gia) AS DoanhThu FROM DatPhong dp JOIN Phong p ON dp.MaPhong = p.MaPhong WHERE MONTH(dp.NgayTra) = MONTH(GETDATE()) AND YEAR(dp.NgayTra) = YEAR(GETDATE())AND dp.TrangThai IN (N'Đã trả phòng', N'Đã nhận')";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("DoanhThu");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getPhongCanBaoTri() {
        String sql = "SELECT COUNT(*) FROM Phong WHERE TrangThai = N'Bảo trì'";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Double> getDoanhThu6ThangGanNhat() {
        List<Double> list = new ArrayList<>();
        String sql = "SELECT TOP 6 MONTH(dp.NgayTra) AS Thang, SUM(DATEDIFF(day, dp.NgayNhan, dp.NgayTra) * p.Gia) AS TongTien FROM DatPhong dp JOIN Phong p ON dp.MaPhong = p.MaPhong WHERE dp.NgayTra IS NOT NULL GROUP BY MONTH(dp.NgayTra) ORDER BY Thang ";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getDouble("TongTien") / 1000000); // chuyển sang triệu
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Integer> getTiLeDatPhongTheoLoai() {
        List<Integer> list = new ArrayList<>();
        String[] loaiPhong = {"Standard", "Deluxe", "Suite", "VIP"};
        String sql = "SELECT LoaiPhong, COUNT(*) AS SoLuong FROM Phong GROUP BY LoaiPhong";

        Map<String, Integer> map = new HashMap<>();
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString("LoaiPhong"), rs.getInt("SoLuong"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Đảm bảo đúng thứ tự 4 loại
        for (String loai : loaiPhong) {
            list.add(map.getOrDefault(loai, 0));
        }

        return list;
    }
}
