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
        String sql = "SELECT COUNT(*) FROM Phong WHERE TrangThai IN (N'Trống', N'Đang ở')";
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
        String sql = "SELECT COUNT(DISTINCT MaKH) FROM ChiTietDatPhong WHERE TrangThai = N'Đang ở'";
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
        String sql
                = "SELECT ISNULL(SUM(ct.Gia * DATEDIFF(day, dp.NgayNhan, dp.NgayTra)), 0) AS DoanhThu "
                + "FROM DatPhong dp "
                + "JOIN ChiTietDatPhong ct ON dp.MaDatPhong = ct.MaDatPhong "
                + "WHERE MONTH(dp.NgayTra) = MONTH(GETDATE()) "
                + "  AND YEAR(dp.NgayTra) = YEAR(GETDATE()) "
                + "  AND ct.TrangThai IN (N'Đã trả phòng', N'Đã trả')";

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
        List<Double> result = new ArrayList<>();

        String sql
                = "SELECT MONTH(dp.NgayTra) AS Thang, "
                + "       SUM(ct.Gia * DATEDIFF(day, dp.NgayNhan, dp.NgayTra)) AS TongTien "
                + "FROM DatPhong dp "
                + "JOIN ChiTietDatPhong ct ON dp.MaDatPhong = ct.MaDatPhong "
                + "WHERE dp.NgayTra >= DATEADD(month, -6, GETDATE()) "
                + "GROUP BY MONTH(dp.NgayTra)";

        Map<Integer, Double> map = new HashMap<>();

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int month = rs.getInt("Thang");
                double money = rs.getDouble("TongTien") / 1_000_000.0; 
                map.put(month, money);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH) + 1;

        for (int i = 5; i >= 0; i--) {
            int month = currentMonth - i;
            if (month <= 0) {
                month += 12;
            }
            result.add(map.getOrDefault(month, 0.0));
        }

        return result;
    }


    public List<Integer> getTiLeDatPhongTheoLoai() {
        List<Integer> list = new ArrayList<>();
        String[] loaiPhong = {"Standard", "Deluxe", "Suite", "VIP", "Presidential Suite"};
        String sql = "SELECT p.LoaiPhong, COUNT(*) AS SoLuong FROM ChiTietDatPhong ct JOIN Phong p ON ct.MaPhong = p.MaPhong GROUP BY p.LoaiPhong";

        Map<String, Integer> map = new HashMap<>();
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString("LoaiPhong"), rs.getInt("SoLuong"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String loai : loaiPhong) {
            list.add(map.getOrDefault(loai, 0));
        }

        return list;
    }

    public List<Integer> get6MonthsLabels() {
        List<Integer> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        int current = cal.get(Calendar.MONTH) + 1;

        for (int i = 5; i >= 0; i--) {
            int m = current - i;
            if (m <= 0) {
                m += 12;
            }
            list.add(m);
        }
        return list;
    }

}
