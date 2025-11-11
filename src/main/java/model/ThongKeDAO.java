package model;

import java.sql.*;
import java.util.*;
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

    // ------------------------
    // 1️⃣ Tổng số phòng
    // ------------------------
    public int getTongPhong() {
        String sql = "SELECT COUNT(*) FROM Phong";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // ------------------------
    // 2️⃣ Số phòng đang hoạt động (Trống hoặc Đang ở)
    // ------------------------
    public int getPhongHoatDong() {
        String sql = "SELECT COUNT(*) FROM Phong WHERE TrangThai IN (N'Trống', N'Đang ở')";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // ------------------------
    // 3️⃣ Số khách đang lưu trú (tức là đang có đơn “Đang ở”)
    // ------------------------
    public int getKhachDangLuuTru() {
        String sql = "SELECT COUNT(DISTINCT MaKH) FROM DatPhong WHERE TrangThai = N'Đang ở'";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // ------------------------
    // 4️⃣ Doanh thu tháng hiện tại (tính từ ChiTietDatPhong)
    // ------------------------
    public double getDoanhThuThang() {
        String sql = "SELECT ISNULL(SUM(ct.Gia * DATEDIFF(day, dp.NgayNhan, dp.NgayTra)), 0) AS DoanhThu FROM DatPhong dp JOIN ChiTietDatPhong ct ON dp.MaDatPhong = ct.MaDatPhong WHERE MONTH(dp.NgayTra) = MONTH(GETDATE()) AND YEAR(dp.NgayTra) = YEAR(GETDATE()) AND dp.TrangThai IN (N'Đã trả phòng', N'Đã nhận')";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getDouble("DoanhThu");
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // ------------------------
    // 5️⃣ Số phòng cần bảo trì
    // ------------------------
    public int getPhongCanBaoTri() {
        String sql = "SELECT COUNT(*) FROM Phong WHERE TrangThai = N'Bảo trì'";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // ------------------------
    // 6️⃣ Doanh thu 6 tháng gần nhất (đơn giản, tính trung bình theo tháng)
    // ------------------------
    public List<Double> getDoanhThu6ThangGanNhat() {
        List<Double> list = new ArrayList<>();
        String sql = "SELECT TOP 6 MONTH(dp.NgayTra) AS Thang, SUM(ct.Gia * DATEDIFF(day, dp.NgayNhan, dp.NgayTra)) AS TongTien FROM DatPhong dp JOIN ChiTietDatPhong ct ON dp.MaDatPhong = ct.MaDatPhong WHERE dp.NgayTra IS NOT NULL GROUP BY MONTH(dp.NgayTra) ORDER BY Thang";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getDouble("TongTien") / 1_000_000.0); // đổi sang triệu đồng
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // ------------------------
    // 7️⃣ Tỉ lệ đặt phòng theo loại (đếm số phòng mỗi loại có trong ChiTietDatPhong)
    // ------------------------
    public List<Integer> getTiLeDatPhongTheoLoai() {
        List<Integer> list = new ArrayList<>();
        String[] loaiPhong = {"Standard", "Deluxe", "Suite", "VIP", "Presidential Suite"};
        String sql = "SELECT p.LoaiPhong, COUNT(*) AS SoLuong FROM ChiTietDatPhong ct JOIN Phong p ON ct.MaPhong = p.MaPhong GROUP BY p.LoaiPhong";

        Map<String, Integer> map = new HashMap<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString("LoaiPhong"), rs.getInt("SoLuong"));
            }
        } catch (Exception e) { e.printStackTrace(); }

        // Đảm bảo đúng thứ tự cố định
        for (String loai : loaiPhong) {
            list.add(map.getOrDefault(loai, 0));
        }

        return list;
    }
}
