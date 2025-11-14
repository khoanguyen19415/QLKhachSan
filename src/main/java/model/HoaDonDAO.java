package model;

import util.DBConnection;
import java.sql.*;

public class HoaDonDAO {

    public HoaDon getByMaDatPhong(int maDatPhong) {

        String sql
                = "SELECT hd.*, kh.HoTen, kh.SoDienThoai, dp.NgayNhan, dp.NgayTra "
                + "FROM HoaDon hd "
                + "JOIN KhachHang kh ON hd.MaKH = kh.MaKH "
                + "JOIN DatPhong dp ON hd.MaDatPhong = dp.MaDatPhong "
                + "WHERE hd.MaDatPhong = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDatPhong);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    HoaDon hd = new HoaDon();
                    hd.setMaHoaDon(rs.getInt("MaHoaDon"));
                    hd.setMaDatPhong(rs.getInt("MaDatPhong"));
                    hd.setMaKH(rs.getInt("MaKH"));
                    hd.setNgayLap(rs.getTimestamp("NgayLap"));
                    
                    Date ngayNhan = rs.getDate("NgayNhan");
                    Date ngayTra = rs.getDate("NgayTra");
                    hd.setNgayNhan(ngayNhan);
                    hd.setNgayTra(ngayTra);

                    double tong = tinhTongTien(rs.getInt("MaHoaDon"));
                    hd.setTongTien(tong);

                    hd.setDaThanhToan(rs.getBoolean("DaThanhToan"));
                    hd.setTenKhach(rs.getString("HoTen"));
                    hd.setSdt(rs.getString("SoDienThoai"));

                    return hd;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public int insert(HoaDon hd) {

        String sql
                = "INSERT INTO HoaDon (MaDatPhong, MaKH, TongTien, DaThanhToan) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps
                = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, hd.getMaDatPhong());
            ps.setInt(2, hd.getMaKH());
            ps.setDouble(3, hd.getTongTien());
            ps.setBoolean(4, hd.isDaThanhToan());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public boolean updateThanhToan(int maDatPhong) {
        String sql = "UPDATE HoaDon SET DaThanhToan = 1, NgayLap = GETDATE() WHERE MaDatPhong = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDatPhong);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private double tinhTongTien(int maHoaDon) {
        String sql = "SELECT SUM(ThanhTien) AS Tong FROM HoaDonChiTiet WHERE MaHoaDon = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maHoaDon);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("Tong");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void generateChiTietHoaDon(int maDatPhong, int maHoaDon) {
        String sql
                = "INSERT INTO HoaDonChiTiet (MaHoaDon, MaPhong, DonGia, SoDem, ThanhTien) "
                + "SELECT ?, MaPhong, Gia, SoDem, (Gia*SoDem) "
                + "FROM ChiTietDatPhong WHERE MaDatPhong = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maHoaDon);
            ps.setInt(2, maDatPhong);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
